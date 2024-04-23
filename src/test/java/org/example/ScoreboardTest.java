package org.example;

import junit.framework.TestCase;
import org.example.enumeration.*;
import org.example.model.PlayArea.ScoreBoard;
import org.example.controller.*;

import java.io.IOException;
import java.text.ParseException;

public class ScoreboardTest extends TestCase {
    public void testAddToken() throws IOException, ParseException {
        ScoreBoard scoreboard = new ScoreBoard();
        Player player1 = new Player("eren_yeager");
        Player player2 = new Player("grisha_yeager");
        Player player3 = new Player("zeke_yeager");
        Player player4 = new Player("carla_yeager");
        Player player5 = new Player("dina_fritz");
        scoreboard.addToken(Color.BLUE, player1);
        scoreboard.addToken(Color.RED, player2);
        scoreboard.addToken(Color.GREEN, player3);
        assertEquals(3, scoreboard.getTokens().size());
        scoreboard.addToken(Color.RED, player4);
        fail("Expected IllegalArgumentException1 was not thrown");
        scoreboard.addToken(Color.YELLOW, player4);
        scoreboard.addToken(Color.WHITE, player5);
        fail("Expected IllegalArgumentException2 was not thrown");
    }

    public void testPoints() throws IOException, ParseException {
        Player player1 = new Player("the_Owl");
        ScoreBoard scoreboard = new ScoreBoard();
        scoreboard.UpdatePlayerPoint(player1, 10);
        assertEquals(10, scoreboard.getPlayerPoint(player1));
    }

    public void testWinner() throws IOException, ParseException {
        Player player1 = new Player("travis_scott");
        Player player2 = new Player("young_thug");
        Player player3 = new Player("twentyone_savage");
        Player player4 = new Player("playboi_carti");
        ScoreBoard scoreboard = new ScoreBoard();
        scoreboard.UpdatePlayerPoint(player1, 10);
        scoreboard.UpdatePlayerPoint(player2, 20);
        scoreboard.UpdatePlayerPoint(player3, 30);
        scoreboard.UpdatePlayerPoint(player4, 40);
        assertEquals(player4, scoreboard.Winner());
        
    }
}
