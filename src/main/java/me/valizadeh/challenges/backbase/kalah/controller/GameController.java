package me.valizadeh.challenges.backbase.kalah.controller;

import me.valizadeh.challenges.backbase.kalah.exception.GameNotFoundException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIdOutOfRangeException;
import me.valizadeh.challenges.backbase.kalah.exception.ServiceValidationException;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.MakeMoveResult;
import me.valizadeh.challenges.backbase.kalah.service.GameService;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
public class GameController {

    private final GameService gameService;
    private final ConversionService conversionService;

    public GameController(GameService gameService, ConversionService conversionService) {
        this.gameService = gameService;
        this.conversionService = conversionService;
    }

    @PostMapping("/games")
    public Game createGame() {
        GameState gameState = gameService.createGame();
        return gameState.getGame();
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    public MakeMoveResult makeMove(@PathVariable @NotNull @NotEmpty Integer gameId,
                                   @PathVariable @NotNull @NotEmpty Integer pitId) {

        GameState gameState = gameService.makeMove(gameId, pitId);
        return conversionService.convert(gameState, MakeMoveResult.class);
    }

    @ExceptionHandler({ServiceValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceValidationException(ServiceValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler({GameNotFoundException.class,
            PitIdOutOfRangeException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGameNotFoundException(GameNotFoundException e) {
        return e.getMessage();
    }
}
