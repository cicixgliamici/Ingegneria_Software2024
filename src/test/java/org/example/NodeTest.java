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
import static org.example.model.PlayArea.Node.AvailableNode;

public class NodeTest  extends TestCase {
    public void testFirstCard4Empty() throws IOException, ParseException {
        // Create a Starter Deck and draw the first one
        Deck StarterDeck = new Deck(Type.STARTER);
        Card starterCard = StarterDeck.drawCard();
        Node initialCard = new Node(starterCard, null, null, null, null, 0, 0);

        // Execute the method
        initialCard.searchAvailableNode();

        // Verify that there are 4 avaiable node
        assertEquals(4, AvailableNode.size());
    }
}
