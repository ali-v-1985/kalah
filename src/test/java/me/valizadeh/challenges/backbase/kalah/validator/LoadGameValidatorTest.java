package me.valizadeh.challenges.backbase.kalah.validator;

import lombok.SneakyThrows;
import me.valizadeh.challenges.backbase.kalah.exception.GameFinishedException;
import me.valizadeh.challenges.backbase.kalah.exception.GameNotFoundException;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoadGameValidatorTest {

    LoadGameValidator underTest;
    private final int gameId = 1;
    private final int notExistGameId = 2;
    private final int finishedGameId = 3;

    private Map<Integer, GameState> gameStates;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        String host = InetAddress.getLocalHost().getHostName();
        int players = 2;
        underTest = new LoadGameValidator();

        gameStates = new HashMap<>();
        int port = 8086;
        GameState gameState1 = new GameState(new Game(host, port), players);
        GameState gameState2 = new GameState(new Game(host, port), players);
        gameState2.setFinished(true);
        gameStates.put(gameId, gameState1);
        gameStates.put(finishedGameId, gameState2);
    }

    @Test
    void validateGame() {
        assertDoesNotThrow(() -> underTest.validateGame(gameStates, gameId));
    }

    @Test
    void validateGameDoesNotExist() {
        assertThrows(GameNotFoundException.class,
                () -> underTest.validateGame(gameStates, notExistGameId));
    }

    @Test
    void validateGameIsFinished() {
        assertThrows(GameFinishedException.class,
                () -> underTest.validateGame(gameStates, finishedGameId));
    }
}
