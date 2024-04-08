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

public class ModelTest extends TestCase {


    public void testCreateDeck() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
    }

    public void testCreateAllDecks() throws IOException, ParseException {
        ModelController modelController = new ModelController();
        modelController.CreateDeck();
        assertNotNull(modelController);
        assertNotNull(ModelController.getResourcesDeck());
        assertNotNull(ModelController.getGoldDeck());
        assertNotNull(ModelController.getStarterDeck());
        assertNotNull(ModelController.getObjectDeck());
    }
}
