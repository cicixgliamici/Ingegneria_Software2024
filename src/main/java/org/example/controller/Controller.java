package org.example.controller;

import org.example.model.Parameters;
import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** The Controller is a traffic warden that calls the methods to initialize the players
 *  and the gameflow, and then gives it the control.
 */

public class Controller {
    public List<Player> players = new ArrayList<>();
    Model model;

    /**
     * Initializes the list of players and the gameflow, then it will
     * give it the control.
     */
    public Controller(Model model) throws IOException, ParseException {
        this.model = model;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void InitializeController(){
        model.setPlayersAndGameArea(this.players);  //passes the list with all the players to the model
        model.DealCards();
    }
}