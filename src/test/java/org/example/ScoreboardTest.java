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
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Player player3 = new Player("player3");
        Player player4 = new Player("player4");
        Player player5 = new Player("player5");
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
}
