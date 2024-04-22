package org.example;

import junit.framework.TestCase;
import org.example.model.PlayArea.DrawingCardArea;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Stack;

import org.example.model.deck.*;
import org.example.enumeration.*;
import org.example.model.deck.*;

public class DrawingCardAreaTest extends TestCase{
    public void VReCardTest() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        assertNotNull(drawingCardArea);
        int reDeckSize = drawingCardArea.getResourceDeck().getCardNumbers();
        drawingCardArea.initializeVReCard();
        assertEquals(2, drawingCardArea.getVisibleReCard().size());
        assertEquals(reDeckSize - 2, drawingCardArea.getResourceDeck().getCardNumbers());
        for (Card card : drawingCardArea.getVisibleReCard()) {
            assertEquals(Type.RESOURCES, card.getType());
        }
    }

    public void VGoCardTest() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        assertNotNull(drawingCardArea);
        int goDeckSize = drawingCardArea.getGoldDeck().getCardNumbers();
        drawingCardArea.initializeVGoCard();
        assertEquals(2, drawingCardArea.getVisibleGoCard().size());
        assertEquals(goDeckSize - 2, drawingCardArea.getGoldDeck().getCardNumbers());
        for (Card card : drawingCardArea.getVisibleGoCard()) {
            assertEquals(Type.GOLD, card.getType());
        }
    }

    public void drawFromDeckTest() throws IOException, ParseException{
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

    public void drawFromVisibleTest() throws IOException, ParseException{
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

}
