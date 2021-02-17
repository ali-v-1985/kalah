package me.valizadeh.challenges.backbase.kalah.convertor;

import me.valizadeh.challenges.backbase.kalah.model.GameState;
import me.valizadeh.challenges.backbase.kalah.model.MakeMoveResult;
import org.springframework.core.convert.converter.Converter;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * The convertor which is converting an instance of {@link GameState} to an instance of {@link MakeMoveResult}.
 *
 * @author Ali
 */
public class GameStateToMakeMoveResultConvertor implements Converter<GameState, MakeMoveResult> {

    private final Comparator<String> comparator;

    public GameStateToMakeMoveResultConvertor() {
        this.comparator  = getComparator();
    }

    /**
     * The convert using an instance of {@link TreeMap} and {@link Comparator} for the {@code MakeMoveResult#state} filed
     * to make the {@link java.util.Map} ordered.
     *
     * @param gameState The {@link GameState} which should be converted.
     * @return The result of conversion which is an instance of {@link MakeMoveResult}
     */
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

    private Comparator<String> getComparator() {
        return (o1, o2) -> {
            if (o1 == null || o2 == null || o1.equals(o2) || o1.isBlank() || o2.isBlank())
                return 0;
            int delta = Integer.parseInt(o1) - Integer.parseInt(o2);
            return Integer.compare(delta, 0);
        };
    }
}
