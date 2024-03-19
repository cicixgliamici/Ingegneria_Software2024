package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    public void testDeckResource() {
        Deck deck = new Deck(Type.RESOURCE);
        assertEquals(Type.RESOURCE, deck.getType());
        assertEquals(40, deck.getCardNumbers());
    }

    @Test
    public void testDeckResource() {
        Deck deck = new Deck(Type.GOLD);
        assertEquals(Type.GOLD, deck.getType());
        assertEquals(40, deck.getCardNumbers());
    }

    @Test
    public void testDeckResource() {
        Deck deck = new Deck(Type.OBJECT);
        assertEquals(Type.OBJECT, deck.getType());
        assertEquals(16, deck.getCardNumbers());
    }

    @Test
    public void testDeckResource() {
        Deck deck = new Deck(Type.STARTER);
        assertEquals(Type.STARTER, deck.getType());
        assertEquals(6, deck.getCardNumbers());
    }
}
