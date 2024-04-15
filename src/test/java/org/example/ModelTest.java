package org.example;

import junit.framework.TestCase;

import org.example.controller.Controller;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.enumeration.*;
import org.example.controller.Player;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class ModelTest extends TestCase {
    public void testDealCards() throws IOException, ParseException{
        Model model = new Model();
        Player player1 = new Player("Blur");
        Player player2 = new Player("manuxo");
        model.getPlayersList().add(player1);
        model.getPlayersList().add(player2);
        model.DealCards();
    }
    public void testPlayArea() throws IOException, ParseException {
        Model model = new Model();
        Player p1 = new Player("Blur");
        Player p2 = new Player("manuxo");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        ByteArrayInputStream in = new ByteArrayInputStream("1\n1\n".getBytes());
        Scanner scanner = new Scanner(in);
        System.setIn(in);
        model.setPlayersAndGameArea(players);
    }

}


