package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.validator.LoadGameValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameMemory {
    private final Map<Integer, GameState> gameStates;

    private final LoadGameValidator validator;

    public GameMemory(LoadGameValidator validator) {
        this.validator = validator;
        this.gameStates = new HashMap<>();
    }

    public GameState loadGame(Integer gameId) {
        this.validator.validateGame(gameStates, gameId);
        return gameStates.get(gameId);
    }

    public void storeGame(Integer gameId, GameState gameStateAfterCheckFinish) {
        gameStates.put(gameId, gameStateAfterCheckFinish);
    }
}
