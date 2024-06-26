package org.example.modelcontrollertest;

import junit.framework.TestCase;
import org.example.controller.Controller;
import org.example.model.Model;
import org.example.controller.Player;
import org.json.simple.parser.ParseException;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test case for the Controller class.
 */
public class ControllerTest extends TestCase {

    private Model model;
    private Controller controller;

    /**
     * Sets up the test environment.
     * @throws Exception if an error occurs during setup.
     */
    protected void setUp() throws Exception {
        super.setUp();
        // Create a mock of the Model object using Mockito
        model = Mockito.mock(Model.class);
        // Initialize the controller with the mocked model
        controller = new Controller(model);
    }

    /**
     * Test for the setPlayers method.
     * Verifies that players are correctly set in the controller.
     */
    public void testSetPlayers() {
        // Create a list of players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Naruto Uzumaki");
        Player player2 = new Player("Sasuke Uchiha");
        players.add(player1);
        players.add(player2);
        // Set the list of players in the controller
        controller.setPlayers(players);
        // Use reflection to access the private players list in the controller
        try {
            java.lang.reflect.Field playersField = Controller.class.getDeclaredField("players");
            playersField.setAccessible(true);
            List<Player> actualPlayers = (List<Player>) playersField.get(controller);
            // Verify that the players list set in the controller is correct
            assertEquals(players, actualPlayers);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Fail the test if an error occurs while accessing the field
            fail("Error accessing the players field: " + e.getMessage());
        }
    }

    /**
     * Test for the initializeController method.
     * Verifies that players are set and cards are dealt correctly in the model.
     */
    public void testInitializeController() {
        // Create a list of players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("LeBron James");
        Player player2 = new Player("Michael Jeffrey Jordan");
        players.add(player1);
        players.add(player2);
        // Set the list of players in the controller
        controller.setPlayers(players);
        // Initialize the controller
        controller.initializeController();
        // Verify that the model methods are called with the correct parameters
        Mockito.verify(model).setPlayersAndGameArea(players);
        Mockito.verify(model).DealCards();
    }

    /**
     * Test for the drawableArea method.
     * Verifies that the showArea method in the model is called.
     */
    public void testDrawableArea() {
        // Call the drawableArea method in the controller
        controller.drawableArea();
        // Verify that the showArea method in the model is called
        Mockito.verify(model).showArea();
    }

    /**
     * Test for the publicObj method.
     * Verifies that the showPublicObjective method in the model is called.
     */
    public void testPublicObj() {
        // Call the publicObj method in the controller
        controller.publicObj();
        // Verify that the showPublicObjective method in the model is called
        Mockito.verify(model).showPublicObjective();
    }

    /**
     * Test for the getPlayerByUsername method.
     * Verifies that the correct player is retrieved by username.
     */
    public void testGetPlayerByUsername() {
        // Create a list of players
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Leonhard Euler");
        Player player2 = new Player("Johann Friedrich Carl Gauss");
        players.add(player1);
        players.add(player2);
        // Set the list of players in the controller
        controller.setPlayers(players);
        // Retrieve a player by username
        Player result = controller.getPlayerByUsername("Leonhard Euler");
        // Verify that the correct player is retrieved
        assertEquals(player1, result);
        // Attempt to retrieve a player that does not exist
        result = controller.getPlayerByUsername("Georg Friedrich Bernhard Riemann");
        // Verify that null is returned when the player is not found
        assertNull(result);
    }
}
