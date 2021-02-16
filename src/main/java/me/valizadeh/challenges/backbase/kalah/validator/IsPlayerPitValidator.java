package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.IsNotPlayerPitException;
import me.valizadeh.challenges.backbase.kalah.predicate.IsPlayerPitPredicate;

import java.util.function.BiPredicate;

public class IsPlayerPitValidator implements BiPredicate<Integer, Integer> {

    private final IsPlayerPitPredicate isPlayerPitPredicate;

    public IsPlayerPitValidator(Integer pits) {
        this.isPlayerPitPredicate = new IsPlayerPitPredicate(pits);
    }

    @Override
    public boolean test(Integer playerId, Integer pitId) {
        if(this.isPlayerPitPredicate.test(playerId, pitId))
            return true;
        else
            throw new IsNotPlayerPitException(pitId);
    }
}
