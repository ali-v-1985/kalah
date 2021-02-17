package me.valizadeh.challenges.backbase.kalah.validator;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PlayGameValidator {

    private final PitIdValidator pitIdValidator;
    private final NotKalahValidator notKalahValidator;
    private final IsPlayerTurnValidator playerTurnValidator;
    private final PlayedPitValidator playedPitValidator;

    public PlayGameValidator(@Value("${kalah.pits}") Integer pits,
                             @Value("${kalah.players}") Integer players) {
        this.pitIdValidator = new PitIdValidator(pits, players);
        this.notKalahValidator = new NotKalahValidator(pits);
        this.playerTurnValidator = new IsPlayerTurnValidator();
        this.playedPitValidator = new PlayedPitValidator();
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
