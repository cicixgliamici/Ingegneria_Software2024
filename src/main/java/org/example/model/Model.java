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


    /** Constructor of the Model
     *
     */
    public Model() throws IOException, ParseException {
        drawingCardArea = new DrawingCardArea();  //creates a drawing card area that creates the decks
        scoreBoard = new ScoreBoard(); //creates a unique scoreboard for each player
        gameArea = new HashMap<>();
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
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            ObjectiveCard.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        }
        PublicObjective = new ArrayList<>();
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
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
}
