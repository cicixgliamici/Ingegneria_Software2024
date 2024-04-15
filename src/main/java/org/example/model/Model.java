package org.example.model;

//* import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.controller.Player;
import org.example.enumeration.ObjectivePoints;
import org.example.model.PlayArea.*;
import org.example.enumeration.Type;
import org.json.simple.parser.ParseException;
import org.example.model.deck.*;
import org.example.model.PlayArea.DrawingCardArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/** Class in which the Model is properly set up to start the match
 */

public class Model {
    private DrawingCardArea drawingCardArea;     // Initializes all the Decks and the Cards on the Board
    private HashMap<Player, Token> players;     // Associates Player to Token
    private HashMap<Player, PlayerCardArea> gameArea;
    private ScoreBoard scoreBoard;              // Object scoreboard to memorize points
    private List<Player> PlayersList;

    private List<Card> PublicObjective;


    /** Constructor of the Model
     *
     */
    public Model() throws IOException, ParseException {
        drawingCardArea = new DrawingCardArea();  //creates a drawing card area that creates the decks
        scoreBoard = new ScoreBoard(); //creates a unique scoreboard for each player
        players = new HashMap<>();
        //todo collegare i player ai token
        gameArea = new HashMap<>();
    }

    /** This method cycles on the PlayersList because you can't
     * do that on a HashMap.
     * Then gives the player 3 cards.
     */
    public void DealCards(){
        for (Player player : PlayersList){
            System.out.println(player + ", you are receiving 2 resource cards and 1 gold card");
            PlayerCardArea playerCardArea = gameArea.get(player);
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.GOLD));
            List<Card> ObjectiveCard= new ArrayList<>();
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            System.out.println("choose your secret objective, 0 or 1");
            System.out.println("0: " + ObjectiveCard.get(0) +"\n1: "+ ObjectiveCard.get(1));
            Scanner scanner= new Scanner(System.in);
            int choice= scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 0 :
                    playerCardArea.setSecretObjective(ObjectiveCard.get(0));
                    drawingCardArea.getObjectDeck().AddCard(ObjectiveCard.get(1));
                case 1:
                    playerCardArea.setSecretObjective(ObjectiveCard.get(1));
                    drawingCardArea.getObjectDeck().AddCard(ObjectiveCard.get(0));
            }
        }
        PublicObjective = new ArrayList<>();
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
    }

    /** Getter and Setter Area
     */
    public HashMap<Player, Token> getPlayers() {
        return players;
    }

    public HashMap<Player, PlayerCardArea> getGameArea() {
        return gameArea;
    }

    public List<Player> getPlayersList() {
        return PlayersList;
    }

    public DrawingCardArea getDrawingCardArea() {
        return drawingCardArea;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public void setDrawingCardArea(DrawingCardArea drawingCardArea) {
        this.drawingCardArea = drawingCardArea;
    }

    public void setPlayers(HashMap<Player, Token> players) {
        this.players = players;
    }

    public void setScoreBoard(ScoreBoard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public List<Card> getPublicObjective() {
        return PublicObjective;
    }

    public void setGameArea(HashMap<Player, PlayerCardArea> gameArea) {
        this.gameArea = gameArea;
    }

    /** Associates to each player
     * their own player area
     */
    public void setPlayersAndGameArea(List<Player> playersList) {
        PlayersList = playersList;
        for (Player p : playersList){
            System.out.println(p + " this is your starter card:\n");
            Card starter = drawingCardArea.drawCardFromDeck(Type.STARTER);
            System.out.println(starter);
            if (p.ChooseStarterSide()==1){
                starter.setSide(1);
            } else {
                starter.setSide(2);
            }
            PlayerCardArea playersCardArea = new PlayerCardArea(starter);
            gameArea.put(p, playersCardArea);
        }
    }

    /** Getter of the HashMap
     *
     */
    public PlayerCardArea getPlayerArea(Player P){
          return gameArea.get(P);
    }

    public boolean Checkpoints(){
        for (Player p : PlayersList){
            if(getPlayerArea(p).getCounter().getPointCounter()>=20) return true;
        }
        //todo metodo che verifica per ogni player nel model che nessuno sia arrivato a venti punti
        return false;
    }






























/*



    /** Associate the player to a color (from an Enum) and
     *  put in on the scoreboard; then add to him the cards from
     *  the drawingCardArea


    public void ConnectPlayer(Player P, Color color){
        PlayersList.add(P);
        scoreBoard.addToken(color);
        Players.put(P, scoreBoard.getToken(color));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
        P.addCard(drawingCardArea.drawCardFromDeck(Type.GOLD));
        P.setInitialCard(drawingCardArea.drawCardFromDeck(Type.STARTER));
        // TODO mettere la chiamata la metodo che assegna il l'obbiettivo segreto al giocatore
    }

    /** Add the player from the
         public void AddPlayer(Player P) {
        //il player P non viene creato qua ma deve essere creato quando si connette, nel momento della creazione sceglie gia come piazzare la sua carta inizale
        //funziona? se io devo scegliere come posizionare la carta iniziale ma non so ancora che carte ho in mano come faccio?
        //chi crea il player? lo creiamo in locale o lo crea il server?
        PlayersList.add(P);
    }

    /** Associate a player to a specific color, no usage !!

    public void MapPlayerToken(Player player, Color color) {
        scoreBoard.addToken(color);
        Players.put(player, scoreBoard.getToken(color));
    }
*/


}