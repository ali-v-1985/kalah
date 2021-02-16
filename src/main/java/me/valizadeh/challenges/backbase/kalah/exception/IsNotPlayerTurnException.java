package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class IsNotPlayerTurnException extends ServiceValidationException {
    private static final String MESSAGE = "It is not player {0} turn. Player {1} should play!";

    private final Integer playerId;
    private final Integer gameTurn;

    public IsNotPlayerTurnException(Integer playerId, Integer gameTurn) {
        super(MessageFormat.format(MESSAGE, playerId, gameTurn));
        this.playerId = playerId;
        this.gameTurn = gameTurn;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public Integer getGameTurn() {
        return gameTurn;
    }
}
