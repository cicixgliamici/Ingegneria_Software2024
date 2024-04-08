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

public class CardTest extends TestCase {
    public void testCardResource() {
        Deck deck = null;
        try {
            deck = new Deck(Type.RESOURCES);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        Card card = deck.drawCard();

    }
}
