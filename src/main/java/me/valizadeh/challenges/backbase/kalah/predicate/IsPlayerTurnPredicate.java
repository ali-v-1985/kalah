package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.BiPredicate;

public class IsPlayerTurnPredicate implements BiPredicate<Integer, Integer> {

    @Override
    public boolean test(Integer playerId, Integer gameTurn) {
        return  playerId.intValue() == gameTurn;
    }
}
