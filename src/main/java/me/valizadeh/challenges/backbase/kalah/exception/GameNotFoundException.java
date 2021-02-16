package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class GameNotFoundException extends ServiceValidationException {

    private static final String MESSAGE = "The game {0} does not exist!";

    private final Integer gameId;

    public GameNotFoundException(Integer gameId) {
        super(MessageFormat.format(MESSAGE, gameId));
        this.gameId = gameId;
    }

    public Integer getGameId() {
        return gameId;
    }
}
