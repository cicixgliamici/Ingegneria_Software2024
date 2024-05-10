package org.example;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.enumeration.*;
import org.example.exception.InvalidCardException;
import org.example.exception.PlaceholderNotValid;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.playarea.*;
import org.json.simple.parser.ParseException;
import org.example.model.Model;

import java.util.ArrayList;
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
        playerCardArea.getCounter().addResource(PropertiesCorner.ANIMAL);
        Card returnedValidCard = player.checkChosenCard(model, validCard);
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
            p1.draw(model, i);
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

    public void testPlay() throws IOException, ParseException, PlaceholderNotValid, InvalidCardException {
        Model model = new Model();
        Deck deckRes = new Deck(Type.RESOURCES);
        Deck deckStarter = new Deck(Type.STARTER);
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("al-Khwārizmī");
        model.getPlayersList().add(player1);
        Card starter = deckStarter.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=new PlayerCardArea();
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        model.getGameArea().put(player1, playerCardArea);
        assertEquals(0, model.getPlayerCardArea(player1).getCounter().getObjectiveCounter());
        Card card1 = deckRes.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(card1);
        int handSize = playerCardArea.getHand().size();
        player1.play(model, 0, 2, 1,1);
        List<PlaceHolder> placeHolderList = playerCardArea.getPlaceHolders();
        List<PlaceHolder> availableNodes = playerCardArea.getAvailableNodes();
        List<PlaceHolder> allNodes = playerCardArea.getAllNodes();
        PlaceHolder node1 = new Node(card1, 1, 1, placeHolderList, availableNodes, allNodes);
        assertEquals(node1.getCard(), card1);
        assertEquals(handSize-1, playerCardArea.getHand().size());
        assertEquals(node1.getCard().getSide().getSide(), Side.BACK);
    }
}


