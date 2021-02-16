package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.exception.EmptyPitException;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import me.valizadeh.challenges.backbase.kalah.predicate.EmptyPitPredicate;

import java.util.function.Predicate;

public class PlayedPitValidator implements Predicate<Pit> {

    private final EmptyPitPredicate emptyPitPredicate;

    public PlayedPitValidator() {
        this.emptyPitPredicate = new EmptyPitPredicate();
    }

    @Override
    public boolean test(Pit playedPit) {
        if(this.emptyPitPredicate.test(playedPit))
            throw new EmptyPitException(playedPit.getId());
        else
            return true;
    }
}
