package me.valizadeh.challenges.backbase.kalah.controller;

import me.valizadeh.challenges.backbase.kalah.exception.GameNotFoundException;
import me.valizadeh.challenges.backbase.kalah.exception.ServiceValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The advice which is handling game exceptions.
 */
@ControllerAdvice
public class GameExceptionHandler {

    @ExceptionHandler({ServiceValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleServiceValidationException(ServiceValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler({GameNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGameNotFoundException(GameNotFoundException e) {
        return e.getMessage();
    }
}
