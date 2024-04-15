package org.example;

import junit.framework.TestCase;
import org.example.enumeration.Type;
import org.example.model.PlayArea.Node;
import org.example.model.PlayArea.PlaceHolder;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.enumeration.*;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodeTest  extends TestCase {

    /** Constructors
     */
        public void testFCNode() throws IOException, ParseException {
            Deck starterDeck = new Deck(Type.STARTER);
            Card starterCard = starterDeck.drawCard();
            List<PlaceHolder> placeHolderList = new ArrayList<>();
            List<Node> availableNodes = new ArrayList<>();
            List<Node> allNodes = new ArrayList<>();
            Node node = new Node(starterCard, 0, 0, placeHolderList, availableNodes, allNodes);
            assertNotNull(node);
            assertEquals(0, node.getX());
            assertEquals(0, node.getY());
            assertEquals(starterCard, node.getCard());
            assertNotNull(node.getTopL());
            assertNotNull(node.getTopR());
            assertNotNull(node.getBotL());
            assertNotNull(node.getBotR());
            assertTrue(node.getTopL() instanceof PlaceHolder);
            assertTrue(node.getTopR() instanceof PlaceHolder);
            assertTrue(node.getBotL() instanceof PlaceHolder);
            assertTrue(node.getBotR() instanceof PlaceHolder);
        }


    public void testSCNode() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node node = new Node(starterCard, null, null, null, null, 0,0);
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
        assertNotNull(starterCard);
        assertNotNull(starterCard);
        assertNotNull(node);
        assertNull(node.getTopL());
        assertNull(node.getTopR());
        assertNull(node.getBotL());
        assertNull(node.getBotR());
    }

    /** Test Null Node
     */
    public void testNullNodeTopR() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
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
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
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
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
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
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        Node botL = starterNode.NullNodeBotL();
        assertNull(botL.getCard());
        assertNull(botL.getTopL());
        assertNotNull(botL.getTopR());
        assertNull(botL.getBotL());
        assertNull(botL.getBotR());
    }

    public void testSetNullNode() throws IOException, ParseException{
        List<Node> availableNodes = new ArrayList<>();
        Deck deck = new Deck(Type.STARTER);
        Card starterCard= deck.getCards().get(5);
        starterCard.setSide(1);
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        starterNode.SetNullNode(availableNodes);

    }

    /** Test particular setters
     */
    public void testSetPlaceHolderByCard() throws IOException, ParseException {
        // todo
    }

    public void testSetNodePlaceHolder() throws IOException, ParseException{
        // todo
    }

}
