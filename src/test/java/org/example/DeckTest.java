package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.*;

import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 *  Test all the Methods of Deck, like creation and randomize
 */

public class DeckTest extends TestCase {

//* Test the creation and the correct number of cards in the decks
    public void testDeckResource() {
        Deck deck = null;
        try {
            deck = new Deck(Type.RESOURCES);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Type.RESOURCES, deck.getTypeDeck());
        assertEquals(40, deck.getCardNumbers());
    }


    public void testDeckGold() {
        Deck deck = null;
        try {
            deck = new Deck(Type.GOLD);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Type.GOLD, deck.getTypeDeck());
        assertEquals(40, deck.getCardNumbers());
    }


    public void testDeckObject() {
        Deck deck = null;
        try {
            deck = new Deck(Type.OBJECT);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Type.OBJECT, deck.getTypeDeck());
        assertEquals(16, deck.getCardNumbers());
    }


    public void testDeckStarter() {
        Deck deck = null;
        try {
            deck = new Deck(Type.STARTER);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        assertEquals(Type.STARTER, deck.getTypeDeck());
        assertEquals(6, deck.getCardNumbers());
    }
//* Test the draw method
    public void testDrawCardRes() {
        Deck deck = null;
        try {
            deck = new Deck(Type.RESOURCES);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(39, deck.getCardNumbers());
    }

    public void testDrawCardGold() {
        Deck deck = null;
        try {
            deck = new Deck(Type.GOLD);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(39, deck.getCardNumbers());
    }

    public void testDrawCardObj() {
        Deck deck = null;
        try {
            deck = new Deck(Type.OBJECT);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(15, deck.getCardNumbers());
    }

    public void testDrawCardStarter() {
        Deck deck = null;
        try {
            deck = new Deck(Type.STARTER);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card drawnCard = deck.drawCard();
        assertNotNull(drawnCard);
        assertEquals(5, deck.getCardNumbers());
    }

    public void testShuffleResources(){
        Deck deck = null;
        try {
            deck = new Deck(Type.RESOURCES);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card FirstShuffleCard = deck.FakeDrawCard();
        assertNotNull(FirstShuffleCard);
        deck.shuffle();
        Card SecondShuffleCard = deck.FakeDrawCard();
        assertNotNull(SecondShuffleCard);
        assertNotSame(FirstShuffleCard, SecondShuffleCard);
    }
}
