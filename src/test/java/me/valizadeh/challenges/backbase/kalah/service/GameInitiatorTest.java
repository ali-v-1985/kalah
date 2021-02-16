package me.valizadeh.challenges.backbase.kalah.service;

import lombok.SneakyThrows;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.List;

import static me.valizadeh.challenges.backbase.kalah.service.GameInitiator.KALAH_INITIAL_STONES;
import static org.junit.jupiter.api.Assertions.*;

class GameInitiatorTest {

    private static final String FORMAT_URI = "http://{0}:{1}/games/{2}";

    private GameInitiator underTest;
    private final Integer mockedStones = 6;
    private final Integer mockedPits = 6;
    private final Integer mockedPlayer = 2;
    private final Integer mockedPort = 8086;
    private String host;


    @SneakyThrows
    @BeforeEach
    void setUp() {
        this.host = InetAddress.getLocalHost().getHostName();
        underTest = new GameInitiator(mockedStones,
                mockedPits,
                mockedPlayer,
                mockedPort);
    }

    @Test
    void testInitiateCheckGameState() {
        // given

        // when
        GameState initiatedGameState = underTest.initiate();
        // then
        assertNotNull(initiatedGameState);
        assertEquals(1, initiatedGameState.getTurn());
        assertEquals(mockedPlayer, initiatedGameState.getPlayers());
        assertEquals(0, initiatedGameState.getWinner());
    }

    @Test
    void testInitiateCheckGame() {
        // given

        // when
        GameState initiatedGameState = underTest.initiate();
        // then
        assertNotNull(initiatedGameState);
        Game game = initiatedGameState.getGame();
        assertNotNull(game);
        assertNotNull(game.getUri());
        assertEquals(MessageFormat.format(FORMAT_URI, host,
                String.valueOf(mockedPort),
                game.getId()), game.getUri());

    }

    @Test
    void testInitiateCheckPits() {
        // given
        int pitsCount = mockedPits * mockedPlayer + mockedPlayer;

        // when
        GameState initiatedGameState = underTest.initiate();
        // then
        assertNotNull(initiatedGameState);
        List<Pit> pits = initiatedGameState.getPits();
        assertNotNull(pits);
        assertFalse(pits.isEmpty());
        assertEquals(pitsCount,
                pits.size());

        pits.forEach(p -> {
            if(p.getId() % (mockedPits + 1) == 0) {
                assertEquals(KALAH_INITIAL_STONES, p.getStones());
            } else {
                assertEquals(mockedStones, p.getStones());
            }
        });
    }
}
