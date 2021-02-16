package me.valizadeh.challenges.backbase.kalah.predicate;

import me.valizadeh.challenges.backbase.kalah.model.Pit;

import java.util.function.Predicate;

public class EmptyPitPredicate implements Predicate<Pit> {
    @Override
    public boolean test(Pit pit) {
        return pit.getStones() == 0;
    }
}
