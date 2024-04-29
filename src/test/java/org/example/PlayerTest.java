package org.example;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.enumeration.*;
import org.example.exception.InvalidCardException;
import org.example.exception.PlaceholderNotValid;
import org.example.model.deck.Card;
import org.example.model.deck.Corner;
import org.example.model.deck.SideCard;
import org.example.model.playarea.*;
import org.json.simple.parser.ParseException;
import org.example.model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;

public class PlayerTest extends TestCase {
    public void testToString() throws IOException, ParseException{
        Player player1 = new Player("cash_carti");
        String expectedToString = "cash_carti";
        assertEquals(expectedToString, player1.toString());
    }

    public void testCheckChosenCard() throws IOException, ParseException, InvalidCardException {
        Player player = new Player("vamp_carti");
        Model model = new Model();
        CardRes[] requireGold = {CardRes.ANIMAL};
        Card validCard = new Card(Type.GOLD, requireGold);
        List<Player> playersList = new ArrayList<>();
        playersList.add(player);
        model.setPlayersAndGameArea(playersList);
        PlayerCardArea playerCardArea = model.getPlayerCardArea(player);
        playerCardArea.getHand().add(validCard);
        Card returnedValidCard = player.CheckChosenCard(model, validCard);
        assertEquals(validCard, returnedValidCard);
    }

    public void testDraw() throws IOException, ParseException {
        Player p1 = new Player("p1");
        Model model = new Model();
        List<Player> playerList = new ArrayList<>();
        playerList.add(p1);
        model.setPlayersAndGameArea(playerList);
        DrawingCardArea drawingCardArea = new DrawingCardArea();
        model.setDrawingCardArea(drawingCardArea);
        Card testCard = new Card();
        int initialHandSize = model.getPlayerCardArea(p1).getHand().size();
        for(int i=0; i<=5; i++) {
            p1.Draw(model, i);
            assertEquals(initialHandSize + 1 + i, model.getPlayerCardArea(p1).getHand().size());
        }
    }

    public void testUpdateScoreboardPoints() throws IOException, ParseException {
        Player p1 = new Player("p1");
        List<Player> playerList = new ArrayList<>();
        playerList.add(p1);
        Model model = new Model();
        model.setPlayersAndGameArea(playerList);
        PlayerCardArea playerCardArea = model.getPlayerCardArea(p1);
        playerCardArea.getCounter().setPointCounter(5);
        ScoreBoard scoreBoard = model.getScoreBoard();
        scoreBoard.updatePlayerPoint(p1, 0);
        p1.updateScoreboardPoints(model);
        assertEquals(5, model.getScoreBoard().getPlayerPoint(p1));
    }
}


