package me.valizadeh.challenges.backbase.kalah.exception;

import java.text.MessageFormat;

public class GameFinishedException extends ServiceValidationException {

    private static final String MESSAGE = "The game {0} is already finished. Player {1} is winner!";

    private final Integer gameId;
    private final Integer winner;

    public GameFinishedException(Integer gameId, Integer winner) {
        super(MessageFormat.format(MESSAGE, gameId));
        this.gameId = gameId;
        this.winner = winner;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Integer getWinner() {
        return winner;
    }
}
