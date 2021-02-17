package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import me.valizadeh.challenges.backbase.kalah.predicate.IsOpponentKalahPredicate;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerKalahPitPredicate;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerRegularPitPredicate;
import me.valizadeh.challenges.backbase.kalah.validator.PlayGameValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class MoveExecutor {

    private final PlayGameValidator validator;

    private final IsPlayerKalahPitPredicate isPlayerKalahPitPredicate;
    private final IsPlayerRegularPitPredicate isPlayerRegularPitPredicate;
    private final IsOpponentKalahPredicate opponentKalahPredicate;

    private final Integer pits;

    private final Integer players;

    public MoveExecutor(PlayGameValidator validator,
                        @Value("${kalah.pits}") Integer pits,
                        @Value("${kalah.players}") Integer players) {
        this.validator = validator;
        this.pits = pits;
        this.players = players;
        this.opponentKalahPredicate = new IsOpponentKalahPredicate(pits);
        this.isPlayerKalahPitPredicate = new IsPlayerKalahPitPredicate(pits);
        this.isPlayerRegularPitPredicate = new IsPlayerRegularPitPredicate(pits);
    }

    public GameState play(GameState gameState,
                          int pitId) {
        validator.validatePitId(pitId);
        int playerId = getPlayerId(pitId);
        Pit playedPit = gameState.getPits().get(pitId - 1);
        validator.validatePlayedPit(gameState,
                playedPit,
                playerId);

        emptyPlayedPitAndPlay(gameState,
                playedPit,
                pitId,
                playerId);

        return gameState;
    }

    private void emptyPlayedPitAndPlay(GameState gameState,
                                       Pit playedPit,
                                       int pitId,
                                       int playerId) {
        int playedPitStones = playedPit.empty();
        AtomicInteger stonesToPlayed = new AtomicInteger(playedPitStones);
        List<Pit> pitsBeforePlay = gameState.getPits();
        getGoThroughPits(pitsBeforePlay, pitId, playedPitStones, playedPitStones)
                .filter(p -> opponentKalahPredicate.negate().test(p, playerId))
                .forEach(p -> putStoneInPit(gameState, stonesToPlayed, playerId,
                        p));
    }

    private int getPlayerId(int pitId) {
        return ((pitId - 1) / (pits + 1)) + 1;
    }

    private IntStream getGoThroughPits(List<Pit> pits,
                                       int pitId,
                                       int stones,
                                       int playedPitStones) {
        int pitRelativeId = getPitRelativeToPlayerId(pitId);

        boolean jumpOpponentKalah = needJumpOpponentKalah(pits, stones, pitRelativeId);
        return getShouldBePlayedPitsStream(pits, pitId, stones, playedPitStones, jumpOpponentKalah);
    }

    private boolean needJumpOpponentKalah(List<Pit> pits,
                                          int stones,
                                          int pitRelativeId) {
        return stones >= pits.size() - pitRelativeId;
    }

    private IntStream getShouldBePlayedPitsStream(List<Pit> pits,
                                                  int pitId,
                                                  int stones,
                                                  int playedPitStones,
                                                  boolean jumpOpponentKalah) {
        IntStream goThroughPits;
        int lastPit = pitId + playedPitStones;
        if (lastPit >= pits.size()) {
            goThroughPits = goAroundBoard(pits, pitId, stones, jumpOpponentKalah);
        } else {
            goThroughPits = playAcrossBoard(pitId, jumpOpponentKalah, lastPit);
        }
        return goThroughPits;
    }

    private IntStream playAcrossBoard(int pitId,
                                      boolean jumpOpponentKalah,
                                      int lastPit) {
        IntStream goThroughPits;
        if (jumpOpponentKalah)
            lastPit += 1;
        goThroughPits = IntStream.rangeClosed(pitId + 1, lastPit);
        return goThroughPits;
    }

    private IntStream goAroundBoard(List<Pit> pits,
                                    int pitId,
                                    int stones,
                                    boolean jumpOpponentKalah) {
        int lastPit;
        IntStream goThroughPits;
        lastPit = stones - (pits.size() - pitId);
        if (jumpOpponentKalah)
            lastPit += 1;
        goThroughPits = IntStream.concat(IntStream.rangeClosed(pitId + 1, pits.size()),
                IntStream.rangeClosed(1, lastPit));
        return goThroughPits;
    }

    private int getPitRelativeToPlayerId(int pitId) {
        int pitRelativeId = pitId;
        if (pitId > pits) {
            pitRelativeId = pitId - (pits + 1);
        }
        return pitRelativeId;
    }

    private void putStoneInPit(GameState gameState,
                               AtomicInteger stonesToPlayed,
                               int playerId,
                               int pitId) {
        Pit pit = gameState.getPits().get(pitId - 1);
        pit.inc();
        stonesToPlayed.getAndDecrement();
        gameState.getPits().set(pit.getId() - 1, pit);
        if (stonesToPlayed.get() == 0) {
            lastPitToPutStone(gameState, pit, playerId);
        }
    }

    private void lastPitToPutStone(GameState gameState,
                                   Pit pit,
                                   int playerId) {
        if (pit.getStones() == 1 &&
                isPlayerRegularPitPredicate.test(playerId, pit.getId())) {
            emptyPitAddToKalah(gameState, pit, playerId);
        }
        if (isPlayerKalahPitPredicate.negate()
                .test(playerId, pit.getId())) {
            gameState.changeTurn();
        }
    }

    private void emptyPitAddToKalah(GameState gameState,
                                    Pit pit,
                                    int playerId) {
        int addToKalah = emptyPit(gameState, pit);
        addToKalah += emptyOppositePit(gameState, pit);
        putToKalah(gameState, playerId, addToKalah);
    }

    private void putToKalah(GameState gameState,
                            int playerId,
                            int stones) {
        int playerKalahPitId = pits * playerId + playerId;
        Pit playerKalahPit = gameState.getPits().get(playerKalahPitId - 1);
        playerKalahPit.inc(stones);
        gameState.getPits().set(playerKalahPitId - 1, playerKalahPit);
    }

    private int emptyOppositePit(GameState gameState,
                                 Pit pit) {
        int lastPlayedPitIdOpposite = (pits * players + players) - pit.getId();
        Pit lastPlayedPitOpposite = gameState.getPits().get(lastPlayedPitIdOpposite - 1);
        int stones = lastPlayedPitOpposite.empty();
        gameState.getPits().set(lastPlayedPitIdOpposite - 1, lastPlayedPitOpposite);
        return stones;
    }

    private int emptyPit(GameState gameState,
                         Pit pit) {
        int stones = pit.empty();
        gameState.getPits().set(pit.getId() - 1, pit);
        return stones;
    }
}
