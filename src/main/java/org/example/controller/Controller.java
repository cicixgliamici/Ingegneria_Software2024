package org.example.controller;

import org.example.model.Model;
import org.example.model.deck.enumeration.Color;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        //player di prova
        Player p1= new Player("Matteo");
        players.add(p1);
        for (Player p : players) {
            int i = 0;
            List<String> colorStrings = Arrays.stream(Color.values())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose your token color:");
            for (Color color : Color.values()) {
                System.out.println(i + ". " + color);
                i++;
            }
            int chosenColorIndex = scanner.nextInt();
            Color chosenColor = Color.values()[chosenColorIndex];
            model.getScoreBoard().addToken(chosenColor , p);
        }
        return players;
    }
    public void CalculateObjective (){
        //todo: metodo che per ogni player calcola i punti degli obbiettivi pubblici e nascosti
    }

    public void Winner(){
        // todo: compara i punti totali dei player e restituisce il vincitore
    }

}
