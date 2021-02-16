package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class IsNotPlayerPitException extends ServiceValidationException {

    private static final String MESSAGE = "The pit {0} is not player to play!";

    private final Integer pitId;

    public IsNotPlayerPitException(Integer pitId) {
        super(MessageFormat.format(MESSAGE, pitId));
        this.pitId = pitId;
    }

    public Integer getPitId() {
        return pitId;
    }
}
