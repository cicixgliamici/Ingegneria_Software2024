package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.*;

import org.example.model.ModelController;
import org.example.model.PlayArea.*;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.example.model.PlayArea.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;

public class ModelTest extends TestCase {


    public void testCreateDeck() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
    }

    public void testCreateAllDecks() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
        assertNotNull(ModelController.getResourcesDeck());
        assertNotNull(ModelController.getGoldDeck());
        assertNotNull(ModelController.getStarterDeck());
        assertNotNull(ModelController.getObjectDeck());
    }
    public void testCreateCorrectNumbers() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
        assertEquals(40, ModelController.getResourcesDeck().getCardNumbers());
        assertEquals(40, ModelController.getGoldDeck().getCardNumbers());
        assertEquals(6, ModelController.getStarterDeck().getCardNumbers());
        assertEquals(16, ModelController.getObjectDeck().getCardNumbers());
    }
    public void testAddPlayer() throws Exception {
        ModelController modelController = new ModelController();
        Player player = new Player(null);
        Field field = ModelController.class.getDeclaredField("PlayersList");
        field.setAccessible(true);
        List<Player> playersList = new ArrayList<>();
        field.set(modelController, playersList);
        modelController.AddPlayer(player);
        assertNotNull(modelController);
        assertTrue(playersList.contains(player));
        assertEquals(1, playersList.size());
    }
    public void testmaxPlayers() throws Exception {
        ModelController modelController = new ModelController();
        Player player = new Player(null);
        Field field = ModelController.class.getDeclaredField("PlayersList");
        field.setAccessible(true);
        List<Player> playersList = new ArrayList<>();
        field.set(modelController, playersList);
        modelController.AddPlayer(player);
        modelController.AddPlayer(player);
        modelController.AddPlayer(player);
        modelController.AddPlayer(player);
        assertNotNull(modelController);
        assertTrue(playersList.contains(player));
        assertEquals(4, playersList.size());
    }

    public void testInitializePlayer() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
        Player player = new Player(null);
        modelController.AddPlayer(player);
        modelController.InizitializePlayers();
        List<Card> hand = player.getHand();
        assertEquals(3, player.getHand().size());
        assertEquals(Type.RESOURCES, hand.get(0).getType());
        assertEquals(Type.RESOURCES, hand.get(1).getType());
        assertEquals(Type.GOLD, hand.get(2).getType());
    }

}


