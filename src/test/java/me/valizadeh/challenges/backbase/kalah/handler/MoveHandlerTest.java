package me.valizadeh.challenges.backbase.kalah.handler;

import me.valizadeh.challenges.backbase.kalah.exception.EmptyPitException;
import me.valizadeh.challenges.backbase.kalah.exception.IsNotPlayerTurnException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIdOutOfRangeException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIsKalahException;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import me.valizadeh.challenges.backbase.kalah.validator.PlayGameValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static me.valizadeh.challenges.backbase.kalah.utils.PitsData.afterEmptyToKalah;
import static me.valizadeh.challenges.backbase.kalah.utils.PitsData.assertPits;
import static me.valizadeh.challenges.backbase.kalah.utils.PitsData.beforeEmptyToKalah;
import static me.valizadeh.challenges.backbase.kalah.utils.PitsData.initial;
import static me.valizadeh.challenges.backbase.kalah.utils.PitsData.played1FromInitial;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MoveHandlerTest {

    MoveHandler underTest;

    PlayGameValidator validator;
    int players = 2;

    @BeforeEach
    void setUp() {
        int pits = 6;
        validator = new PlayGameValidator(pits, players);
        underTest = new MoveHandler(validator, pits, players);
    }

    @Test
    void testPlayInvalidPit() {
        // given
        GameState gameState = mock(GameState.class);
        int pitId = 16;

        // when
        Assertions.assertThrows(PitIdOutOfRangeException.class, () -> underTest.play(gameState, pitId));
    }

    @Test
    void testPlayKalahPit() {
        // given
        GameState gameState = mock(GameState.class);
        int pitId = 7;

        // when
        Assertions.assertThrows(PitIsKalahException.class, () -> underTest.play(gameState, pitId));
    }

    @Test
    void testPlayNotPlayerTurn() {
        // given
        List<Pit> pits = initial();

        GameState gameState = mock(GameState.class);
        when(gameState.getPits()).thenReturn(pits);
        when(gameState.getTurn()).thenReturn(1);
        int pitId = 8;

        // when
        Assertions.assertThrows(IsNotPlayerTurnException.class, () -> underTest.play(gameState, pitId));
    }

    @Test
    void testPlayEmptyPit() {
        // given
        List<Pit> pits = played1FromInitial();

        GameState gameState = mock(GameState.class);
        when(gameState.getPits()).thenReturn(pits);
        when(gameState.getTurn()).thenReturn(1);
        int pitId = 1;

        // when
        Assertions.assertThrows(EmptyPitException.class, () -> underTest.play(gameState, pitId));
    }

    @Test
    void testPlay() {
        // given
        List<Pit> pits = initial();

        Game game = mock(Game.class);
        GameState gameState = new GameState(game, players);
        gameState.setPits(pits);
        int pitId = 1;

        // when
        GameState playedGameState = underTest.play(gameState, pitId);

        // then

        assertPits(played1FromInitial(), playedGameState.getPits());
        assertEquals(1, playedGameState.getTurn());

    }

    @Test
    void testPlayEmptyToKalah() {
        // given
        List<Pit> pits = beforeEmptyToKalah();

        Game game = mock(Game.class);
        GameState gameState = new GameState(game, players);
        gameState.setPits(pits);
        gameState.setTurn(2);
        int pitId = 9;

        // when
        GameState playedGameState = underTest.play(gameState, pitId);

        // then

        assertPits(afterEmptyToKalah(), playedGameState.getPits());
        assertEquals(1, playedGameState.getTurn());

    }



}
