package me.valizadeh.challenges.backbase.kalah.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class GameState {

    private final Game game;

    private int winner;

    private boolean finished;

    @EqualsAndHashCode.Exclude
    private int turn;

    @EqualsAndHashCode.Exclude
    private final int players;

    @EqualsAndHashCode.Exclude
    private List<Pit> pits;

    public GameState(Game game, int players) {
        this.game = game;
        this.pits = new ArrayList<>();
        this.players = players;
        this.turn = 1;
        this.winner = 0;
        this.finished = false;
    }

    public void changeTurn() {
        if(this.turn < players)
            this.turn ++;
        else
            this.turn = 1;
    }
}
