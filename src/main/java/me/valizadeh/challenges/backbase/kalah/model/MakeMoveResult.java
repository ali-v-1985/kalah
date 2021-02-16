package me.valizadeh.challenges.backbase.kalah.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MakeMoveResult {

    private String id;
    private String uri;
    private boolean finished;
    private int winner;
    private Map<String, String> state;
}
