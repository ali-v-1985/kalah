package me.valizadeh.challenges.backbase.kalah.predicate;

import me.valizadeh.challenges.backbase.kalah.model.GameState;

import java.util.function.Predicate;

public class GameNotFinishedPredicate implements Predicate<GameState> {

    @Override
    public boolean test(GameState gameState) {
        return !gameState.isFinished();
    }

}
