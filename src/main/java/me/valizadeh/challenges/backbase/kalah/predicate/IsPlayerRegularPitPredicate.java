package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.BiPredicate;

public class IsPlayerRegularPitPredicate implements BiPredicate<Integer, Integer> {

    private IsPlayerPitPredicate isPlayerPitPredicate;
    private IsKalahPredicate isKalahPredicate;

    public IsPlayerRegularPitPredicate(Integer pits) {
        this.isPlayerPitPredicate = new IsPlayerPitPredicate(pits);
        this.isKalahPredicate = new IsKalahPredicate(pits);
    }

    @Override
    public boolean test(Integer playerId, Integer pitId) {
        return   isKalahPredicate.negate().test(pitId) &&
                isPlayerPitPredicate.test(playerId, pitId);
    }
}
