package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class PitIdOutOfRangeException extends ServiceValidationException {

    private static final String MESSAGE = "Pit Id {0} is not in the game range.";

    private final int pitId;

    public PitIdOutOfRangeException(int pitId) {
        super(MessageFormat.format(MESSAGE, pitId));
        this.pitId = pitId;
    }

    public int getPitId() {
        return pitId;
    }
}
