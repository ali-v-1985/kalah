package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.IntPredicate;

public class IsKalahPredicate implements IntPredicate {

    private final Integer pits;

    public IsKalahPredicate(Integer pits) {
        this.pits = pits;
    }

    @Override
    public boolean test(int pitId) {
        return   pitId % (pits + 1) == 0;
    }
}
