package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.GameNotFoundException;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.predicate.GameExistPredicate;

import java.util.Map;
import java.util.function.BiPredicate;

public class GameExistValidator implements BiPredicate<Map<Integer, GameState>, Integer> {

    private final GameExistPredicate gameExistPredicate;

    public GameExistValidator() {
        this.gameExistPredicate = new GameExistPredicate();
    }


    @Override
    public boolean test(Map<Integer, GameState> gameStates, Integer gameId) {
        if(this.gameExistPredicate.test(gameStates, gameId))
            return true;
        else
            throw new GameNotFoundException(gameId);
    }
}
