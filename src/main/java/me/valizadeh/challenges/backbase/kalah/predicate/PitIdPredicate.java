package me.valizadeh.challenges.backbase.kalah.predicate;

import java.util.function.IntPredicate;

public class PitIdPredicate implements IntPredicate {

    private final Integer pits;
    private final Integer players;

    public PitIdPredicate(Integer pits, Integer players) {
        this.pits = pits;
        this.players = players;
    }

    @Override
    public boolean test(int pitId) {
        return pitId >= 1 && pitId <= pits * players + players;
    }
}
