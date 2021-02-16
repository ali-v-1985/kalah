package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.BiPredicate;

public class IsPlayerKalahPitPredicate implements BiPredicate<Integer, Integer> {

    private IsPlayerPitPredicate isPlayerPitPredicate;
    private IsKalahPredicate isKalahPredicate;

    public IsPlayerKalahPitPredicate(Integer pits) {
        this.isPlayerPitPredicate = new IsPlayerPitPredicate(pits);
        this.isKalahPredicate = new IsKalahPredicate(pits);
    }

    @Override
    public boolean test(Integer playerId, Integer pitId) {
        return   isKalahPredicate.test(pitId) &&
                isPlayerPitPredicate.test(playerId, pitId);
    }
}
