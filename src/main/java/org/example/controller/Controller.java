package org.example.controller;

import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Player> players;
    private Model model;

    public Controller(Model model) throws IOException, ParseException {
        this.model = model;
        this.players = new ArrayList<>();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void initializeController(){
        model.setPlayersAndGameArea(players);  // Passes the list with all the players to the model
        model.DealCards();
    }

    public void drawableArea(){
        model.showArea();
    }

    // Metodo per recuperare un giocatore tramite username
    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        return null;
    }
}
