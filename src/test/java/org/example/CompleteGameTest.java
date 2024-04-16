package org.example;


import junit.framework.TestCase;
import org.example.controller.Controller;
import org.example.enumeration.*;
import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *  Test all the Methods of Deck, like creation and randomize
 */

public class CompleteGameTest extends TestCase {
    public void testMain() throws IOException, ParseException{
        // This is for: 1 start the match
        // insert "teo" as username, choose RED as color,
        // then insert another one with "lioko" as username and GREEN as color,
        // then start to play
        String simulatedUserInputs = "1\nteo\n1\n1\nlioko\n2\n2\n";
        System.setIn(new ByteArrayInputStream(simulatedUserInputs.getBytes()));
        Model model = new Model();
        assertNotNull(model);
        Controller controller = new Controller(model);
        assertNotNull(controller);
        assertEquals(2, controller.players.size());
        assertEquals(Color.RED, model.getScoreBoard().getTokenColor(controller.players.get(0)));
        assertEquals(Color.GREEN, model.getScoreBoard().getTokenColor(controller.players.get(1)));
    }
}
