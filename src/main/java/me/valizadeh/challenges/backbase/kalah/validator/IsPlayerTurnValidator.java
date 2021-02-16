package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.IsNotPlayerTurnException;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerTurnPredicate;

import java.util.function.BiPredicate;

public class IsPlayerTurnValidator implements BiPredicate<Integer, Integer> {

    private final IsPlayerTurnPredicate isPlayerPitPredicate;

    public IsPlayerTurnValidator() {
        this.isPlayerPitPredicate = new IsPlayerTurnPredicate();
    }

    @Override
    public boolean test(Integer playerId, Integer gameTurn) {
        if(this.isPlayerPitPredicate.test(playerId, gameTurn))
            return true;
        else
            throw new IsNotPlayerTurnException(playerId, gameTurn);
    }
}
