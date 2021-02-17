package me.valizadeh.challenges.backbase.kalah.handler;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.validator.LoadGameValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The memory manager of the game which store and retrieve the games.
 *
 * @author Ali
 */
@Component
public class GameMemory {
    private final Map<Integer, GameState> gameStates;

    private final LoadGameValidator validator;

    public GameMemory(LoadGameValidator validator) {
        this.validator = validator;
        this.gameStates = new HashMap<>();
    }

    /**
     * Validating the given game id and if it is a valid game id then load the game.
     *
     * @param gameId The game id which game should be loaded based on.
     * @return The loaded game state for the given id.
     */
    public GameState loadGame(int gameId) {
        this.validator.validateGame(gameStates, gameId);
        return gameStates.get(gameId);
    }

    /**
     * Stores the given game with the id which is passed.
     *
     * @param gameId The id which game will be stored by.
     * @param gameState The state of the game which should be stored.
     */
    public void storeGame(int gameId,
                          GameState gameState) {
        gameStates.put(gameId, gameState);
    }
}
