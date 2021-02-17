package me.valizadeh.challenges.backbase.kalah.utils;

import me.valizadeh.challenges.backbase.kalah.model.Pit;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PitsData {

    public static void assertPits(List<Pit> expectedPits, List<Pit> actualPits) {
        for (int i = 0; i < actualPits.size(); i++) {
            Pit actualPit = actualPits.get(i);
            Pit expectedPit = expectedPits.get(i);
            Assertions.assertEquals(expectedPit.getStones(), actualPit.getStones());

        }
    }

    public static List<Pit> initial() {
        return Arrays.asList(
                new Pit(1, 6),
                new Pit(2, 6),
                new Pit(3, 6),
                new Pit(4, 6),
                new Pit(5, 6),
                new Pit(6, 6),
                new Pit(7, 0),
                new Pit(8, 6),
                new Pit(9, 6),
                new Pit(10, 6),
                new Pit(11, 6),
                new Pit(12, 6),
                new Pit(13, 6),
                new Pit(14, 0)
        );
    }

    public static List<Pit> played1FromInitial() {
        return Arrays.asList(
                new Pit(1, 0),
                new Pit(2, 7),
                new Pit(3, 7),
                new Pit(4, 7),
                new Pit(5, 7),
                new Pit(6, 7),
                new Pit(7, 1),
                new Pit(8, 6),
                new Pit(9, 6),
                new Pit(10, 6),
                new Pit(11, 6),
                new Pit(12, 6),
                new Pit(13, 6),
                new Pit(14, 0)
        );
    }

    public static List<Pit> beforeEmptyToKalah() {
        return Arrays.asList(
                new Pit(1, 0),
                new Pit(2, 9),
                new Pit(3, 3),
                new Pit(4, 0),
                new Pit(5, 5),
                new Pit(6, 3),
                new Pit(7, 7),
                new Pit(8, 5),
                new Pit(9, 13),
                new Pit(10, 11),
                new Pit(11, 1),
                new Pit(12, 11),
                new Pit(13, 0),
                new Pit(14, 4)
        );
    }

    public static List<Pit> afterEmptyToKalah() {
        return Arrays.asList(
                new Pit(1, 1),
                new Pit(2, 10),
                new Pit(3, 4),
                new Pit(4, 1),
                new Pit(5, 0),
                new Pit(6, 4),
                new Pit(7, 7),
                new Pit(8, 6),
                new Pit(9, 0),
                new Pit(10, 12),
                new Pit(11, 2),
                new Pit(12, 12),
                new Pit(13, 1),
                new Pit(14, 12)
        );
    }

    public static Map<String, String> pitsToMap(List<Pit> pits) {
        return pits
                .stream()
                .collect(Collectors
                        .toMap(pit -> String.valueOf(pit.getId()),
                                pit -> String.valueOf(pit.getStones()),
                                (k, v) -> k + ":" + v,
                                () -> new TreeMap<>(getComparator())));
    }

    public static List<Pit> mapToPits(Map<String, String> map) {
        return map.entrySet()
                .stream()
                .map(p -> new Pit(Integer.parseInt(p.getKey()),
                        Integer.parseInt(p.getValue())))
                .collect(Collectors.toList());
    }

    private static Comparator<String> getComparator() {
        return (o1, o2) -> {
            if (o1 == null || o2 == null || o1.equals(o2) || o1.isBlank() || o2.isBlank())
                return 0;
            int delta = Integer.parseInt(o1) - Integer.parseInt(o2);
            return Integer.compare(delta, 0);
        };
    }
}
