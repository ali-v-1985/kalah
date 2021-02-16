package me.valizadeh.challenges.backbase.kalah;

import me.valizadeh.challenges.backbase.kalah.convertor.GameStateToMakeMoveResultConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterRegistry;

@Configuration
@EnableConfigurationProperties(KalahApplicationConfigModel.class)
public class KalahApplicationConfiguration {

    @Autowired
    public void registerConverters(
            ConverterRegistry converterRegistry) {
        converterRegistry.addConverter(new GameStateToMakeMoveResultConvertor());
    }

}
