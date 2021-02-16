package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.GameFinishedException;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.predicate.GameNotFinishedPredicate;

import java.util.function.Predicate;

public class GameNotFinishedValidator implements Predicate<GameState> {

    private final GameNotFinishedPredicate gameNotFinishedPredicate;

    public GameNotFinishedValidator() {
        this.gameNotFinishedPredicate = new GameNotFinishedPredicate();
    }


    @Override
    public boolean test(GameState gameState) {
        if(this.gameNotFinishedPredicate.test(gameState))
            return true;
        else
            throw new GameFinishedException(gameState.getGame().getId(),
                    gameState.getWinner());
    }
}
