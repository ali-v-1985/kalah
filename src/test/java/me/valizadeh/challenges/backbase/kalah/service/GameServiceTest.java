package me.valizadeh.challenges.backbase.kalah.service;

import lombok.SneakyThrows;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    private GameState gameState;

    @InjectMocks
    GameService gameService;

    @Mock
    GameInitiator gameInitiator;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        String host = InetAddress.getLocalHost().getHostName();
        int players = 2;

        int port = 8086;
        gameState = new GameState(new Game(host, port), players);

    }

    @Test
    void createGame() {

        when(gameInitiator.initiate()).thenReturn(gameState);

        GameState createdGameState = gameService.createGame();

        verify(gameInitiator).initiate();
        assertEquals(gameState, createdGameState);
    }

    @Test
    void makeMove() {
    }
}
