package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.*;
import org.example.model.*;
import org.example.model.PlayArea.Node;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.*;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import static junit.framework.Assert.assertEquals;

public class NodeTest  extends TestCase {
    public void testFCNode() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, 0,0);
        assertNotNull(starterCard);
        assertNotNull(starterCard);
        assertNotNull(starterNode);
    }
    public void testSCNode() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        assertNotNull(starterCard);
        assertNotNull(starterCard);
        assertNotNull(starterNode);
    }
    public void testTCNode() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard);
        assertNotNull(starterCard);
        assertNotNull(starterCard);
        assertNotNull(starterNode);
    }
    public void testNullNodeTopR() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard);
        Node topR = starterNode.NullNodeTopR();
        assertNull(topR.getCard());
        assertNull(topR.getTopL());
        assertNull(topR.getTopR());
        assertNotNull(topR.getBotL());
        assertNull(topR.getBotR());
    }
    public void testNullNodeTopL() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard);
        Node topL = starterNode.NullNodeTopL();
        assertNull(topL.getCard());
        assertNull(topL.getTopL());
        assertNull(topL.getTopR());
        assertNull(topL.getBotL());
        assertNotNull(topL.getBotR());
    }

    public void testNullNodeBotR() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard);
        Node botR = starterNode.NullNodeBotR();
        assertNull(botR.getCard());
        assertNotNull(botR.getTopL());
        assertNull(botR.getTopR());
        assertNull(botR.getBotL());
        assertNull(botR.getBotR());
    }

    public void testNullNodeBotL() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard);
        Node botL = starterNode.NullNodeBotL();
        assertNull(botL.getCard());
        assertNull(botL.getTopL());
        assertNotNull(botL.getTopR());
        assertNull(botL.getBotL());
        assertNull(botL.getBotR());
    }

    public void testSetNullNode() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, 0, 0);
        starterNode.SetNullNode();
        assertNull(starterNode.getTopL().getCard());
        assertNull(starterNode.getTopR().getCard());
        assertNull(starterNode.getBotL().getCard());
        assertNull(starterNode.getBotR().getCard());
        assertEquals(1, starterNode.getTopR().getX());
        assertEquals(1, starterNode.getTopR().getY());
        assertEquals(-1, starterNode.getTopL().getX());
        assertEquals(1, starterNode.getTopL().getY());
        assertEquals(1, starterNode.getBotR().getX());
        assertEquals(-1, starterNode.getBotR().getY());
        assertEquals(-1, starterNode.getBotL().getX());
        assertEquals(-1, starterNode.getBotL().getY());
    }

    /*
    public void testAvailableNode() throws IOException, ParseException{
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, 0, 0);
        assertTrue(AvailableNode.isEmpty());
        AvailableNode.add(starterNode);
        assertEquals(1,AvailableNode.size());
        AvailableNode.remove(starterNode);
        assertTrue(AvailableNode.isEmpty());
    }
    public void testSearchAvbNode() throws IOException, ParseException{
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, 0, 0);
        assertTrue(AvailableNode.isEmpty());
        starterNode.searchAvailableNode();
        assertEquals(4,AvailableNode.size());
        AvailableNode.clear();
        assertTrue(AvailableNode.isEmpty());
    }
    public void testSetCard() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(null, 0, 0);
        assertNull(starterNode.getCard());
        starterNode.SetCardNode(starterCard);
        assertEquals(starterCard, starterNode.getCard());
    }

    public void testPaCNode() throws IOException, ParseException{
        //negro bastardo pisello in culo
    }

     */
}

