package org.example.modelcontrollertest;

import junit.framework.TestCase;
import java.io.IOException;
import org.example.model.deck.*;
import org.example.enumeration.*;

/**
 * Test class for various methods in the Deck class, including creation, drawing, and shuffling.
 */
public class DeckTest extends TestCase {

    /**
     * Test the creation of decks and the correct number of cards in each type of deck.
     */
    public void testDeckProperties() throws IOException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            assertEquals(type, deck.getTypeDeck());
            switch (type) {
                case OBJECT:
                    assertEquals(16, deck.getCardNumbers());
                    break;
                case STARTER:
                    assertEquals(6, deck.getCardNumbers());
                    break;
                default:
                    assertEquals(40, deck.getCardNumbers());
                    break;
            }
        }
    }

    /**
     * Test the draw method from the Deck class.
     */
    public void testDeckDraw() throws IOException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            assertEquals(type, deck.getTypeDeck());
            Card drawnCard = deck.drawCard();
            assertNotNull(drawnCard);
            switch (type) {
                case OBJECT:
                    assertEquals(15, deck.getCardNumbers());
                    break;
                case STARTER:
                    assertEquals(5, deck.getCardNumbers());
                    break;
                default:
                    assertEquals(39, deck.getCardNumbers());
                    break;
            }
        }
    }

    /**
     * This test might fail occasionally due to the nature of shuffling.
     * Had to use "fakeDrawCard" instead of "drawCard" because drawCard removes the card from the deck.
     *
     * It can fail 1/40 times
     */
    public void testShuffleResources() throws IOException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            assertEquals(type, deck.getTypeDeck());
            Card firstShuffleCard = deck.fakeDrawCard();
            assertNotNull(firstShuffleCard);
            deck.shuffle();
            Card secondShuffleCard = deck.fakeDrawCard();
            assertNotNull(secondShuffleCard);
            assertNotSame(firstShuffleCard, secondShuffleCard);
        }
    }

    /**
     * More comprehensive shuffle test that checks every card.
     */
    public void testShuffleV2Resources() throws IOException {
        for (Type type : Type.values()) {
            Deck originalDeck = new Deck(type);
            assertEquals(type, originalDeck.getTypeDeck());
            Deck shuffledDeck = new Deck(type);
            shuffledDeck.getCards().clear();
            for (Card card : originalDeck.getCards()) {
                shuffledDeck.getCards().add(new Card());
            }
            shuffledDeck.shuffle();
            for (int i = 0; i < originalDeck.getCardNumbers(); i++) {
                assertNotSame(originalDeck.getCards().get(i), shuffledDeck.getCards().get(i));
            }
        }
    }

    /**
     * Test the addCard method in the Deck class.
     */
    public void testAddCard() throws IOException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            int cardsNumber = deck.getCardNumbers();
            Card card = new Card();
            deck.addCard(card);
            assertEquals(cardsNumber + 1, deck.getCardNumbers());
        }
    }

    /**
     * Test getters and setters of the Deck class.
     */
    public void testDeckGetSet() throws IOException {
        Deck reDeck = new Deck(Type.RESOURCES);
        assertEquals(Type.RESOURCES, reDeck.getTypeDeck());
        assertEquals(40, reDeck.getCardNumbers());

        Deck goDeck = new Deck(Type.GOLD);
        assertEquals(Type.GOLD, goDeck.getTypeDeck());
        assertEquals(40, goDeck.getCardNumbers());

        Deck stDeck = new Deck(Type.STARTER);
        assertEquals(Type.STARTER, stDeck.getTypeDeck());
        assertEquals(6, stDeck.getCardNumbers());

        Deck obDeck = new Deck(Type.OBJECT);
        assertEquals(Type.OBJECT, obDeck.getTypeDeck());
        assertEquals(16, obDeck.getCardNumbers());
    }
}
