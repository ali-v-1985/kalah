package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LoadGameValidator {

    private final GameExistValidator gameExistValidator;
    private final GameNotFinishedValidator gameNotFinishedValidator;

    public LoadGameValidator() {
        this.gameExistValidator = new GameExistValidator();
        this.gameNotFinishedValidator = new GameNotFinishedValidator();
    }

    public void validateGame(Map<Integer, GameState> gameStates, Integer gameId) {
        gameExistValidator.test(gameStates, gameId);
        gameNotFinishedValidator.test(gameStates.get(gameId));
    }

}
