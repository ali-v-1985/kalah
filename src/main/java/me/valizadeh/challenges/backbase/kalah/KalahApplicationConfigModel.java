package me.valizadeh.challenges.backbase.kalah;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kalah")
public class KalahApplicationConfigModel {

    private Integer stones;

    private Integer pits;
}
