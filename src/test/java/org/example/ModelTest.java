package org.example;

import junit.framework.TestCase;

import org.example.model.Model;
import org.example.controller.Player;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class ModelTest extends TestCase {
    public void testDealCards() throws IOException, ParseException{
        Model model = new Model();
        Player player1 = new Player("Blur", 1);
        Player player2 = new Player("manuxo", 2);
        model.getPlayersList().add(player1);
        model.getPlayersList().add(player2);
        model.DealCards();
    }
    public void testPlayArea() throws IOException, ParseException {
        Model model = new Model();
        Player p1 = new Player("Blur", 1);
        Player p2 = new Player("manuxo", 2);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        ByteArrayInputStream in = new ByteArrayInputStream("1\n1\n".getBytes());
        Scanner scanner = new Scanner(in);
        System.setIn(in);
        model.setPlayersAndGameArea(players);
    }

}


