package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class EmptyPitException extends ServiceValidationException {

    private static final String MESSAGE = "The pit {0} is empty and cannot be played!";


    private final Integer pitId;

    public EmptyPitException(Integer pitId) {
        super(MessageFormat.format(MESSAGE, pitId));

        this.pitId = pitId;
    }

    public Integer getPitId() {
        return pitId;
    }
}
