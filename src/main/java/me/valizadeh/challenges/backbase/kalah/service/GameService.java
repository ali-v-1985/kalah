package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.springframework.stereotype.Component;

@Component
public class GameService {

    private final GameInitiator gameInitiator;

    private final GameMemory gameMemory;

    private final MoveExecutor moveExecutor;

    private final GameStateChecker gameStateChecker;

    public GameService(GameInitiator gameInitiator,
                       GameMemory gameMemory,
                       MoveExecutor moveExecutor,
                       GameStateChecker gameStateChecker) {
        this.gameInitiator = gameInitiator;
        this.gameMemory = gameMemory;
        this.moveExecutor = moveExecutor;
        this.gameStateChecker = gameStateChecker;
    }

    public GameState createGame() {
        GameState gameState = gameInitiator.initiate();
        gameMemory.storeGame(gameState.getGame().getId(), gameState);
        return gameState;
    }

    public GameState makeMove(Integer gameId, Integer pitId) {
        GameState gameState = gameMemory.loadGame(gameId);
        GameState playedGameState = moveExecutor.play(gameState, pitId);
        GameState gameStateAfterCheckFinish = gameStateChecker.checkIfFinished(playedGameState);
        gameMemory.storeGame(gameId, gameStateAfterCheckFinish);
        return gameState;
    }
}
