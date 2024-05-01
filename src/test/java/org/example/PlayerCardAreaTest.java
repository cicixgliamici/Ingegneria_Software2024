package org.example;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.model.playarea.*;
import org.example.enumeration.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerCardAreaTest extends TestCase {
    public void testUpdateCounter() {
        PlayerCardArea playerCardArea = new PlayerCardArea();
        Counter counter = playerCardArea.getCounter();
        Corner c1 = new Corner(Position.BOTTOML, PropertiesCorner.QUILL);
        Corner c2 = new Corner(Position.TOPL, PropertiesCorner.PLANT);
        Corner c3 = new Corner(Position.BOTTOMR, PropertiesCorner.MANUSCRIPT);
        Corner c4 = new Corner(Position.TOPR, PropertiesCorner.FUNGI);
        List<Corner> front = new ArrayList<>();
        List<Corner> back = new ArrayList<>(Arrays.asList(c1, c2, c3, c4));
        SideCard side = new SideCard(Side.BACK, front, back);
        Card c = new Card(1, Type.RESOURCES, CardRes.ANIMAL, null, null, null, null, null, side);
        playerCardArea.UpdateCounter(c);
        assertEquals(1,counter.getQuillCounter());
        assertEquals(1,counter.getPlantCounter());
        assertEquals(1, counter.getAnimalCounter());
        assertEquals(1,counter.getFungiCounter());
        assertEquals(1, counter.getManuscriptCounter());

        CardRes[] requireGold = {CardRes.INSECT, CardRes.ANIMAL};
        Corner c5 = new Corner(Position.BOTTOML, PropertiesCorner.HIDDEN);
        Corner c6 = new Corner(Position.TOPL, PropertiesCorner.HIDDEN);
        Corner c7 = new Corner(Position.BOTTOMR, PropertiesCorner.HIDDEN);
        Corner c8 = new Corner(Position.TOPR, PropertiesCorner.HIDDEN);
        List<Corner> front2 = new ArrayList<>(Arrays.asList(c5, c6, c7, c8));
        List<Corner> back2 = new ArrayList<>();
        SideCard side2 = new SideCard(Side.FRONT, front2, back2);
        Card sc = new Card(2, Type.STARTER, null, requireGold, null, null, null, null, side2);
        playerCardArea.UpdateCounter(sc);
        assertEquals(1, counter.getInsectCounter());
        assertEquals(2, counter.getAnimalCounter());
    }

    public void testUpdatePoints(){
        PlayerCardArea playerCardArea = new PlayerCardArea();
        Counter counter = playerCardArea.getCounter();
        Card c1 = new Card(1, Type.RESOURCES, null, null, 1, null, null, null, null);
        Card c2 = new Card(2, Type.GOLD, null, null, 1, GoldenPoint.NULL, null, null, null);
        Card c3 = new Card(3, Type.GOLD, null, null, 1, GoldenPoint.CORNER, null, null, null);
        Card c4 = new Card(4, Type.GOLD, null, null, 1, GoldenPoint.MANUSCRIPT, null, null, null);
        Card c5 = new Card(5, Type.GOLD, null, null, 1, GoldenPoint.INKWELL, null, null, null);
        Card c6 = new Card(6, Type.GOLD, null, null, 1, GoldenPoint.QUILL, null, null, null);
        counter.AddResource(PropertiesCorner.QUILL);
        counter.AddResource(PropertiesCorner.INKWELL);
        counter.AddResource(PropertiesCorner.MANUSCRIPT);
        playerCardArea.UpdatePoints(c1);
        assertEquals(1, counter.getPointCounter());
        playerCardArea.UpdatePoints(c2);
        assertEquals(2, counter.getPointCounter());
        c3.setCoveredCornerByCard(2);
        playerCardArea.UpdatePoints(c3);
        assertEquals(4, counter.getPointCounter());
        playerCardArea.UpdatePoints(c4);
        assertEquals(5, counter.getPointCounter());
        playerCardArea.UpdatePoints(c5);
        assertEquals(6, counter.getPointCounter());
        playerCardArea.UpdatePoints(c6);
        assertEquals(7, counter.getPointCounter());
    }

    public void testCheckGold(){
        PlayerCardArea playerCardArea = new PlayerCardArea();
        Counter counter = playerCardArea.getCounter();
        CardRes[] requireGold = {CardRes.INSECT, CardRes.ANIMAL};
        Card c = new Card(1, Type.GOLD, null, requireGold, null, null, null, null, null);
        counter.AddResource(PropertiesCorner.ANIMAL);
        assertTrue(playerCardArea.CheckPlayForGold(c));
        counter.AddResource(PropertiesCorner.INSECT);
        assertFalse(playerCardArea.CheckPlayForGold(c));
    }

    public void testPublicObjective() throws IOException, ParseException {
        Model model = new Model();
        Player player = new Player("p");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);
        model.setPlayersAndGameArea(playerList);
        PlayerCardArea playerCardArea = model.getPlayerCardArea(player);
        Counter counter = playerCardArea.getCounter();

        Card c1 = new Card(1, Type.OBJECT, CardRes.FUNGI, null, null, null, ObjectivePoints.DIAG, null, null);
        Card c2 = new Card(2, Type.OBJECT, CardRes.PLANT, null, null, null, ObjectivePoints.DIAG, null, null);
        Card c3 = new Card(3, Type.OBJECT, CardRes.INSECT, null, null, null, ObjectivePoints.DIAG, null, null);
        Card c4 = new Card(4, Type.OBJECT, CardRes.ANIMAL, null, null, null, ObjectivePoints.DIAG, null, null);
        Card c5 = new Card(5, Type.OBJECT, CardRes.FUNGI, null, null, null, ObjectivePoints.RES, null, null);
        Card c6 = new Card(6, Type.OBJECT, CardRes.PLANT, null, null, null, ObjectivePoints.RES, null, null);
        Card c7 = new Card(7, Type.OBJECT, CardRes.INSECT, null, null, null, ObjectivePoints.RES, null, null);
        Card c8 = new Card(8, Type.OBJECT, CardRes.ANIMAL, null, null, null, ObjectivePoints.RES, null, null);
        Card c9 = new Card(9, Type.OBJECT, CardRes.QUILL, null, null, null, ObjectivePoints.RES, null, null);
        Card c10 = new Card(10, Type.OBJECT, CardRes.INKWELL, null, null, null, ObjectivePoints.RES, null, null);
        Card c11 = new Card(11, Type.OBJECT, CardRes.MANUSCRIPT, null, null, null, ObjectivePoints.RES, null, null);
        Card c12 = new Card(12, Type.OBJECT, null, null, null, null, ObjectivePoints.REDGREEN, null, null);
        Card c13 = new Card(13, Type.OBJECT, null, null, null, null, ObjectivePoints.GREENPURPLE, null, null);
        Card c14 = new Card(14, Type.OBJECT, null, null, null, null, ObjectivePoints.BLUERED, null, null);
        Card c15 = new Card(15, Type.OBJECT, null, null, null, null, ObjectivePoints.PURPLEBLUE, null, null);
        Card c16 = new Card(16, Type.OBJECT, null, null, null, null, ObjectivePoints.MIX, null, null);

        List<Card> publicObjective = new ArrayList<>();
        publicObjective.add(c1);
        model.setPublicObjective(publicObjective);
        // da finire

    }
}
