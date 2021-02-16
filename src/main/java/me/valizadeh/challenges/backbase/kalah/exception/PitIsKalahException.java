package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class PitIsKalahException extends ServiceValidationException {

    private static final String MESSAGE = "The pit {0} is kalah and cannot be played!";

    private final Integer pitId;

    public PitIsKalahException(Integer pitId) {
        super(MessageFormat.format(MESSAGE, pitId));
        this.pitId = pitId;
    }

    public Integer getPitId() {
        return pitId;
    }
}
