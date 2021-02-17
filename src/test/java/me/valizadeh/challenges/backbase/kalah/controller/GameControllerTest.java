package me.valizadeh.challenges.backbase.kalah.controller;

import me.valizadeh.challenges.backbase.kalah.exception.EmptyPitException;
import me.valizadeh.challenges.backbase.kalah.exception.GameFinishedException;
import me.valizadeh.challenges.backbase.kalah.exception.GameNotFoundException;
import me.valizadeh.challenges.backbase.kalah.exception.IsNotPlayerTurnException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIdOutOfRangeException;
import me.valizadeh.challenges.backbase.kalah.exception.PitIsKalahException;
import me.valizadeh.challenges.backbase.kalah.handler.GameHandler;
import me.valizadeh.challenges.backbase.kalah.model.Game;
import me.valizadeh.challenges.backbase.kalah.model.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameHandler handler;

    @Test
    public void testCreateGame() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);

        MvcResult mvcResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertNotNull(content);
    }

    @Test
    public void testMakeMove() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(1))).thenReturn(gameState);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        MvcResult playGameResult = this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/1"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(playGameResult.getResponse().getContentAsString());

    }

    @Test
    public void testMakeMoveGameNotFound() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(2))).thenThrow(GameNotFoundException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/2"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));


    }

    @Test
    public void testMakeMoveEmptyPit() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(1))).thenThrow(EmptyPitException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/1"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


    }

    @Test
    public void testMakeMoveGameFinished() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(1))).thenThrow(GameFinishedException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/1"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


    }

    @Test
    public void testMakeMoveIsNotPlayerTurn() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(1))).thenThrow(IsNotPlayerTurnException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/1"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


    }

    @Test
    public void testMakeMovePitIdOutOfRange() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(16))).thenThrow(PitIdOutOfRangeException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/16"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


    }

    @Test
    public void testMakeMovePitIsKalah() throws Exception {
        GameState gameState = new GameState(new Game("localhost", 8086), 2);
        when(handler.createGame()).thenReturn(gameState);
        when(handler.makeMove(eq(gameState.getGame().getId()), eq(7))).thenThrow(PitIsKalahException.class);

        MvcResult createGameResult = this.mockMvc.perform(post("/games")).andExpect(status().isOk())
                .andReturn();

        assertNotNull(createGameResult.getResponse().getContentAsString());

        this.mockMvc.perform(put("/games/" + gameState.getGame().getId() + "/pits/7"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));


    }
}
