package org.example.controller;

import org.example.model.Model;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** The Controller is a traffic warden that calls the methods to initialize the players
 *  and the gameflow, and then gives it the control.
 */

public class Controller {
    List<Player> players= new ArrayList<>();
    GameFlow gameFlow; //per ora costruttore vuoto
    Model model;

    /** Initializes the list of players and the gameflow, then it will
     * give it the control.
     */
    public Controller(Model model) throws IOException, ParseException {
        //todo riceve le richieste dal server, per ora lavoriamo unicamente con la TUI
        this.model=model;
        players= this.AcceptPlayer(); //accept all players that want to connect
        model.setPlayersAndGameArea(players);  //passes the list with all the players to the model
        model.DealCards();
        gameFlow= new GameFlow(players, model);
    }



    public List<Player> AcceptPlayer(){
        //todo: accetta il player tramite richiesta TUI o Server
        return players;
    }
    public void CalculateObjective (){
        //todo: metodo che per ogni player calcola i punti degli obbiettivi pubblici e nascosti
    }

    public void Winner(){
        // todo: compara i punti totali dei player e restituisce il vincitore
    }

}
