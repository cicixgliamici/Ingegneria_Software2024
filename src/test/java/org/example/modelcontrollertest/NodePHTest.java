package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.controller.Player;
import org.example.enumeration.Type;
import org.example.exception.InvalidCardException;
import org.example.exception.PlaceholderNotValid;
import org.example.model.Model;
import org.example.model.playarea.Node;
import org.example.model.playarea.PlaceHolder;
import org.example.model.deck.Card;
import org.example.model.deck.Deck;
import org.example.model.playarea.PlayerCardArea;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodePHTest extends TestCase {

    public void testPlaceHolder() {
        PlaceHolder placeholder = new PlaceHolder(12,24);
        assertEquals(12, placeholder.getX());
        assertEquals(24, placeholder.getY());
        String expectedToString = "Placeholder 12 : 24";
        assertEquals(expectedToString, placeholder.toString());
        assertNull(placeholder.getBotL());
    }

    /** Constructors
     */
        public void testFCNode() throws IOException, ParseException {
            Deck starterDeck = new Deck(Type.STARTER);
            Card starterCard = starterDeck.drawCard();
            List<PlaceHolder> placeHolderList = new ArrayList<>();
            List<PlaceHolder> availableNodes = new ArrayList<>();
            List<PlaceHolder> allNodes = new ArrayList<>();
            Node node = new Node(starterCard, 0, 0, placeHolderList, availableNodes, allNodes);
            assertNotNull(node);
            assertEquals(0, node.getX());
            assertEquals(0, node.getY());
            assertEquals(starterCard, node.getCard());
            assertNotNull(node.getTopL());
            assertNotNull(node.getTopR());
            assertNotNull(node.getBotL());
            assertNotNull(node.getBotR());
            assertTrue(node.getTopL() instanceof PlaceHolder);
            assertTrue(node.getTopR() instanceof PlaceHolder);
            assertTrue(node.getBotL() instanceof PlaceHolder);
            assertTrue(node.getBotR() instanceof PlaceHolder);
        }


    public void testSCNode() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node node = new Node(starterCard, null, null, null, null, 0,0);
        assertEquals(0, node.getX());
        assertEquals(0, node.getY());
        assertNotNull(starterCard);
        assertNotNull(starterCard);
        assertNotNull(node);
        assertNull(node.getTopL());
        assertNull(node.getTopR());
        assertNull(node.getBotL());
        assertNull(node.getBotR());
    }

    /** Test Null Node
     */
    public void testNullNodeTopR() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        Node topR = starterNode.nullNodeTopR();
        assertNull(topR.getCard());
        assertNull(topR.getTopL());
        assertNull(topR.getTopR());
        assertNotNull(topR.getBotL());
        assertNull(topR.getBotR());
    }
    public void testNullNodeTopL() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        Node topL = starterNode.nullNodeTopL();
        assertNull(topL.getCard());
        assertNull(topL.getTopL());
        assertNull(topL.getTopR());
        assertNull(topL.getBotL());
        assertNotNull(topL.getBotR());
    }

    public void testNullNodeBotR() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        Node botR = starterNode.nullNodeBotR();
        assertNull(botR.getCard());
        assertNotNull(botR.getTopL());
        assertNull(botR.getTopR());
        assertNull(botR.getBotL());
        assertNull(botR.getBotR());
    }

    public void testNullNodeBotL() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        Node botL = starterNode.nullNodeBotL();
        assertNull(botL.getCard());
        assertNull(botL.getTopL());
        assertNotNull(botL.getTopR());
        assertNull(botL.getBotL());
        assertNull(botL.getBotR());
    }

    public void testSetNullNode() throws IOException{
        List<PlaceHolder> availableNodes = new ArrayList<>();
        Deck deck = new Deck(Type.STARTER);
        Card starterCard= deck.getCards().get(5);
        starterCard.setSide(1);
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        starterNode.setNullNode(availableNodes);

    }

    /** Test particular setters
     */
    public void testSetPlaceHolderByCard() throws IOException, ParseException, PlaceholderNotValid, InvalidCardException {
        Model model = new Model();
        Deck deckRes = new Deck(Type.RESOURCES);
        Deck deckStarter = new Deck(Type.STARTER);
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("Cartesio");
        model.getPlayersList().add(player1);
        Card starter = deckStarter.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=new PlayerCardArea();
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        model.getGameArea().put(player1, playerCardArea);
        Card res= deckRes.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(res);
        player1.play(model, 0, 1,1,1);
        assertEquals(1, model.getPlayerCardArea(player1).getPlaceHolders().size());
        PlaceHolder placeHolder= model.getPlayerCardArea(player1).getPlaceHolders().get(0);
        assertEquals(2, placeHolder.getX());
        assertEquals(0, placeHolder.getY());

    }

    public void testSetNodePlaceHolder() throws IOException, PlaceholderNotValid, InvalidCardException, ParseException {
        Model model = new Model();
        Deck deckRes = new Deck(Type.RESOURCES);
        Deck deckStarter = new Deck(Type.STARTER);
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("Pascal");
        model.getPlayersList().add(player1);
        Card starter = deckStarter.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=new PlayerCardArea();
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        model.getGameArea().put(player1, playerCardArea);
        Card res1= deckRes.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(res1);
        player1.play(model, 0, 1,1,1);
        Card res2= deckRes.getCards().get(1);
        model.getPlayerCardArea(player1).getHand().add(res2);
        player1.play(model, 0, 2,1,-1);
        assertTrue(model.getPlayerCardArea(player1).getNodeByXY(1,-1).getTopR() instanceof PlaceHolder);
        assertEquals(1, model.getPlayerCardArea(player1).getPlaceHolders().size());
    }

    public void testSetNodeForExistingCard() throws IOException, ParseException, PlaceholderNotValid, InvalidCardException{
        Model model = new Model();
        Deck deckRes = new Deck(Type.RESOURCES);
        Deck deckStarter = new Deck(Type.STARTER);
        List<Player> playerslist=new ArrayList<>();
        model.setPlayersList(playerslist);
        Player player1 = new Player("Smith");
        model.getPlayersList().add(player1);
        Card starter = deckStarter.getCards().get(0);
        starter.setSide(1);
        PlayerCardArea playerCardArea=new PlayerCardArea();
        playerCardArea.setCardStarter(starter);
        playerCardArea.setStarterNode();
        model.getGameArea().put(player1, playerCardArea);
        Card res1= deckRes.getCards().get(0);
        model.getPlayerCardArea(player1).getHand().add(res1);
        player1.play(model, 0, 2,1,1);
        Card res2= deckRes.getCards().get(1);
        model.getPlayerCardArea(player1).getHand().add(res2);
        player1.play(model, 0, 2,-1,1);
        Card res3= deckRes.getCards().get(2);
        model.getPlayerCardArea(player1).getHand().add(res3);
        player1.play(model, 0, 2,0,2);
        assertEquals(2, model.getPlayerCardArea(player1).getNodeByXY(0,2).getCard().getCoveredCornerByCard());
        assertEquals(8, model.getPlayerCardArea(player1).getAvailableNodes().size());
        assertEquals(4, model.getPlayerCardArea(player1).getAllNodes().size());
        assertEquals(0, model.getPlayerCardArea(player1).getPlaceHolders().size());
    }

    public void testToString() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        Node starterNode = new Node(starterCard, null, null, null, null, 0,0);
        String expectedToString = "nodo: 0 0";
        assertEquals(expectedToString, starterNode.toString());
    }
    public void testSetGet() throws IOException {
        Deck starterDeck = new Deck(Type.STARTER);
        Card starterCard = starterDeck.drawCard();
        PlaceHolder placeHolder1 = new PlaceHolder(1,1);
        PlaceHolder placeHolder2 = new PlaceHolder(1,-1);
        PlaceHolder placeHolder3 = new PlaceHolder(-1,1);
        PlaceHolder placeHolder4 = new PlaceHolder(-1,-1);
        Node node = new Node(starterCard, placeHolder3, placeHolder1, placeHolder4  , placeHolder2, 0,0);
        assertEquals(starterCard, node.getCard());
        assertEquals(placeHolder1, node.getTopR());
        assertEquals(placeHolder2, node.getBotR());
        assertEquals(placeHolder3, node.getTopL());
        assertEquals(placeHolder4, node.getBotL());
        Deck deck = new Deck(Type.RESOURCES);
        Card card = deck.drawCard();
        node.setCard(card);
        assertEquals(card, node.getCard());
        PlaceHolder newplaceHolder1 = new PlaceHolder(2,2);
        PlaceHolder newplaceHolder2 = new PlaceHolder(2,-2);
        PlaceHolder newplaceHolder3 = new PlaceHolder(-2,2);
        PlaceHolder newplaceHolder4 = new PlaceHolder(-2,-2);
        node.setBotL(newplaceHolder3);
        node.setBotR(newplaceHolder2);
        node.setTopL(newplaceHolder1);
        node.setTopR(newplaceHolder4);
        assertEquals(newplaceHolder3, node.getBotL());
        assertEquals(newplaceHolder1, node.getTopL());
        assertEquals(newplaceHolder2, node.getBotR());
        assertEquals(newplaceHolder4, node.getTopR());
    }
}
