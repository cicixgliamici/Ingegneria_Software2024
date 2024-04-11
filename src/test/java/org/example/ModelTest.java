package org.example;

import junit.framework.TestCase;

import org.example.model.Model;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.controller.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;

public class ModelTest extends TestCase {


    public void testCreateDeck() throws IOException, ParseException {
        Model model = new Model();
        model.CreateDeck();
        assertNotNull(model);
    }

    public void testCreateAllDecks() throws IOException, ParseException {
        Model model = new Model();
        model.CreateDeck();
        assertNotNull(model);
        assertNotNull(Model.getResourcesDeck());
        assertNotNull(Model.getGoldDeck());
        assertNotNull(Model.getStarterDeck());
        assertNotNull(Model.getObjectDeck());
    }
    public void testCreateCorrectNumbers() throws IOException, ParseException {
        Model model = new Model();
        model.CreateDeck();
        assertNotNull(model);
        assertEquals(40, Model.getResourcesDeck().getCardNumbers());
        assertEquals(40, Model.getGoldDeck().getCardNumbers());
        assertEquals(6, Model.getStarterDeck().getCardNumbers());
        assertEquals(16, Model.getObjectDeck().getCardNumbers());
    }
    public void testAddPlayer() throws Exception {
        Model model = new Model();
        Player player = new Player(null);
        Field field = Model.class.getDeclaredField("PlayersList");
        field.setAccessible(true);
        List<Player> playersList = new ArrayList<>();
        field.set(model, playersList);
        model.AddPlayer(player);
        assertNotNull(model);
        assertTrue(playersList.contains(player));
        assertEquals(1, playersList.size());
    }
    public void testmaxPlayers() throws Exception {
        Model model = new Model();
        Player player = new Player(null);
        Field field = Model.class.getDeclaredField("PlayersList");
        field.setAccessible(true);
        List<Player> playersList = new ArrayList<>();
        field.set(model, playersList);
        model.AddPlayer(player);
        model.AddPlayer(player);
        model.AddPlayer(player);
        model.AddPlayer(player);
        assertNotNull(model);
        assertTrue(playersList.contains(player));
        assertEquals(4, playersList.size());
    }

    public void testInitializePlayer() throws IOException, ParseException {
        Model model = new Model();
        model.CreateDeck();
        assertNotNull(model);
        Player player = new Player(null);
        model.AddPlayer(player);
        model.InizitializePlayers();
        List<Card> hand = player.getHand();
        assertEquals(3, player.getHand().size());
        assertEquals(Type.RESOURCES, hand.get(0).getType());
        assertEquals(Type.RESOURCES, hand.get(1).getType());
        assertEquals(Type.GOLD, hand.get(2).getType());
    }

}


