package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.PitIdOutOfRangeException;
import me.valizadeh.challenges.backbase.kalah.predicate.PitIdPredicate;

import java.util.function.IntPredicate;

public class PitIdValidator implements IntPredicate {

    private final PitIdPredicate pitIdPredicate;

    public PitIdValidator(Integer pits, Integer players) {
        this.pitIdPredicate = new PitIdPredicate(pits, players);
    }


    @Override
    public boolean test(int pitId) {
        if(this.pitIdPredicate.test(pitId))
            return true;
        else
            throw new PitIdOutOfRangeException(pitId);
    }
}
