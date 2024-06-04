package org.example.controller;

import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for managing the players and interactions
 * with the Model.
 */
public class Controller {
    
    private List<Player> players;
    private Model model;

    /**
     * Constructor for the Controller class.
     * 
     * @param model The model instance to interact with.
     * @throws IOException If an input or output exception occurred.
     * @throws ParseException If a parsing exception occurred.
     */
    public Controller(Model model) throws IOException, ParseException {
        this.model = model;
        this.players = new ArrayList<>();
    }

    /**
     * Sets the list of players.
     * 
     * @param players A list of players to be set.
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Initializes the controller by setting players and game area in the model
     * and dealing cards.
     */
    public void initializeController(){
        model.setPlayersAndGameArea(players);  // Passes the list with all the players to the model
        model.DealCards();
    }

    /**
     * Displays the drawable area using the model.
     */
    public void drawableArea(){
        model.showArea();
    }

    /**
     * Displays the public objective using the model.
     */
    public void publicObj(){
        model.showPublicObjective();
    }

    /**
     * Retrieves a player by their username.
     * 
     * @param username The username of the player to be retrieved.
     * @return The player with the specified username, or null if not found.
     */
    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
}
