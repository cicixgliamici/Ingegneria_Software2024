package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.enumeration.Side;
import org.example.enumeration.Type;
import org.example.exception.PlaceholderNotValid;
import org.example.exception.InvalidCardException;
import org.example.model.Model;
import org.example.model.playarea.Counter;
import org.example.model.playarea.PlayerCardArea;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.deck.SideCard;
import org.example.model.playarea.PlaceHolder;
import org.example.model.playarea.DrawingCardArea;
import org.example.model.playarea.ScoreBoard;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test case for the Player class.
 */
public class PlayerTest extends TestCase {

    private Model model;
    private Player player;
    private PlayerCardArea playerCardArea;
    private DrawingCardArea drawingCardArea;
    private Counter counter;
    private Deck deck;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if an error occurs during setup.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        model = Mockito.mock(Model.class);
        player = new Player("testPlayer");
        playerCardArea = Mockito.mock(PlayerCardArea.class);
        drawingCardArea = Mockito.mock(DrawingCardArea.class);
        counter = Mockito.mock(Counter.class);
        deck = Mockito.mock(Deck.class);

        Mockito.when(model.getPlayerCardArea(player)).thenReturn(playerCardArea);
        Mockito.when(model.getDrawingCardArea()).thenReturn(drawingCardArea);
        Mockito.when(playerCardArea.getCounter()).thenReturn(counter);
    }

    /**
     * Tests the checkChosenCard method to verify that an InvalidCardException
     * is thrown for an invalid card.
     */
    public void testCheckChosenCard() {
        Card card = Mockito.mock(Card.class);
        SideCard sideCard = Mockito.mock(SideCard.class);
        PlaceHolder placeHolder = Mockito.mock(PlaceHolder.class);

        Mockito.when(card.getType()).thenReturn(Type.GOLD);
        Mockito.when(card.getSide()).thenReturn(sideCard);
        Mockito.when(sideCard.getSide()).thenReturn(Side.FRONT);
        Mockito.when(playerCardArea.checkPlayForGold(card)).thenReturn(true);

        try {
            player.checkChosenCard(model, card, placeHolder);
            fail("Expected InvalidCardException was not thrown");
        } catch (InvalidCardException e) {
            assertEquals("The selected card is not valid.", e.getMessage());
        }
    }

    /**
     * Tests the setObjStarter method to verify that the starter card and
     * secret objective are set correctly.
     */
    public void testSetObjStarter() {
        Card starterCard = Mockito.mock(Card.class);
        List<Card> tempSecretObjectives = new ArrayList<>();
        Card secretObjective1 = Mockito.mock(Card.class);
        Card secretObjective2 = Mockito.mock(Card.class);
        tempSecretObjectives.add(secretObjective1);
        tempSecretObjectives.add(secretObjective2);

        Mockito.when(playerCardArea.getCardStarter()).thenReturn(starterCard);
        Mockito.when(playerCardArea.getTempSecretObjective()).thenReturn(tempSecretObjectives);
        Mockito.when(drawingCardArea.getObjectDeck()).thenReturn(deck);

        player.setObjStarter(model, 1, 1);

        Mockito.verify(starterCard).setSide(1);
        Mockito.verify(playerCardArea).setStarterNode();
        Mockito.verify(playerCardArea).setSecretObjective(secretObjective1);
        assertTrue(tempSecretObjectives.isEmpty());
    }

    /**
     * Tests the findIdinHand method to verify that the correct card index is returned.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    public void testFindIdinHand() throws RemoteException {
        Card card1 = Mockito.mock(Card.class);
        Card card2 = Mockito.mock(Card.class);

        Mockito.when(card1.getId()).thenReturn(1);
        Mockito.when(card2.getId()).thenReturn(2);

        List<Card> hand = new ArrayList<>();
        hand.add(card1);
        hand.add(card2);

        Mockito.when(playerCardArea.getHand()).thenReturn(hand);

        int index = player.findIdinHand(model, 2);

        assertEquals(1, index);
    }

    /**
     * Tests the draw method to verify that a card is drawn from the correct deck
     * and added to the player's hand.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    public void testDraw() throws RemoteException {
        Card card = Mockito.mock(Card.class);
        List<Card> hand = new ArrayList<>();

        Mockito.when(drawingCardArea.drawCardFromDeck(Type.RESOURCES)).thenReturn(card);
        Mockito.when(playerCardArea.getHand()).thenReturn(hand);

        player.draw(model, 0);

        assertTrue(hand.contains(card));
        Mockito.verify(model).notifyModelChange(Mockito.eq("testPlayer"), Mockito.anyString(), Mockito.anyString());
    }

    /**
     * Tests the updateScoreboardPoints method to verify that the scoreboard points
     * are updated correctly.
     */
    public void testUpdateScoreboardPoints() {
        Mockito.when(counter.getPointCounter()).thenReturn(10);
        Mockito.when(playerCardArea.getCounter()).thenReturn(counter);

        ScoreBoard scoreBoard = Mockito.mock(ScoreBoard.class);
        Mockito.when(scoreBoard.getPlayerPoint(player)).thenReturn(5);
        Mockito.when(model.getScoreBoard()).thenReturn(scoreBoard);

        player.updateScoreboardPoints(model);

        Mockito.verify(scoreBoard).updatePlayerPoint(player, 10);
    }

    /**
     * Tests the chatS method to verify that a chat message is sent to the model.
     *
     * @throws RemoteException if a remote communication error occurs.
     */
    public void testChatS() throws RemoteException {
        player.chatS(model, "testPlayer", "Hello World");

        Mockito.verify(model).addChat("testPlayer:Hello World");
        Mockito.verify(model).notifyModelGeneric("chatC:testPlayer,Hello World");
    }
}
