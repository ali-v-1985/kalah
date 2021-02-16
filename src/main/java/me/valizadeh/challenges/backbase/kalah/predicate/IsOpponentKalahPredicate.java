package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.BiPredicate;

public class IsOpponentKalahPredicate implements BiPredicate<Integer, Integer> {

    private final Integer pits;

    private final IsKalahPredicate kalahPredicate;

    public IsOpponentKalahPredicate(Integer pits) {
        this.pits = pits;
        this.kalahPredicate = new IsKalahPredicate(pits);
    }

    @Override
    public boolean test(Integer pitId, Integer playerId) {
        boolean isKalah = kalahPredicate.test(pitId);
        return isKalah && pits * playerId + playerId != pitId;
    }
}
