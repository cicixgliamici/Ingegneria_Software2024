package org.example;

import junit.framework.TestCase;
import org.example.model.PlayArea.Node;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.enumeration.*;
import org.json.simple.parser.ParseException;
import java.io.IOException;

public class NodeTest  extends TestCase {

    /** Constructors
     */
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

    /** Test Null Node
     */
    public void testNullNodeTopR() throws IOException, ParseException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, 0, 0);
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
        Node starterNode = new Node(starterCard,0,0);
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
        Node starterNode = new Node(starterCard,0,0);
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
        Node starterNode = new Node(starterCard,0,0);
        Node botL = starterNode.NullNodeBotL();
        assertNull(botL.getCard());
        assertNull(botL.getTopL());
        assertNotNull(botL.getTopR());
        assertNull(botL.getBotL());
        assertNull(botL.getBotR());
    }

    public void testSetNullNode() throws IOException, ParseException{
        Deck deck = new Deck(Type.STARTER);
        Card card= deck.getCards().get(5);
        card.setSide(1);
        Node node = new Node(card,0,0);
        node.SetNullNode();

    }

}
