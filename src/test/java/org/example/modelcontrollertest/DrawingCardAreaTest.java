package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.model.playarea.DrawingCardArea;
import org.json.simple.parser.ParseException;
import java.io.IOException;

import org.example.model.deck.*;
import org.example.enumeration.*;

public class DrawingCardAreaTest extends TestCase{
    public void testVReCard() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        assertNotNull(drawingCardArea);
        assertEquals(38 , drawingCardArea.getResourceDeck().getCardNumbers());
        assertEquals(2, drawingCardArea.getVisibleReCard().size());
        for (Card card : drawingCardArea.getVisibleReCard()) {
            assertEquals(Type.RESOURCES, card.getType());
        }
    }

    public void testVGoCard() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        assertNotNull(drawingCardArea);
        assertEquals(38, drawingCardArea.getGoldDeck().getCardNumbers());
        assertEquals(2, drawingCardArea.getVisibleGoCard().size());
        for (Card card : drawingCardArea.getVisibleGoCard()) {
            assertEquals(Type.GOLD, card.getType());
        }
    }

    public void testDrawFromDeck() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();

        int reDeckSize = drawingCardArea.getResourceDeck().getCardNumbers();
        drawingCardArea.drawCardFromDeck(Type.RESOURCES);
        assertEquals(reDeckSize - 1, drawingCardArea.getResourceDeck().getCardNumbers());

        int goDeckSize = drawingCardArea.getGoldDeck().getCardNumbers();
        drawingCardArea.drawCardFromDeck(Type.GOLD);
        assertEquals(goDeckSize - 1, drawingCardArea.getGoldDeck().getCardNumbers());

        int stDeckSize = drawingCardArea.getStarterDeck().getCardNumbers();
        drawingCardArea.drawCardFromDeck(Type.STARTER);
        assertEquals(stDeckSize - 1, drawingCardArea.getStarterDeck().getCardNumbers());

        int obDeckSize = drawingCardArea.getObjectDeck().getCardNumbers();
        drawingCardArea.drawCardFromDeck(Type.OBJECT);
        assertEquals(obDeckSize - 1, drawingCardArea.getObjectDeck().getCardNumbers());
    }

    public void testDrawFromVisible() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();

        int reDeckSize = drawingCardArea.getResourceDeck().getCardNumbers();
        Card drawnCard1 = drawingCardArea.drawCardFromVisible(Type.RESOURCES, 0);
        assertEquals(Type.RESOURCES,drawnCard1.getType());
        assertEquals(2, drawingCardArea.getVisibleReCard().size());
        assertEquals(reDeckSize - 1, drawingCardArea.getResourceDeck().getCardNumbers());

        int goDeckSize = drawingCardArea.getGoldDeck().getCardNumbers();
        Card drawnCard2 = drawingCardArea.drawCardFromVisible(Type.GOLD, 0);
        assertEquals(Type.GOLD,drawnCard2.getType());
        assertEquals(2, drawingCardArea.getVisibleGoCard().size());
        assertEquals(goDeckSize - 1, drawingCardArea.getGoldDeck().getCardNumbers());
    }

    public void testDCAGetSet() throws IOException, ParseException {
        DrawingCardArea drawingCardArea = new DrawingCardArea();

        Deck obDeck = drawingCardArea.getObjectDeck();
        assertNotNull(obDeck);
        assertEquals(Type.OBJECT, obDeck.getTypeDeck());
        assertNotNull(obDeck.getCards());
        assertFalse(obDeck.getCards().isEmpty());

        Deck reDeck = drawingCardArea.getResourceDeck();
        assertNotNull(reDeck);
        assertEquals(Type.RESOURCES, reDeck.getTypeDeck());
        assertNotNull(reDeck.getCards());
        assertFalse(reDeck.getCards().isEmpty());

        Deck goDeck = drawingCardArea.getGoldDeck();
        assertNotNull(goDeck);
        assertEquals(Type.GOLD, goDeck.getTypeDeck());
        assertNotNull(goDeck.getCards());
        assertFalse(goDeck.getCards().isEmpty());

        Deck stDeck = drawingCardArea.getStarterDeck();
        assertNotNull(stDeck);
        assertEquals(Type.STARTER, stDeck.getTypeDeck());
        assertNotNull(stDeck.getCards());
        assertFalse(stDeck.getCards().isEmpty());

        assertNotNull(drawingCardArea.getVisibleReCard());
        assertEquals(2, drawingCardArea.getVisibleReCard().size());
        for(Card card:drawingCardArea.getVisibleReCard()) {
            assertEquals(Type.RESOURCES, card.getType());
        }

        assertNotNull(drawingCardArea.getVisibleGoCard());
        assertEquals(2,drawingCardArea.getVisibleGoCard().size());
        for(Card card:drawingCardArea.getVisibleGoCard()){
            assertEquals(Type.GOLD, card.getType());
        }
    }

}
