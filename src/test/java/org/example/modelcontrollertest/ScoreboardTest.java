package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.enumeration.*;
import org.example.model.playarea.ScoreBoard;
import org.example.controller.*;

import java.io.IOException;
import java.text.ParseException;

public class ScoreboardTest extends TestCase {
    public void testAddToken() throws IOException, ParseException {
        ScoreBoard scoreboard = new ScoreBoard();
        String u1 = "eren_yeager";
        String u2 = "grisha_yeager";
        String u3 = "zeke_yeager";
        String u4 = "carla_yeager";
        String u5 = "dina_fritz";
        scoreboard.addToken(u1, Color.BLUE);
        scoreboard.addToken(u2, Color.RED);
        scoreboard.addToken(u3, Color.GREEN);
        assertEquals(3, scoreboard.getTokens().size());
        try {
            scoreboard.addToken(u4, Color.RED);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Select another color"));
        }
        scoreboard.addToken(u4, Color.YELLOW);
        try {
            scoreboard.addToken(u5, Color.WHITE);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Max 4 players"));
        }
    }

    public void testPoints() throws IOException, ParseException {
        Player player1 = new Player("the_Owl");
        ScoreBoard scoreboard = new ScoreBoard();
        scoreboard.updatePlayerPoint(player1, 10);
        assertEquals(10, scoreboard.getPlayerPoint(player1));
    }

    public void testWinner() throws IOException, ParseException {
        Player player1 = new Player("travis_scott");
        Player player2 = new Player("young_thug");
        Player player3 = new Player("twentyone_savage");
        Player player4 = new Player("playboi_carti");
        ScoreBoard scoreboard = new ScoreBoard();
        scoreboard.updatePlayerPoint(player1, 10);
        scoreboard.updatePlayerPoint(player2, 20);
        scoreboard.updatePlayerPoint(player3, 30);
        scoreboard.updatePlayerPoint(player4, 40);
        assertEquals(player4, scoreboard.winner());
    }

    public void testScoreboardGeTSet(){
        ScoreBoard scoreBoard = new ScoreBoard();
        String u1 = "kanye";
        Player player1 = new Player(u1);
        Player player2 = new Player("donda");
        scoreBoard.getPoints().put(player1, 10);
        scoreBoard.getPoints().put(player2, 15);
        Integer points1 = 10;
        Integer points2 = 15;
        assertEquals(points1, scoreBoard.getPoints().get(player1));
        assertEquals(points2, scoreBoard.getPoints().get(player2));
        scoreBoard.updatePlayerPoint(player1, 2);
        Integer points3 = 2;
        assertEquals(points3, scoreBoard.getPoints().get(player1));
        scoreBoard.addToken(u1, Color.RED);
        assertEquals(Color.RED, scoreBoard.getTokens().get(u1));
    }
}
