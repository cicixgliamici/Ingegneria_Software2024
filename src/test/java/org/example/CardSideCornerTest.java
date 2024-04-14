package org.example;

import junit.framework.TestCase;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.PropertiesCorner;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CardSideCornerTest extends TestCase {
    /** Card Zone Tests
     */
    public void testCardType() throws IOException, ParseException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            Card card = deck.drawCard();
            assertEquals(type, card.getType());
        }
    }
    public void testPropCorn() throws IOException, ParseException{
        Deck deck = new Deck(Type.RESOURCES);
        Card card = deck.drawCard();
        assertEquals(PropertiesCorner.FUNGI, card.getFRONTPropCorn(0));
    }
}
