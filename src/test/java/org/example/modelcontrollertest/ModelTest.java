package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.enumeration.Type;
import org.example.exception.InvalidCardException;
import org.example.exception.PlaceholderNotValid;
import org.example.model.Model;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.playarea.Node;
import org.example.model.playarea.PlayerCardArea;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelTest extends TestCase {

    /** Test for the method DealCard which is in the Model, simulate
     * the beginning of a match
     */
    public void testDealCards() throws IOException,ParseException{
        Model model = new Model();
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Deck deckStarter = new Deck(Type.STARTER);
        Card starter1 = deckStarter.getCards().get(0);
        starter1.setSide(1);
        Card starter2 = deckStarter.getCards().get(1);
        starter2.setSide(1);
        Player player1 = new Player("blur");
        Player player2 = new Player("manuxo");
        model.getPlayersList().add(player1);
        model.getPlayersList().add(player2);
        model.setPlayersAndGameArea(model.getPlayersList());
        model.getPlayerCardArea(player1).setCardStarter(starter1);
        model.getPlayerCardArea(player1).setStarterNode();
        model.getPlayerCardArea(player2).setCardStarter(starter2);
        model.getPlayerCardArea(player2).setStarterNode();
        model.DealCards();
        assertEquals(2, playerslist.size());
        for(Player player: playerslist) {
            assertEquals(1, model.getPlayerCardArea(player).getAllNodes().size());
            assertTrue(model.getPlayerCardArea(player).getAllNodes().get(0) instanceof Node);
            assertEquals(Type.STARTER, model.getPlayerCardArea(player).getAllNodes().get(0).getCard().getType());
            assertEquals(3, model.getPlayerCardArea(player).getHand().size());
            assertEquals(Type.RESOURCES, model.getPlayerCardArea(player).getHand().get(0).getType());
            assertEquals(Type.RESOURCES, model.getPlayerCardArea(player).getHand().get(1).getType());
            assertEquals(Type.GOLD, model.getPlayerCardArea(player).getHand().get(2).getType());
        }
        assertEquals(4, model.getDrawingCardArea().getStarterDeck().getCardNumbers());
        // 40 - 2*n - 2 on ground
        assertEquals(34, model.getDrawingCardArea().getResourceDeck().getCardNumbers());
        // 40 - 2*n - 2 on ground
        assertEquals(36, model.getDrawingCardArea().getGoldDeck().getCardNumbers());
        assertEquals(2, model.getDrawingCardArea().getVisibleGoCard().size());
        assertEquals(2, model.getDrawingCardArea().getVisibleReCard().size());
    }

    public void testPlayAndDraw() throws IOException, ParseException, PlaceholderNotValid, InvalidCardException {
        Model model = new Model();
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("dario_moccia");
        model.getPlayersList().add(player1);
        model.setPlayersAndGameArea(model.getPlayersList());
        model.DealCards();
        Deck starterDeck = new Deck(Type.STARTER);
        Card starter = starterDeck.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=model.getPlayerCardArea(player1);
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        player1.play(model, 1, 1, 1,1);
        assertEquals(2, model.getPlayerCardArea(player1).getHand().size());
        assertEquals(2, model.getPlayerCardArea(player1).getAllNodes().size());
        player1.draw(model, 0);
        assertEquals(3, model.getPlayerCardArea(player1).getHand().size());
        // 40 - 2*n - 2 - 1
        assertEquals(35, model.getDrawingCardArea().getResourceDeck().getCardNumbers());
        player1.play(model,0, 1, -1,1);
        assertEquals(3, model.getPlayerCardArea(player1).getAllNodes().size());
        assertEquals(2, model.getPlayerCardArea(player1).getHand().size());
    }


    /** Add 3 resource cards by back to have the needed requirement for placing the
     * first gold card, and place it.
     * Then try to place a non-valid one, like the second one.
     */
    public void testGoldReq() throws IOException, ParseException, PlaceholderNotValid, InvalidCardException {
        Model model = new Model();
        Deck deckRes = new Deck(Type.RESOURCES);
        Deck deckStarter = new Deck(Type.STARTER);
        Deck deckGold = new Deck(Type.GOLD);
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("ola");
        model.getPlayersList().add(player1);
        Card starter = deckStarter.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=new PlayerCardArea();
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        model.getGameArea().put(player1, playerCardArea);
        Card card1 = deckRes.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(card1);
        Card card2 = deckRes.getCards().get(1);
        model.getPlayerCardArea(player1).getHand().add(card2);
        Card card3 = deckRes.getCards().get(21);
        model.getPlayerCardArea(player1).getHand().add(card3);
        player1.play(model, 0, 2, 1,1);
        player1.play(model, 0, 2, -1,1);
        player1.play(model, 0, 2, -1,-1);
        Card card4 = deckGold.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(card4);
        player1.play(model, 0, 1, 1, -1);
        assertEquals(card4, model.getPlayerCardArea(player1).getNodeByXY(1,-1).getCard());
        assertEquals(1, model.getPlayerCardArea(player1).getCounter().getPointCounter());
        try {
            Card card5 = deckGold.getCards().get(1);
            model.getPlayerCardArea(player1).getHand().add(card5);
            player1.play(model, 0, 1, 2, 2); // This is expected  InvalidCardException
            fail("Expected an InvalidCardException to be thrown");
        } catch (InvalidCardException e) {
            assertEquals("La carta selezionata non è valida.", e.getMessage());
        }
    }
}


