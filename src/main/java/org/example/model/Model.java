package org.example.model;

//* import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.controller.Player;
import org.example.model.PlayArea.*;
import org.example.model.deck.enumeration.Type;
import org.json.simple.parser.ParseException;
import org.example.model.deck.*;
import org.example.model.PlayArea.DrawingCardArea;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/** Class in which the Model is properly set up to start the match
 */

public class Model {
    private DrawingCardArea drawingCardArea;     // Initializes all the Decks and the Cards on the Board
    private HashMap<Player, Token> players;     // Associates Player to Token
    private HashMap<Player, PlayerCardArea> gameArea;
    private ScoreBoard scoreBoard;              // Object scoreboard to memorize points
    private List<Player> PlayersList;


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
            PlayerCardArea playerCardArea = gameArea.get(player);
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.GOLD));
        }
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


    /** Associates to each player
     * their own player area
     */
    public void setPlayersAndGameArea(List<Player> playersList) {
        PlayersList = playersList;
        for (Player p : playersList){
            Card starter = drawingCardArea.drawCardFromDeck(Type.STARTER);
            PlayerCardArea playersCardArea = new PlayerCardArea(starter);
            gameArea.put(p, playersCardArea);
            if (p.ChooseStarterSide(gameArea.get(p))==1){
                starter.setSide(1);
                playersCardArea.UpdateCounter(starter);
            } else {
                starter.setSide(2);
                playersCardArea.UpdateCounter(starter);
            }
        }
    }

    /** Getter of the HashMap
     *
     */
    public PlayerCardArea getPlayerArea(Player P){
          return gameArea.get(P);
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