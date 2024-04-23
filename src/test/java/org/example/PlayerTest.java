package org.example;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PlayerTest extends TestCase {
    public void testToString() throws IOException, ParseException{
        Player player1 = new Player("cash_carti");
        String expectedToString = "cash_carti";
        assertEquals(expectedToString, player1.toString());
    }

}
