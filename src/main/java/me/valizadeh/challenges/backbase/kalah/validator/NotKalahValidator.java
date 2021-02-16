package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.PitIsKalahException;
import me.valizadeh.challenges.backbase.kalah.predicate.IsKalahPredicate;

import java.util.function.IntPredicate;

public class NotKalahValidator implements IntPredicate {

    private final IsKalahPredicate isKalahPredicate;

    public NotKalahValidator(Integer pits) {
        this.isKalahPredicate = new IsKalahPredicate(pits);
    }

    @Override
    public boolean test(int pitId) {
        if (this.isKalahPredicate.test(pitId))
            throw new PitIsKalahException(pitId);
        else
            return true;
    }
}
