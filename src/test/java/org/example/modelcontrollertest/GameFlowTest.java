package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.controller.GameFlow;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.model.playarea.PlayerCardArea;
import org.example.model.playarea.Counter;
import org.example.server.Server;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test case for the GameFlow class.
 */
public class GameFlowTest extends TestCase {

    private Model model;
    private Server server;
    private List<Player> players;
    private GameFlow gameFlow;

    /**
     * Sets up the test environment.
     * @throws Exception if an error occurs during setup.
     */
    protected void setUp() throws Exception {
        super.setUp();
        model = Mockito.mock(Model.class);
        server = Mockito.mock(Server.class);
        players = new ArrayList<>();
        players.add(new Player("Carlo VIII di Valois, detto l'Affabile"));
        players.add(new Player("Edoardo IV di York"));
        gameFlow = new GameFlow(players, model, server);
    }

    /**
     * Test for the incrementTurn method.
     * Verifies that the turn counter is incremented correctly.
     */
    public void testIncrementTurn() {
        gameFlow.setMaxTurn(new AtomicInteger(2));
        gameFlow.incrementTurn();
        assertEquals(2, gameFlow.getTurn().get());

        gameFlow.incrementTurn();
        assertEquals(1, gameFlow.getTurn().get());
    }

    /**
     * Test for the isYourTurn method.
     * Verifies that the method correctly identifies the player's turn based on the command.
     */
    public void testIsYourTurn() throws RemoteException {
        gameFlow.setMaxTurn(new AtomicInteger(2));
        gameFlow.setTurn(new AtomicInteger(1));

        assertTrue(gameFlow.isYourTurn("Gaio Giulio Cesare Ottaviano", "play"));
        assertFalse(gameFlow.isYourTurn("Gaio Giulio Cesare Ottaviano", "draw"));
        gameFlow.incrementTurn();
        assertTrue(gameFlow.isYourTurn("Gaio Giulio Cesare Ottaviano", "draw"));
        assertFalse(gameFlow.isYourTurn("Gaio Giulio Cesare Ottaviano", "play"));
    }

    /**
     * Test for the endGame method.
     * Verifies that the end game logic works correctly and identifies the winner.
     */
    public void testEndGame() throws RemoteException {
        // Create mocks for player card areas and point counters
        PlayerCardArea player1CardArea = Mockito.mock(PlayerCardArea.class);
        PlayerCardArea player2CardArea = Mockito.mock(PlayerCardArea.class);
        Counter player1Counter = new Counter();
        Counter player2Counter = new Counter();

        // Mock methods to simulate player scores
        player1Counter.addPoint(10);
        player2Counter.addPoint(15);

        Mockito.when(player1CardArea.getCounter()).thenReturn(player1Counter);
        Mockito.when(player2CardArea.getCounter()).thenReturn(player2Counter);

        // Mock the model to return the mocked player card areas
        Mockito.when(model.getPlayerCardArea(players.get(0))).thenReturn(player1CardArea);
        Mockito.when(model.getPlayerCardArea(players.get(1))).thenReturn(player2CardArea);

        gameFlow.endGame();

        // Verify the server is notified with the correct winner message
        Mockito.verify(server).onModelGeneric("Winner:the winner is player2 with 15 points");
    }

    /**
     * Test for the checkTurn method.
     * Verifies that the method correctly identifies if it's the right turn for a given command.
     */
    public void testCheckTurn() throws RemoteException {
        gameFlow.setMaxTurn(new AtomicInteger(4));
        gameFlow.setTurn(new AtomicInteger(1));

        assertTrue(gameFlow.isYourTurn("Gaio Giulio Cesare Germanico", "play"));
        gameFlow.incrementTurn();
        assertTrue(gameFlow.isYourTurn("Gaio Giulio Cesare Germanico", "draw"));
        gameFlow.incrementTurn();
        assertTrue(gameFlow.isYourTurn("Tito Flavio Vespasiano", "play"));
        gameFlow.incrementTurn();
        assertTrue(gameFlow.isYourTurn("Tito Flavio Vespasiano", "draw"));
    }

    /**
     * Test for the startLastRound method.
     * Verifies that the last round is correctly started.
     */
    public void testStartLastRound() {
        gameFlow.startLastRound();
        assertTrue(gameFlow.lastRoundStarted);
    }

    /**
     * Test for the getNextPlayer method.
     * Verifies that the next player is correctly set and retrieved.
     */
    public void testGetNextPlayer() throws RemoteException {
        gameFlow.setMaxTurn(new AtomicInteger(2));
        gameFlow.setTurn(new AtomicInteger(1));

        gameFlow.isYourTurn("Iovio Diocle Gaio Aurelio Valerio Diocleziano", "play");
        assertEquals("Gaio Valerio Galerio Massimiano", gameFlow.getNextPlayer());

        gameFlow.incrementTurn();
        gameFlow.isYourTurn("Iovio Diocle Gaio Aurelio Valerio Diocleziano", "draw");
        assertEquals("Gaio Valerio Galerio Massimiano", gameFlow.getNextPlayer());
    }
}
