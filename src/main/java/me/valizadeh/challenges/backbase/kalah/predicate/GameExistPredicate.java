package me.valizadeh.challenges.backbase.kalah.predicate;

import me.valizadeh.challenges.backbase.kalah.model.GameState;

import java.util.Map;
import java.util.function.BiPredicate;

public class GameExistPredicate implements BiPredicate<Map<Integer, GameState>, Integer> {

    @Override
    public boolean test(Map<Integer, GameState> gameStates, Integer gameId) {
        return gameStates.containsKey(gameId);
    }

}
