package me.valizadeh.challenges.backbase.kalah.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class Pit {

    private final int id;

    @EqualsAndHashCode.Exclude
    private int stones;

    public void inc() {
        this.stones++;
    }

    public void inc(int stones) {
        this.stones += stones;
    }

    public int empty() {
        var stonesBeforeEmpty = this.stones;
        this.stones = 0;
        return stonesBeforeEmpty;
    }
}
