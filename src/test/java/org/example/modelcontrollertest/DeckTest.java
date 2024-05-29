package org.example.modelcontrollertest;


import junit.framework.TestCase;

import java.io.IOException;
import org.example.model.deck.*;
import org.example.enumeration.*;


/**
 *  Test all the Methods of Deck, like creation and randomize
 */

public class DeckTest extends TestCase {

    //* Test the creation and the correct number of cards in the decks
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
    //* Test the method Draw from the class Deck
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

    /** This test can file 1 time out of 40 :)
    *   Had to use "FakeDrawCard" instead of "drawCard" because
    *   drawCard removes the card from the deck
     */
    public void testShuffleResources() throws IOException {
        for (Type type : Type.values()) {
            Deck deck = new Deck(type);
            assertEquals(type, deck.getTypeDeck());
            Card FirstShuffleCard = deck.fakeDrawCard();
            assertNotNull(FirstShuffleCard);
            deck.shuffle();
            Card SecondShuffleCard = deck.fakeDrawCard();
            assertNotNull(SecondShuffleCard);
            assertNotSame(FirstShuffleCard, SecondShuffleCard);
        }
    }

    //* Pumped version that see every card 
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
    // test addCard method in Deck
    public void testAddCard() throws IOException {
        for (Type type: Type.values()){
            Deck deck = new Deck(type);
            int cardsNumber = deck.getCardNumbers();
            Card card = new Card();
            deck.addCard(card);
            assertEquals(cardsNumber + 1, deck.getCardNumbers());
        }
    }

    public void testDeckGetSet() throws IOException {
        Deck ReDeck = new Deck(Type.RESOURCES);
        assertEquals(Type.RESOURCES, ReDeck.getTypeDeck());
        assertEquals(40, ReDeck.getCardNumbers());
        Deck GoDeck = new Deck(Type.GOLD);
        assertEquals(Type.GOLD, GoDeck.getTypeDeck());
        assertEquals(40, GoDeck.getCardNumbers());
        Deck StDeck = new Deck(Type.STARTER);
        assertEquals(Type.STARTER, StDeck.getTypeDeck());
        assertEquals(6, StDeck.getCardNumbers());
        Deck ObDeck = new Deck(Type.OBJECT);
        assertEquals(Type.OBJECT, ObDeck.getTypeDeck());
        assertEquals(16, ObDeck.getCardNumbers());
    }
}
