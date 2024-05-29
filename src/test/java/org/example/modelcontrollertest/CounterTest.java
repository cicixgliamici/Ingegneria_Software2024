package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.Type;
import org.example.enumeration.cast.CastCardRes;
import org.example.model.playarea.*;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;

import java.io.IOException;

public class CounterTest extends TestCase {
    public void testAddRes(){
        Counter counter = new Counter();
        counter.addResource(PropertiesCorner.ANIMAL);
        assertEquals(1,counter.getAnimalCounter());
        counter.addResource(PropertiesCorner.FUNGI);
        assertEquals(1,counter.getFungiCounter());
        counter.addResource(PropertiesCorner.INSECT);
        assertEquals(1,counter.getInsectCounter());
        counter.addResource(PropertiesCorner.PLANT);
        assertEquals(1,counter.getPlantCounter());
        counter.addResource(PropertiesCorner.INKWELL);
        assertEquals(1,counter.getInkwellCounter());
        counter.addResource(PropertiesCorner.MANUSCRIPT);
        assertEquals(1,counter.getManuscriptCounter());
        counter.addResource(PropertiesCorner.QUILL);
        assertEquals(1,counter.getQuillCounter());
    }

    public void testRemoveRes(){
        Counter counter = new Counter();
        counter.addResource(PropertiesCorner.ANIMAL);
        counter.removeResource(PropertiesCorner.ANIMAL);
        assertEquals(0,counter.getAnimalCounter());
        counter.addResource(PropertiesCorner.FUNGI);
        counter.removeResource(PropertiesCorner.FUNGI);
        assertEquals(0,counter.getFungiCounter());
        counter.addResource(PropertiesCorner.INSECT);
        counter.removeResource(PropertiesCorner.INSECT);
        assertEquals(0,counter.getInsectCounter());
        counter.addResource(PropertiesCorner.PLANT);
        counter.removeResource(PropertiesCorner.PLANT);
        assertEquals(0,counter.getPlantCounter());
        counter.addResource(PropertiesCorner.INKWELL);
        counter.removeResource(PropertiesCorner.INKWELL);
        assertEquals(0,counter.getInkwellCounter());
        counter.addResource(PropertiesCorner.MANUSCRIPT);
        counter.removeResource(PropertiesCorner.MANUSCRIPT);
        assertEquals(0,counter.getManuscriptCounter());
        counter.addResource(PropertiesCorner.QUILL);
        counter.removeResource(PropertiesCorner.QUILL);
        assertEquals(0,counter.getQuillCounter());
    }

    public void testAddPoint(){
        Counter counter = new Counter();
        counter.addPoint(2);
        assertEquals(2,counter.getPointCounter());
    }

    public void testIsPresent() throws IOException {
        Counter counter = new Counter();
        for(Type type: Type.values()){
            Deck deck = new Deck(type);
            Card card = deck.drawCard();
            CastCardRes cardRes1= new CastCardRes(card.getCardRes());
            PropertiesCorner propertiesCorner= cardRes1.getPropertiesCorner();
            counter.addResource(propertiesCorner);
            assertTrue(counter.isPresent(card.getCardRes()));
        }
    }

    public void testCounterGetSet(){
        Counter counter = new Counter();
        assertEquals(0, counter.getAnimalCounter());
        assertEquals(0, counter.getQuillCounter());
        assertEquals(0, counter.getObjectiveCounter());
        assertEquals(0,counter.getPlantCounter());
        assertEquals(0,counter.getInsectCounter());
        assertEquals(0,counter.getInkwellCounter());
        assertEquals(0,counter.getFungiCounter());
        assertEquals(0,counter.getPointCounter());
        assertEquals(0, counter.getManuscriptCounter());
        counter.addObjectiveCounter();
        assertEquals(1,counter.getObjectiveCounter());
    }
}
