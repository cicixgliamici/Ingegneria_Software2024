package org.example;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.Side;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayerTest extends TestCase {
    public void testConstructor() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Player player = new Player(starterCard);
        assertNotNull(player);
        assertNotNull(player.getHand());
        assertEquals(0, player.getHand().size());
    }
/*  DA RIFARE PER CAMBIO METODI
    public void testDrawCard() throws IOException, ParseException {
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        Deck starterDeck = new Deck(Type.STARTER);
        Deck resourcesDeck = new Deck(Type.RESOURCES);
        Card starterCard = starterDeck.drawCard();
        Card resourcesCard = resourcesDeck.drawCard();
        Player player = new Player(starterCard);
        player.addCard(resourcesCard);
        player.addCard(resourcesCard);
        player.drawCard(drawingCardArea, Type.GOLD);
        assertEquals(3, player.getHand().size());
    }
*/

    public void testInitializeGameAreaFront() throws IOException, ParseException{
        String input = "1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Player player = new Player(starterCard);
        assertNotNull(player);
        player.InitializeGameArea();
        assertNotNull(player.getGameArea());
        assertEquals(Side.FRONT, starterCard.getSide().getSide());
    }
    public void testInitializeGameAreaBack() throws IOException, ParseException{
        String input = "2";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Player player = new Player(starterCard);
        assertNotNull(player);
        player.InitializeGameArea();
        assertNotNull(player.getGameArea());
        assertEquals(Side.BACK, starterCard.getSide().getSide());
    }
    public void testChooseACard() throws IOException, ParseException{
        Deck starterDeck = new Deck(Type.STARTER);
        Deck resourcesDeck = new Deck(Type.RESOURCES);
        Deck goldDeck = new Deck(Type.GOLD);
        Card starterCard = starterDeck.drawCard();
        Card resourcesCard = resourcesDeck.drawCard();
        Card goldCard = goldDeck.drawCard();
        Player player = new Player(starterCard);
        player.addCard(resourcesCard);
        player.addCard(resourcesCard);
        player.addCard(goldCard);
        assertEquals(3, player.getHand().size());
        String input = "1\n1";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        player.ChoseACard();
        assertEquals(2, player.getHand().size());
    }
}
