package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    GameService underTest;

    @Mock
    GameInitiator gameInitiator;

    @Mock
    GameMemory gameMemory;

    @Mock
    MoveExecutor moveExecutor;

    @Mock
    GameStateChecker gameStateChecker;

    @Test
    void createGame() {

        // given
        GameState gameState = mock(GameState.class);
        Game game = mock(Game.class);
        Integer gameId = 1;
        when(game.getId()).thenReturn(gameId);
        when(gameState.getGame()).thenReturn(game);
        when(gameInitiator.initiate()).thenReturn(gameState);

        // when
        GameState createdGameState = underTest.createGame();

        // then
        verify(gameInitiator).initiate();
        verify(gameMemory).storeGame(eq(gameId), eq(gameState));
        assertEquals(gameState, createdGameState);
    }

    @Test
    void makeMove() {
        // given
        GameState gameState = mock(GameState.class);
        GameState gameStateAfterPlay = mock(GameState.class);
        Integer gameId = 1;

        when(gameMemory.loadGame(eq(gameId))).thenReturn(gameState);
        Integer pitId = 1;
        when(moveExecutor.play(eq(gameState), eq(pitId))).thenReturn(gameStateAfterPlay);
        when(gameStateChecker.checkIfFinished(eq(gameStateAfterPlay))).thenReturn(gameStateAfterPlay);


        // when
        GameState playedGameState = underTest.makeMove(gameId, pitId);

        // then
        verify(gameMemory).loadGame(eq(gameId));
        verify(moveExecutor).play(eq(gameState), eq(pitId));
        verify(gameStateChecker).checkIfFinished(eq(gameStateAfterPlay));
        verify(gameMemory).storeGame(eq(gameId), eq(gameStateAfterPlay));
        assertEquals(gameState, playedGameState);
    }
}
