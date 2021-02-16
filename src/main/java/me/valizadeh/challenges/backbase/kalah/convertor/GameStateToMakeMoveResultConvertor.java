package me.valizadeh.challenges.backbase.kalah.convertor;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.MakeMoveResult;
import org.springframework.core.convert.converter.Converter;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class GameStateToMakeMoveResultConvertor implements Converter<GameState, MakeMoveResult> {

    private final Comparator<String> comparator;

    public GameStateToMakeMoveResultConvertor() {
        this.comparator  = (o1, o2) -> {
            if(o1 == null || o2 == null || o1.equals(o2) || o1.isBlank() || o2.isBlank())
                return 0;
            int delta = Integer.parseInt(o1) - Integer.parseInt(o2);
            return Integer.compare(delta, 0);
        };
    }

    @Override
    public MakeMoveResult convert(GameState gameState) {
        SortedMap<String, String> state = gameState.getPits()
                .stream()
                .collect(Collectors
                        .toMap(pit -> String.valueOf(pit.getId()),
                                pit -> String.valueOf(pit.getStones()),
                                (k, v) -> k + ":" + v,
                                ()-> new TreeMap<>(comparator)));
        return MakeMoveResult.builder()
                .id(String.valueOf(gameState.getGame().getId()))
                .uri(gameState.getGame().getUri())
                .state(state)
                .finished(gameState.isFinished())
                .winner(gameState.getWinner())
                .build();
    }
}
