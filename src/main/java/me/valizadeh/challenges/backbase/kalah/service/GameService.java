package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import me.valizadeh.challenges.backbase.kalah.predicate.IsKalahPredicate;
import me.valizadeh.challenges.backbase.kalah.predicate.IsOpponentKalahPredicate;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerKalahPitPredicate;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerRegularPitPredicate;
import me.valizadeh.challenges.backbase.kalah.validator.GameValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class GameService {

    private final Map<Integer, GameState> gameStates;

    private final Integer pits;

    private final Integer players;

    private final GameInitiator gameInitiator;

    private final IsKalahPredicate isKalahPredicate;
    private final IsPlayerKalahPitPredicate isPlayerKalahPitPredicate;
    private final IsPlayerRegularPitPredicate isPlayerRegularPitPredicate;
    private final IsOpponentKalahPredicate opponentKalahPredicate;

    private final GameValidator validator;

    public GameService(@Value("${kalah.pits}") Integer pits,
                       @Value("${kalah.players}") Integer players,
                       GameInitiator gameInitiator,
                       GameValidator validator) {
        this.pits = pits;
        this.players = players;
        this.gameInitiator = gameInitiator;
        this.validator = validator;
        this.gameStates = new HashMap<>();
        this.isKalahPredicate = new IsKalahPredicate(pits);
        this.opponentKalahPredicate = new IsOpponentKalahPredicate(pits);
        this.isPlayerKalahPitPredicate = new IsPlayerKalahPitPredicate(pits);
        this.isPlayerRegularPitPredicate = new IsPlayerRegularPitPredicate(pits);
    }

    public GameState createGame() {
        GameState gameState = gameInitiator.initiate();
        gameStates.put(gameState.getGame().getId(), gameState);
        return gameState;
    }

    public GameState makeMove(Integer gameId, Integer pitId) {
        this.validator.validateGame(gameStates, gameId);
        GameState gameState = gameStates.get(gameId);
        GameState playedGameState = play(gameState, pitId);
        GameState gameStateAfterCheckFinish = checkIfFinished(playedGameState);
        gameStates.put(gameId, gameStateAfterCheckFinish);
        return gameState;
    }

    private GameState checkIfFinished(GameState gameState) {
        List<Pit> gameStatePits = gameState.getPits();
        boolean finished = false;
        for (int playerId = 1; playerId <= players; playerId++) {
            int playerStartPit = pits * (playerId - 1) + (playerId - 1);
            int playerEndPit = pits * playerId + (playerId - 1);

            List<Pit> playerPits = gameStatePits
                    .subList(playerStartPit, playerEndPit);
            boolean playerHasMove = playerPits.stream()
                    .anyMatch(p -> p.getStones() != 0);
            if (!playerHasMove) {
                finished = true;
                break;
            }
        }
        if (finished) {
            int winner = 0;
            int winnerStones = 0;
            for (int playerId = 1; playerId <= players; playerId++) {
                int playerStartPit = pits * (playerId - 1) + (playerId - 1);
                int playerEndPit = pits * playerId + (playerId - 1);
                AtomicInteger remainStones = new AtomicInteger();
                List<Pit> playerPits = gameStatePits
                        .subList(playerStartPit, playerEndPit);
                playerPits.forEach(p -> {
                    int pitStones = p.empty();
                    remainStones.addAndGet(pitStones);
                    gameState.getPits().set(p.getId() - 1, p);
                });
                gameState.getPits().get(playerEndPit).inc(remainStones.get());
                if (gameState.getPits().get(playerEndPit).getStones() > winnerStones) {
                    winnerStones = gameState.getPits().get(playerEndPit).getStones();
                    winner = playerId;
                }
            }
            gameState.setWinner(winner);
            gameState.setFinished(true);
        }
        return gameState;
    }

    private GameState play(GameState gameState, Integer pitId) {
        validator.validatePitId(pitId);
        int playerId = getPlayerId(pitId);
        Pit playedPit = gameState.getPits().get(pitId - 1);
        validator.validatePlayedPit(gameState,
                playedPit, playerId);


        int playedPitStones = playedPit.empty();

        AtomicInteger stonesToPlayed = new AtomicInteger(playedPitStones);
        getGoThroughPits(gameState, pitId, playedPitStones, playedPitStones)
                .filter(p -> opponentKalahPredicate.negate().test(p, playerId))
                .forEach(p -> putStoneInPit(gameState, playerId,
                        stonesToPlayed, p));
        return gameState;
    }

    private void putStoneInPit(GameState gameState,
                               int playerId,
                               AtomicInteger stonesToPlayed,
                               int pitId) {
        Pit pit = gameState.getPits().get(pitId - 1);
        pit.inc();
        stonesToPlayed.getAndDecrement();
        gameState.getPits().set(pit.getId() - 1, pit);
        if (stonesToPlayed.get() == 0) {
            lastPitToPutStone(gameState, playerId, pit);
        }
    }

    private void lastPitToPutStone(GameState gameState,
                                   int playerId,
                                   Pit pit) {
        if (pit.getStones() == 1 &&
                isPlayerRegularPitPredicate.test(playerId, pit.getId())) {
            emptyPitAddToKalah(gameState, playerId, pit);
        }
        if (isPlayerKalahPitPredicate.negate()
                .test(playerId, pit.getId())) {
            gameState.changeTurn();
        }
    }

    private void emptyPitAddToKalah(GameState gameState,int playerId, Pit pit) {
        int playerKalahPitId = pits * playerId + playerId;
        var addToKalah = 0;
        addToKalah += pit.empty();
        gameState.getPits().set(pit.getId() - 1, pit);

        int lastPlayedPitIdOpposite = (pits * players + players) - pit.getId();
        Pit lastPlayedPitOpposite = gameState.getPits().get(lastPlayedPitIdOpposite - 1);
        addToKalah += lastPlayedPitOpposite.empty();
        gameState.getPits().set(lastPlayedPitIdOpposite - 1, lastPlayedPitOpposite);

        Pit playerKalahPit = gameState.getPits().get(playerKalahPitId - 1);
        playerKalahPit.inc(addToKalah);
        gameState.getPits().set(playerKalahPitId - 1, playerKalahPit);
    }

    private int getPlayerId(Integer pitId) {
        return (pitId / (pits + 1)) + (pitId % (pits + 1) != 0 ? 1 : 0);
    }

    private IntStream getGoThroughPits(GameState gameState, Integer pitId, int stones, int playedPitStones) {
        int lastPit = pitId + playedPitStones;
        IntStream goThroughPits;
        int pitRelativeId = pitId;
        if (pitId > pits) {
            pitRelativeId = pitId - (pits + 1);
        }
        boolean jumpOpponentKalah = stones >= gameState.getPits().size() - pitRelativeId;
        if (lastPit >= gameState.getPits().size()) {
            lastPit = stones - (gameState.getPits().size() - pitId);
            if (jumpOpponentKalah)
                lastPit += 1;
            goThroughPits = IntStream.concat(IntStream.rangeClosed(pitId + 1, gameState.getPits().size()),
                    IntStream.rangeClosed(1, lastPit));
        } else {
            if (jumpOpponentKalah)
                lastPit += 1;
            goThroughPits = IntStream.rangeClosed(pitId + 1, lastPit);
        }
        return goThroughPits;
    }

}
