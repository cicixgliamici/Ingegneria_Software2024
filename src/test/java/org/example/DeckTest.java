package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.example.model.deck.*;

public class DeckTest {


    public void testDeckResource() {
        Deck deck = new Deck(Type.RESOURCES);
        assertEquals(Type.RESOURCES, deck.getType());
        assertEquals(40, deck.getCardNumbers());
    }


    public void testDeckResource() {
        Deck deck = new Deck(Type.GOLD);
        assertEquals(Type.GOLD, deck.getType());
        assertEquals(40, deck.getCardNumbers());
    }


    public void testDeckResource() {
        Deck deck = new Deck(Type.OBJECT);
        assertEquals(Type.OBJECT, deck.getType());
        assertEquals(16, deck.getCardNumbers());
    }


    public void testDeckResource() {
        Deck deck = new Deck(Type.STARTER);
        assertEquals(Type.STARTER, deck.getType());
        assertEquals(6, deck.getCardNumbers());
    }
}
