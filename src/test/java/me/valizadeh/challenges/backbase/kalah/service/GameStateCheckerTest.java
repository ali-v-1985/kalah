package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameStateCheckerTest {
    GameStateChecker underTest;

    @BeforeEach
    void setUp() {
        underTest = new GameStateChecker(6, 2);
    }

    @Test
    void testNotFinishedGameState() {
        List<Pit> notFinishedPits = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 6; j++) {
                Pit pit = new Pit(j + (6 * (i - 1)), 6);
                notFinishedPits.add(pit);
            }
            Pit kalah = new Pit(i * 7, 0);
            notFinishedPits.add(kalah);
        }
        GameState gameState = mock(GameState.class);
        gameState.setFinished(false);
        gameState.setWinner(0);
        when(gameState.getPits()).thenReturn(notFinishedPits);

        GameState checkedGameState = underTest.checkIfFinished(gameState);

        assertNotNull(checkedGameState);
        assertFalse(checkedGameState.isFinished());
        assertEquals(0, checkedGameState.getWinner());
    }

    @Test
    void testFinishedGameTieState() {
        List<Pit> finishedPitsTie = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            for (int j = 1; j <= 6; j++) {
                Pit pit = new Pit(j + (6 * (i - 1)), 0);
                finishedPitsTie.add(pit);
            }
            Pit kalah = new Pit(i * 7, 36);
            finishedPitsTie.add(kalah);
        }
        Game game = new Game(null, 0);
        GameState gameState = new GameState(game, 2);
        gameState.setFinished(false);
        gameState.setWinner(0);
        gameState.setPits(finishedPitsTie);

        GameState checkedGameState = underTest.checkIfFinished(gameState);

        assertNotNull(checkedGameState);
        assertTrue(checkedGameState.isFinished());
        assertEquals(0, checkedGameState.getWinner());
    }

    @Test
    void testFinishedGameWinnerState() {
        List<Pit> finishedPitsWithWinner = new ArrayList<>();
        int f = -1;
        for (int i = 1; i <= 2; i++) {
            f *= -1;
            for (int j = 1; j <= 6; j++) {
                Pit pit = new Pit(j + (6 * (i - 1)), 0);
                finishedPitsWithWinner.add(pit);
            }
            Pit kalah = new Pit(i * 7, 36 + (f));
            finishedPitsWithWinner.add(kalah);
        }
        Game game = new Game(null, 0);
        GameState gameState = new GameState(game, 2);
        gameState.setFinished(false);
        gameState.setWinner(0);
        gameState.setPits(finishedPitsWithWinner);

        GameState checkedGameState = underTest.checkIfFinished(gameState);

        assertNotNull(checkedGameState);
        assertTrue(checkedGameState.isFinished());
        assertEquals(1, checkedGameState.getWinner());
    }

}
