package me.valizadeh.challenges.backbase.kalah.handler;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.springframework.stereotype.Component;

/**
 * The game handler which says how each of two functionalities of the game will be processed.
 *
 * @author Ali
 */
@Component
public class GameHandler {

    private final GameInitiator gameInitiator;

    private final GameMemory gameMemory;

    private final MoveHandler moveHandler;

    private final GameStateChecker gameStateChecker;

    public GameHandler(GameInitiator gameInitiator,
                       GameMemory gameMemory,
                       MoveHandler moveHandler,
                       GameStateChecker gameStateChecker) {
        this.gameInitiator = gameInitiator;
        this.gameMemory = gameMemory;
        this.moveHandler = moveHandler;
        this.gameStateChecker = gameStateChecker;
    }

    /**
     * This functionality consists of two steps:
     * <ul>
     *     <li>Initiating</li>
     *     <li>Storing</li>
     * </ul>
     * Initiating uses {@link GameInitiator} to initiate the game.
     * Storing uses {@link GameMemory} to store it.
     *
     * @return The game state of the created game.
     */
    public GameState createGame() {
        GameState gameState = gameInitiator.initiate();
        gameMemory.storeGame(gameState.getGame().getId(), gameState);
        return gameState;
    }

    /**
     * This functionality consists of four steps:
     * <ul>
     *     <li>Loading</li>
     *     <li>Playing the Move</li>
     *     <li>Checking the State</li>
     *     <li>Storing</li>
     * </ul>
     * Loading uses {@link GameMemory} to load the game based on the given {@code gameId}.
     * Playing the Move uses {@link GameHandler} to apply the move which player chose to the game.
     * Checking the State uses {@link GameStateChecker} to see if the game is finished and if so then does the
     *                          necessary steps.
     * Storing uses {@link GameMemory} to store the game state.
     *
     * @param gameId A game id which player used to play the move on.
     * @param pitId The pit player wants to make the move on.
     * @return The state of the board after move is made.
     */
    public GameState makeMove(Integer gameId, Integer pitId) {
        GameState gameState = gameMemory.loadGame(gameId);
        GameState playedGameState = moveHandler.play(gameState, pitId);
        GameState gameStateAfterCheckFinish = gameStateChecker.checkIfFinished(playedGameState);
        gameMemory.storeGame(gameId, gameStateAfterCheckFinish);
        return gameState;
    }
}
