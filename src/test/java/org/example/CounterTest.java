package org.example;

import junit.framework.TestCase;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.cast.CastCardRes;
import org.example.model.PlayArea.*;

public class CounterTest extends TestCase {
    public void addResourceTest(){
        Counter counter = new Counter();
        counter.AddResource(PropertiesCorner.ANIMAL);
        assertEquals(1,counter.getAnimalCounter());
        counter.AddResource(PropertiesCorner.FUNGI);
        assertEquals(1,counter.getFungiCounter());
        counter.AddResource(PropertiesCorner.INSECT);
        assertEquals(1,counter.getInsectCounter());
        counter.AddResource(PropertiesCorner.PLANT);
        assertEquals(1,counter.getPlantCounter());
        counter.AddResource(PropertiesCorner.INKWELL);
        assertEquals(1,counter.getInkwellCounter());
        counter.AddResource(PropertiesCorner.MANUSCRIPT);
        assertEquals(1,counter.getManuscriptCounter());
        counter.AddResource(PropertiesCorner.QUILL);
        assertEquals(1,counter.getQuillCounter());
    }

    public void removeResourceTest(){
        Counter counter = new Counter();
        counter.AddResource(PropertiesCorner.ANIMAL);
        counter.RemoveResource(PropertiesCorner.ANIMAL);
        assertEquals(0,counter.getAnimalCounter());
        counter.AddResource(PropertiesCorner.FUNGI);
        counter.RemoveResource(PropertiesCorner.FUNGI);
        assertEquals(0,counter.getFungiCounter());
        counter.AddResource(PropertiesCorner.INSECT);
        counter.RemoveResource(PropertiesCorner.INSECT);
        assertEquals(0,counter.getInsectCounter());
        counter.AddResource(PropertiesCorner.PLANT);
        counter.RemoveResource(PropertiesCorner.PLANT);
        assertEquals(0,counter.getPlantCounter());
        counter.AddResource(PropertiesCorner.INKWELL);
        counter.RemoveResource(PropertiesCorner.INKWELL);
        assertEquals(0,counter.getInkwellCounter());
        counter.AddResource(PropertiesCorner.MANUSCRIPT);
        counter.RemoveResource(PropertiesCorner.MANUSCRIPT);
        assertEquals(0,counter.getManuscriptCounter());
        counter.AddResource(PropertiesCorner.QUILL);
        counter.RemoveResource(PropertiesCorner.QUILL);
        assertEquals(0,counter.getQuillCounter());
    }

    public void addPointCounter(){
        Counter counter = new Counter();
        counter.AddPoint(2);
        assertEquals(2,counter.getPointCounter());
    }

    public void isPresentTest() throws throws IOException, ParseException {
        Counter counter = new Counter();
        Deck deck = new Deck(type.RESOURCES);
        Card card = deck.drawCard();
        assertTrue(counter.isPresent(card));
    }

}
