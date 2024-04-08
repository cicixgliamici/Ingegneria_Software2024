package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.*;

import org.example.model.ModelController;
import org.example.model.deck.*;
import org.example.model.deck.enumeration.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class ModelTest {


    public void testCreateDeck() {
        ModelController modelController = new ModelController();
        try {
            modelController.CreateDeck();
            // Verifica che i mazzi siano stati creati correttamente
            assertEquals(Type.RESOURCES, modelController.ResourceDeck.getTypeDeck());
            assertEquals(Type.GOLD, modelController.getGoldDeck().getTypeDeck());
            assertEquals(Type.OBJECT, modelController.getObjectDeck().getTypeDeck());
            assertEquals(Type.STARTER, modelController.getTypeDeck());
            // Verifica che i mazzi contengano il numero corretto di carte
            assertEquals(40, modelController.getResourcesDeck().getCardNumbers());
            assertEquals(40, modelController.getGoldDeck().getCardNumbers());
            assertEquals(16, modelController.getObjectDeck().getCardNumbers());
            assertEquals(6, modelController.getStarterDeck().getCardNumbers());

        } catch (IOException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }
     *
     */
}
