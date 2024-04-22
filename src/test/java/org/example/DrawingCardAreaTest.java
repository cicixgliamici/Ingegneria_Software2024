package org.example;

import junit.framework.TestCase;
import org.example.model.PlayArea.DrawingCardArea;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import org.example.model.deck.*;
import org.example.enumeration.*;

public class DrawingCardAreaTest extends TestCase{
    public void VReCardTest() throws IOException, ParseException{
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        assertNotNull(drawingCardArea);
        assertEquals(2, drawingCardArea.getVisibleReCard().size());
        for(Card card : drawingCardArea.getVisibleReCard()){
            assertEquals(Type.RESOURCES, card.getType());
        }
        drawingCardArea.initializeVReCard();
    }

}
