package org.example;

import junit.framework.TestCase;

import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.model.Model;
import org.example.controller.Player;
import org.example.model.playarea.Node;
import org.example.model.playarea.PlaceHolder;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

public class ModelTest extends TestCase {

    /** Test for the method DealCard which is in the Model, simulate
     * the beginning of a match
     */
    public void testDealCards() throws IOException,ParseException{
        Model model = new Model();
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("blur");
        Player player2 = new Player("manuxo");
        model.getPlayersList().add(player1);
        model.getPlayersList().add(player2);
        model.setPlayersAndGameArea(model.getPlayersList());
        model.DealCards();
        assertEquals(2, playerslist.size());
        for(Player player: playerslist) {
            assertEquals(1, model.getPlayerArea(player).getAllNodes().size());
            assertTrue(model.getPlayerArea(player).getAllNodes().get(0) instanceof Node);
            assertEquals(Type.STARTER, model.getPlayerArea(player).getAllNodes().get(0).getCard().getType());
            assertEquals(3, model.getPlayerArea(player).getHand().size());
            assertEquals(Type.RESOURCES, model.getPlayerArea(player).getHand().get(0).getType());
            assertEquals(Type.RESOURCES, model.getPlayerArea(player).getHand().get(1).getType());
            assertEquals(Type.GOLD, model.getPlayerArea(player).getHand().get(2).getType());
        }
        assertEquals(4, model.getDrawingCardArea().getStarterDeck().getCardNumbers());
        // 40 - 2*n - 2 on ground
        assertEquals(34, model.getDrawingCardArea().getResourceDeck().getCardNumbers());
        // 40 - 2*n - 2 on ground
        assertEquals(36, model.getDrawingCardArea().getGoldDeck().getCardNumbers());
        assertEquals(2, model.getDrawingCardArea().getVisibleGoCard().size());
        assertEquals(2, model.getDrawingCardArea().getVisibleReCard().size());
    }

    public void testPlayAndDraw() throws IOException, ParseException, PlaceholderNotValid {
        Model model = new Model();
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("dario_moccia");
        model.getPlayersList().add(player1);
        model.setPlayersAndGameArea(model.getPlayersList());
        model.DealCards();
        player1.Play(model, 1, 1,1);
        assertEquals(2, model.getPlayerArea(player1).getHand().size());
        assertEquals(2, model.getPlayerArea(player1).getAllNodes().size());
        player1.Draw(model, 0);
        assertEquals(3, model.getPlayerArea(player1).getHand().size());
        // 40 - 2*n - 2 - 1
        assertEquals(35, model.getDrawingCardArea().getResourceDeck().getCardNumbers());
        player1.Play(model, 1, -1,1);
        assertEquals(3, model.getPlayerArea(player1).getAllNodes().size());
        assertEquals(2, model.getPlayerArea(player1).getHand().size());
    }

}


