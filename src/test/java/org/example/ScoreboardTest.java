package org.example;

import junit.framework.TestCase;
import org.example.enumeration.*;
import org.example.model.playarea.ScoreBoard;
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
        assertEquals(player4, scoreboard.Winner());
    }

    public void testScoreboardGeTSet(){
        ScoreBoard scoreBoard = new ScoreBoard();
        Player player1 = new Player("kanye");
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
        scoreBoard.addToken(Color.RED, player1);
        assertEquals(player1, scoreBoard.getTokens().get(Color.RED));
    }
}
