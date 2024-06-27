package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.enumeration.*;
import org.example.model.playarea.ScoreBoard;
import org.example.controller.*;

import java.io.IOException;
import java.text.ParseException;

/**
 * Test case for the ScoreBoard class.
 */
public class ScoreboardTest extends TestCase {

    /**
     * Tests the addToken method of the ScoreBoard class.
     * Verifies the correct behavior when adding tokens and handling exceptions.
     */
    public void testAddToken() throws IOException, ParseException {
        ScoreBoard scoreboard = new ScoreBoard();
        String u1 = "eren_yeager";
        String u2 = "grisha_yeager";
        String u3 = "zeke_yeager";
        String u4 = "carla_yeager";
        String u5 = "dina_fritz";

        // Adding tokens for three players
        scoreboard.addToken(u1, Color.BLUE);
        scoreboard.addToken(u2, Color.RED);
        scoreboard.addToken(u3, Color.GREEN);

        // Verifying the number of tokens added
        assertEquals(3, scoreboard.getTokens().size());

        // Adding a token with an already used color should throw an exception
        try {
            scoreboard.addToken(u4, Color.RED);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Select another color"));
        }

        // Adding a token with a new color
        scoreboard.addToken(u4, Color.YELLOW);

        // Adding a fifth token should throw an exception as the max players are 4
        try {
            scoreboard.addToken(u5, Color.WHITE);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Max 4 players"));
        }
    }

    /**
     * Tests the updatePlayerPoint method of the ScoreBoard class.
     * Verifies the correct update of player points.
     */
    public void testPoints() throws IOException, ParseException {
        Player player1 = new Player("the_Owl");
        ScoreBoard scoreboard = new ScoreBoard();

        // Updating player points
        scoreboard.updatePlayerPoint(player1, 10);

        // Verifying the points of the player
        assertEquals(10, scoreboard.getPlayerPoint(player1));
    }

    /**
     * Tests the winner method of the ScoreBoard class.
     * Verifies that the correct winner is determined based on the highest points.
     */
    public void testWinner() throws IOException, ParseException {
        Player player1 = new Player("travis_scott");
        Player player2 = new Player("young_thug");
        Player player3 = new Player("twentyone_savage");
        Player player4 = new Player("playboi_carti");
        ScoreBoard scoreboard = new ScoreBoard();

        // Updating points for multiple players
        scoreboard.updatePlayerPoint(player1, 10);
        scoreboard.updatePlayerPoint(player2, 20);
        scoreboard.updatePlayerPoint(player3, 30);
        scoreboard.updatePlayerPoint(player4, 40);

        // Verifying the winner
        assertEquals(player4, scoreboard.winner());
    }

    /**
     * Tests the getters and setters of the ScoreBoard class.
     */
    public void testScoreboardGeTSet(){
        ScoreBoard scoreBoard = new ScoreBoard();
        String u1 = "kanye";
        Player player1 = new Player(u1);
        Player player2 = new Player("donda");

        // Setting and getting points for players
        scoreBoard.getPoints().put(player1, 10);
        scoreBoard.getPoints().put(player2, 15);
        Integer points1 = 10;
        Integer points2 = 15;

        assertEquals(points1, scoreBoard.getPoints().get(player1));
        assertEquals(points2, scoreBoard.getPoints().get(player2));

        // Updating player points
        scoreBoard.updatePlayerPoint(player1, 2);
        Integer points3 = 2;
        assertEquals(points3, scoreBoard.getPoints().get(player1));

        // Adding and getting a token for a player
        scoreBoard.addToken(u1, Color.RED);
        assertEquals(Color.RED, scoreBoard.getTokens().get(u1));
    }
}