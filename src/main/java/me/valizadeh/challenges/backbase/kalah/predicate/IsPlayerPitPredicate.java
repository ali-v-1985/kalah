package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.BiPredicate;

public class IsPlayerPitPredicate implements BiPredicate<Integer, Integer> {

    private final Integer pits;

    public IsPlayerPitPredicate(Integer pits) {
        this.pits = pits;
    }

    @Override
    public boolean test(Integer playerId, Integer pitId) {
        int playerStartPit = pits * (playerId - 1) + playerId;
        int playerEndPit = pits * (playerId - 1) + (pits + playerId);
        return  pitId >= playerStartPit && pitId <= playerEndPit;
    }
}
