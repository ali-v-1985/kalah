package me.valizadeh.challenges.backbase.kalah.handler;

import lombok.SneakyThrows;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.IntStream;

/**
 * The game initiator.
 *
 * @author Ali
 */
@Component
public class GameInitiator {

    static final int KALAH_INITIAL_STONES = 0;

    private final Integer players;
    private final String host;
    private final Integer port;
    private final Integer stones;

    private final Integer pits;


    @SneakyThrows(UnknownHostException.class)
    public GameInitiator(@Value("${kalah.stones}") Integer stones,
                         @Value("${kalah.pits}") Integer pits,
                         @Value("${kalah.players}") Integer players,
                         @Value("${server.port}") Integer port) {
        this.host = InetAddress.getLocalHost().getHostName();
        this.port = port;
        this.players = players;
        this.pits = pits;
        this.stones = stones;
    }

    /**
     * Initiate the game with the configured settings.
     *
     * @return The initial state of the game.
     */
    public GameState initiate() {
        Game game = new Game(host, port);
        return initiateGameBoard(game);
    }

    /**
     * Create the {@link GameState} for the given {@code game} and add the pits based on configuration.
     * @param game The game which a board should be created for.
     * @return The initial game state of the given {@code game}.
     */
    private GameState initiateGameBoard(Game game) {
        GameState gameState = new GameState(game, players);
        IntStream.rangeClosed(1, players).forEach(p -> {
            addPlayerPits(gameState, p);
            addPlayerKalah(gameState, p);
        });
        return gameState;
    }

    private void addPlayerKalah(GameState gameState,
                                int playerId) {
        gameState.getPits().add(new Pit(playerId * pits + playerId, KALAH_INITIAL_STONES));
    }

    private void addPlayerPits(GameState gameState,
                               int playerId) {
        IntStream.rangeClosed(getPlayerStartPit(playerId), getPlayerEndPit(playerId))
                .forEach(i -> gameState.getPits().add(new Pit(i, stones)));
    }

    private int getPlayerStartPit(int playerId) {
        return pits * (playerId - 1) + playerId;
    }

    private int getPlayerEndPit(int playerId) {
        return pits * (playerId - 1) + (pits + (playerId - 1));
    }


}
