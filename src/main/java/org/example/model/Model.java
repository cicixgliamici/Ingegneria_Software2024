package org.example.model;

import org.example.controller.Player;
import org.example.model.playarea.*;
import org.example.enumeration.Type;
import org.json.simple.parser.ParseException;
import org.example.model.deck.*;
import org.example.model.playarea.DrawingCardArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Class in which the Model is properly set up to start the match
 */

public class Model {
    private DrawingCardArea drawingCardArea;     // Initializes all the Decks and the Cards on the Board
    private final HashMap<Player, PlayerCardArea> gameArea;
    private final ScoreBoard scoreBoard;              // Object scoreboard to memorize points
    private List<Player> PlayersList;
    private List<Card> PublicObjective;
    private int currentPlayer;
    private List<ModelChangeListener> listeners = new ArrayList<>(); // List of other classes that are interested when something changes (only the Server)

    /** Constructor of the Model
     *
     */
    public Model() throws IOException, ParseException {
        drawingCardArea = new DrawingCardArea();  //creates a drawing card area that creates the decks
        scoreBoard = new ScoreBoard(); //creates a unique scoreboard for each player
        gameArea = new HashMap<>();
        PublicObjective = new ArrayList<>();
    }

    /** This method cycles on the PlayersList because you can't
     * do that on a HashMap.
     * Then gives the player 3 cards.
     */
    public void DealCards(){
        for (Player player : PlayersList){
            PlayerCardArea playerCardArea = gameArea.get(player);
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.GOLD));
            List<Card> ObjectiveCard= new ArrayList<>();
            //todo chiedere al client quale carta obbiettivo sceglie
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        }
        PublicObjective = new ArrayList<>();
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        //todo il client riceve 5 carte: le tre della sua mano e le 2 carte obbiettivo--> dovrà ritornare due valori, uno indica la carta obbiettivo scelta e una indica il lato della carta starter
        notifyModelChange("Card added to players' hands");
    }

    /** Getter and Setter Area
     */

    public List<Player> getPlayersList() {
        return PlayersList;
    }

    public DrawingCardArea getDrawingCardArea() {
        return drawingCardArea;
    }

    public ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    public List<Card> getPublicObjective() {
        return PublicObjective;
    }

    public void setDrawingCardArea(DrawingCardArea drawingCardArea){
        this.drawingCardArea = drawingCardArea;
    }


    public void setPlayersList(List<Player> playersList) {
        PlayersList = playersList;
    }

    /** Associates to each player
     * their own player area
     */
    public void setPlayersAndGameArea(List<Player> playersList) {
        PlayersList = playersList;
        for (Player p : playersList){
            //System.out.println(p + " this is your starter card:\n");
            Card starter = drawingCardArea.drawCardFromDeck(Type.STARTER);
            //System.out.println(starter);
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
    public PlayerCardArea getPlayerCardArea(Player P){
        return gameArea.get(P);
    }

    public boolean Checkpoints(){
        for (Player p : PlayersList){
            if(getPlayerCardArea(p).getCounter().getPointCounter()>=20) return true;
        }
        return false;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<Player, PlayerCardArea> getGameArea() {
        return gameArea;
    }

    public void setPublicObjective(List<Card> publicObjective) {
        PublicObjective = publicObjective;
    }

    public String tryConnection(){
        return "Connection successful";
    }




    public void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }

    // Called when something changes in the Model from the methods like Draw and Play
    public void notifyModelChange(String updateMessage) {
        for (ModelChangeListener listener : listeners) {
            listener.onModelChange(updateMessage);
        }
    }

    // Esempio di un metodo che cambia lo stato del modello
    public void changeSomethingInModel() {
        // Logica per cambiare il modello
        // ...
        notifyModelChange("Model changed, update sent to all listeners.");
    }

}
