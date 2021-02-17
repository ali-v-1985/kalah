package me.valizadeh.challenges.backbase.kalah.service;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.validator.LoadGameValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameMemoryTest {

    @InjectMocks
    GameMemory underTest;

    @Mock
    LoadGameValidator validator;

    @Test
    void loadGame() {
        Integer gameId = 1;
        assertDoesNotThrow(() -> underTest.loadGame(gameId));
        verify(validator).validateGame(any(), eq(gameId));
    }

    @Test
    void storeGame() {
        Integer gameId = 1;
        GameState gameState = mock(GameState.class);
        assertDoesNotThrow(() -> underTest.storeGame(gameId, gameState));
    }
}
