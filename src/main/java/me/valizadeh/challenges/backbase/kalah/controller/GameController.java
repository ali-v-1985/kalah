package me.valizadeh.challenges.backbase.kalah.controller;

import me.valizadeh.challenges.backbase.kalah.handler.GameHandler;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.MakeMoveResult;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The controller class which is exposing the Kalah game api.
 * This api contains two functionalities.
 * {@link this#createGame} and {@link this#makeMove}
 * The {@link this#createGame} functionality is creating a game with
 * the configured numbers of {@code players} and {@code pits}
 * The {@link this#makeMove} functionality let the players move its stones across the board on a specific game.
 *
 * @author Ali
 */
@RestController
public class GameController {

    private final GameHandler gameHandler;
    private final ConversionService conversionService;

    /**
     *
     * @param gameHandler The handler of game.
     *                    It defines what any of the functionalities mean.
     * @param conversionService Conversion service for any conversion which is needed.
     */
    public GameController(GameHandler gameHandler,
                          ConversionService conversionService) {
        this.gameHandler = gameHandler;
        this.conversionService = conversionService;
    }

    /**
     * This functionality is creating a game by {@link GameHandler}
     *
     * @return An instance of the created game.
     */
    @PostMapping("/games")
    public Game createGame() {
        GameState gameState = gameHandler.createGame();
        return gameState.getGame();
    }

    /**
     * Making a move on a specific game using {@link GameHandler}
     *
     * @param gameId The id of the game which the move should be made on.
     * @param pitId The pit which should be played.
     * @return The state of the game after move has been made.
     *         The {@link GameHandler#makeMove} will return an instance of {@link GameState}
     *         which will be converted using {@link ConversionService} and
     *         {@link me.valizadeh.challenges.backbase.kalah.convertor.GameStateToMakeMoveResultConvertor}
     *         to an instance of {@link MakeMoveResult}
     */
    @PutMapping("/games/{gameId}/pits/{pitId}")
    public MakeMoveResult makeMove(@PathVariable @NotNull @NotEmpty Integer gameId,
                                   @PathVariable @NotNull @NotEmpty Integer pitId) {

        GameState gameState = gameHandler.makeMove(gameId, pitId);
        return conversionService.convert(gameState, MakeMoveResult.class);
    }
}
