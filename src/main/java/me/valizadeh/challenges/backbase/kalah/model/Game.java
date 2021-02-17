package me.valizadeh.challenges.backbase.kalah.model;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class Game {

    private static final String FORMAT_URI = "http://{0}:{1}/games/{2}";

    /**
     * Should be replaced with a better mechanism.
     */
    private static Integer idGenerator = 0;

    private Integer id;

    private String uri;

    public Game(String host, int port) {
        this.id = ++idGenerator;
        this.uri = MessageFormat.format(FORMAT_URI, host, String.valueOf(port), String.valueOf(this.id));
    }
}
