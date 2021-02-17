package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class GameStateChecker {

    private final Integer pits;

    private final Integer players;

    public GameStateChecker(@Value("${kalah.pits}") Integer pits,
                            @Value("${kalah.players}") Integer players) {
        this.pits = pits;
        this.players = players;
    }

    public GameState checkIfFinished(GameState gameState) {
        List<Pit> gameStatePits = gameState.getPits();
        boolean finished = false;
        for (int playerId = 1; playerId <= players; playerId++) {
            if (!isPlayerHasMove(gameStatePits, playerId)) {
                finished = true;
                break;
            }
        }
        if (finished) {
            endGameMoves(gameState, gameStatePits);
        }
        return gameState;
    }

    private boolean isPlayerHasMove(List<Pit> gameStatePits, int playerId) {
        int playerStartPit = pits * (playerId - 1) + (playerId - 1);
        int playerEndPit = pits * playerId + (playerId - 1);

        List<Pit> playerPits = gameStatePits
                .subList(playerStartPit, playerEndPit);
        return playerPits.stream()
                .anyMatch(p -> p.getStones() != 0);
    }

    private void endGameMoves(GameState gameState, List<Pit> gameStatePits) {
        int winner = 0;
        int winnerStones = 0;
        for (int playerId = 1; playerId <= players; playerId++) {
            int playerStartPit = pits * (playerId - 1) + (playerId - 1);
            int playerEndPit = pits * playerId + (playerId - 1);
            emptyPitsToKalah(gameState, gameStatePits, playerStartPit, playerEndPit);
            if (gameState.getPits().get(playerEndPit).getStones() > winnerStones) {
                winnerStones = gameState.getPits().get(playerEndPit).getStones();
                winner = playerId;
            } else if(gameState.getPits().get(playerEndPit).getStones() == winnerStones) {
                winner = 0;
            }
        }
        setWinner(gameState, winner);
    }

    private void emptyPitsToKalah(GameState gameState,
                                  List<Pit> gameStatePits,
                                  int playerStartPit,
                                  int playerEndPit) {
        AtomicInteger remainStones = new AtomicInteger();
        List<Pit> playerPits = gameStatePits
                .subList(playerStartPit, playerEndPit);
        playerPits.forEach(p -> {
            int pitStones = p.empty();
            remainStones.addAndGet(pitStones);
            gameState.getPits().set(p.getId() - 1, p);
        });
        gameState.getPits().get(playerEndPit).inc(remainStones.get());
    }

    private void setWinner(GameState gameState, int winner) {
        gameState.setWinner(winner);
        gameState.setFinished(true);
    }
}
