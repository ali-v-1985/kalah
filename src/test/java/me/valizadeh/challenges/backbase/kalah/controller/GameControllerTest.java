package me.valizadeh.challenges.backbase.kalah.controller;

import me.valizadeh.challenges.backbase.kalah.model.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class GameControllerTest {

    @Autowired
    private GameController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnDefaultMessage() {
        Game response = this.restTemplate.getForObject("http://localhost:" + port + "/games",
                Game.class);
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getUri());
    }
}
