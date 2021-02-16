package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameValidator {

    private final GameExistValidator gameExistValidator;
    private final GameNotFinishedValidator gameNotFinishedValidator;
    private final PitIdValidator pitIdValidator;
    private final NotKalahValidator notKalahValidator;
    private final IsPlayerTurnValidator playerTurnValidator;
    private final PlayedPitValidator playedPitValidator;

    public GameValidator(@Value("${kalah.pits}") Integer pits,
                         @Value("${kalah.players}") Integer players) {
        this.gameExistValidator = new GameExistValidator();
        this.gameNotFinishedValidator = new GameNotFinishedValidator();
        this.pitIdValidator = new PitIdValidator(pits, players);
        this.notKalahValidator = new NotKalahValidator(pits);
        this.playerTurnValidator = new IsPlayerTurnValidator();
        this.playedPitValidator = new PlayedPitValidator();
    }

    public void validateGame(Map<Integer, GameState> gameStates, Integer gameId) {
        gameExistValidator.test(gameStates, gameId);
        gameNotFinishedValidator.test(gameStates.get(gameId));
    }

    public void validatePitId(Integer pitId) {
        pitIdValidator.test(pitId);
        notKalahValidator.test(pitId);
    }

    public void validatePlayedPit(GameState gameState,
                         Pit playedPit,
                         Integer playerId) {
        playerTurnValidator.test(playerId, gameState.getTurn());
        playedPitValidator.test(playedPit);

    }

}
