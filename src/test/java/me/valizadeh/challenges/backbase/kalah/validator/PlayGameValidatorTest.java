package me.valizadeh.challenges.backbase.kalah.validator;

import lombok.SneakyThrows;
import me.valizadeh.challenges.backbase.kalah.exception.EmptyPitException;
import me.valizadeh.challenges.backbase.kalah.exception.IsNotPlayerTurnException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIdOutOfRangeException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIsKalahException;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayGameValidatorTest {

    PlayGameValidator underTest;

    private GameState gameState;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        Integer pits = 6;
        String host = InetAddress.getLocalHost().getHostName();
        int players = 2;
        underTest = new PlayGameValidator(pits, players);

        int port = 8086;
        gameState = new GameState(new Game(host, port), players);
        GameState gameState2 = new GameState(new Game(host, port), players);
        gameState2.setFinished(true);
    }

    @Test
    void validatePitId() {
        int pitId = 1;
        assertDoesNotThrow(() -> underTest.validatePitId(pitId));
    }

    @Test
    void validatePitIdOutOfRange() {
        int outOfRangePitId = 16;
        assertThrows(PitIdOutOfRangeException.class,
                () -> underTest.validatePitId(outOfRangePitId));
    }

    @Test
    void validatePitIdIsFinished() {
        int kalahPitId = 7;
        assertThrows(PitIsKalahException.class,
                () -> underTest.validatePitId(kalahPitId));
    }

    @Test
    void validatePlayedPit() {
        int playerId = 1;
        Pit pit = new Pit(1, 6);
        assertDoesNotThrow(() -> underTest.validatePlayedPit(gameState, pit, playerId));
    }

    @Test
    void validatePlayedPitNotPlayerTurn() {
        int playerId = 2;
        Pit pit = new Pit(1, 6);
        assertThrows(IsNotPlayerTurnException.class,
                () -> underTest.validatePlayedPit(gameState, pit, playerId));
    }

    @Test
    void validatePlayedPitEmptyPit() {
        int playerId = 1;
        Pit pit = new Pit(1, 0);
        assertThrows(EmptyPitException.class,
                () -> underTest.validatePlayedPit(gameState, pit, playerId));
    }
}
