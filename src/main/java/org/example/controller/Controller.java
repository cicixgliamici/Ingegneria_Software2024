package org.example.controller;

import org.example.model.Model;
import org.example.enumeration.Color;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** The Controller is a traffic warden that calls the methods to initialize the players
 *  and the gameflow, and then gives it the control.
 */

public class Controller {
    public List<Player> players= new ArrayList<>();
    GameFlow gameFlow; //per ora costruttore vuoto
    Model model;

    /** Initializes the list of players and the gameflow, then it will
     * give it the control.
     */
    public Controller(Model model) throws IOException, ParseException {
        this.model=model;
        players= this.AcceptPlayerBYTUI(); //accept all players that want to connect
        model.setPlayersAndGameArea(players);  //passes the list with all the players to the model
        model.DealCards();
        gameFlow= new GameFlow(players, model);
    }


    /** First TUI method, accept player, scan username
     *  give them the possibility to choose color.
     */
    // TODO gestire l'eccezione inserimento (Int invece che Stringe e viceversa)
        public List<Player> AcceptPlayerBYTUI() {
            //System.out.println("Welcome\n");
            //Scanner scanner = new Scanner(System.in);
            List<Player> players = new ArrayList<>();
            List<Color> availableColors = new ArrayList<>(Arrays.asList(Color.values())); // List to remove chosen Colors
            int choice = 0;
            while (choice != 2) {
                /*System.out.println("Press:" +
                        "\n1 to add a new player." +
                        "\n2 to start the match." +
                        "\n3 to end the session.");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        System.out.println("Insert the name");
                        String username = scanner.nextLine();
                        Player player = new Player(username);
                        players.add(player);
                        System.out.println("Choose your token color:");
                        for (int i = 0; i < availableColors.size(); i++) {
                            System.out.println(i + ". " + availableColors.get(i));
                        }
                        int chosenColorIndex = scanner.nextInt();
                        Color chosenColor = availableColors.get(chosenColorIndex);
                        availableColors.remove(chosenColor); // Remove the chosen color from the available ones
                        model.getScoreBoard().addToken(chosenColor, player);
                        model.getScoreBoard().getPoints().put(player,0);
                        break;

                    case 2:
                        //System.out.println("THE MATCH HAS STARTED: ");
                        break;
                    case 3:
                        //System.out.println("Goodbye.");
                        System.exit(0);
                        break;

                }
            }*/
            return players;
        }
}
