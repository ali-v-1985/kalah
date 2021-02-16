package me.valizadeh.challenges.backbase.kalah;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(KalahApplicationConfiguration.class)
public class KalahApplication {

    public static void main(String... args) {
        SpringApplication.run(KalahApplication.class, args);
    }
}
