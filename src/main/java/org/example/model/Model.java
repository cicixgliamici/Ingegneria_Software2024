package org.example.model;

import org.example.controller.Player;
import org.example.model.playarea.*;
import org.example.enumeration.Type;
import org.example.server.ModelChangeListener;
import org.json.simple.parser.ParseException;
import org.example.model.deck.*;
import org.example.model.playarea.DrawingCardArea;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class in which the Model is properly set up to start the match.
 */
public class Model {
    private DrawingCardArea drawingCardArea; // Initializes all the Decks and the Cards on the Board
    private final HashMap<Player, PlayerCardArea> gameArea;
    private final ScoreBoard scoreBoard; // Object scoreboard to memorize points
    private List<Player> PlayersList;
    private List<Card> PublicObjective;
    private int currentPlayer;
    private List<ModelChangeListener> listeners = new ArrayList<>(); // List of other classes that are interested when something changes (only the Server)
    private List<String> chat = new ArrayList<>();

    /**
     * Constructor of the Model.
     *
     * @throws IOException
     * @throws ParseException
     */
    public Model() throws IOException, ParseException {
        drawingCardArea = new DrawingCardArea(); // Creates a drawing card area that creates the decks
        scoreBoard = new ScoreBoard(); // Creates a unique scoreboard for each player
        gameArea = new HashMap<>();
        PublicObjective = new ArrayList<>();
    }

    /**
     * This method iterates over the `PlayersList` (since iterating directly over a HashMap is not feasible).
     * It draws cards from various decks and assigns them to each player.
     *
     * It then sends a notification to each player with their initial hand and objectives using the `notifyModelSpecific` method.
     */
    public void DealCards() {
        PublicObjective = new ArrayList<>();
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        PublicObjective.add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
        for (Player player : PlayersList) {
            PlayerCardArea playerCardArea = gameArea.get(player);
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.RESOURCES));
            playerCardArea.getHand().add(drawingCardArea.drawCardFromDeck(Type.GOLD));
            playerCardArea.setCardStarter(drawingCardArea.drawCardFromDeck(Type.STARTER));
            playerCardArea.getTempSecretObjective().add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            playerCardArea.getTempSecretObjective().add(drawingCardArea.drawCardFromDeck(Type.OBJECT));
            try {
                notifyModelSpecific(player.getUsername(), "firstHand:" +
                        playerCardArea.getHand().get(0).getId() + "," +
                        playerCardArea.getHand().get(1).getId() + "," +
                        playerCardArea.getHand().get(2).getId() + "," +
                        playerCardArea.getCardStarter().getId() + "," +
                        playerCardArea.getTempSecretObjective().get(0).getId() + "," +
                        playerCardArea.getTempSecretObjective().get(1).getId());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows the visible area.
     */
    public void showArea() {
        try {
            notifyModelGeneric("visibleArea:"
                    + DrawingCardArea.getCardIdFromDeck(Type.RESOURCES) + ","
                    + DrawingCardArea.getCardIdFromDeck(Type.GOLD) + ","
                    + DrawingCardArea.getVisibleReCard().get(0).getId() + ","
                    + DrawingCardArea.getVisibleReCard().get(1).getId() + ","
                    + DrawingCardArea.getVisibleGoCard().get(0).getId() + ","
                    + DrawingCardArea.getVisibleGoCard().get(1).getId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Shows the public objectives.
     */
    public void showPublicObjective() {
        try {
            notifyModelGeneric("pubObj:" +
                    getPublicObjective().get(0).getId() + ","
                    + getPublicObjective().get(1).getId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the players and their game areas.
     *
     * @param playersList the list of players
     */
    public void setPlayersAndGameArea(List<Player> playersList) {
        PlayersList = playersList;
        for (Player p : playersList) {
            PlayerCardArea playersCardArea = new PlayerCardArea();
            gameArea.put(p, playersCardArea);
        }
    }

    // Getter and Setter Area

   /**
 * Returns the list of players in the game.
 * 
 * @return the list of players
 */
public List<Player> getPlayersList() {
    return PlayersList;
}

/**
 * Returns the drawing card area of the game.
 * 
 * @return the drawing card area
 */
public DrawingCardArea getDrawingCardArea() {
    return drawingCardArea;
}

/**
 * Returns the score board of the game.
 * 
 * @return the score board
 */
public ScoreBoard getScoreBoard() {
    return scoreBoard;
}

/**
 * Returns the list of public objective cards.
 * 
 * @return the list of public objective cards
 */
public List<Card> getPublicObjective() {
    return PublicObjective;
}

/**
 * Sets the drawing card area of the game.
 * 
 * @param drawingCardArea the new drawing card area
 */
public void setDrawingCardArea(DrawingCardArea drawingCardArea) {
    this.drawingCardArea = drawingCardArea;
}

/**
 * Sets the list of players in the game.
 * 
 * @param playersList the new list of players
 */
public void setPlayersList(List<Player> playersList) {
    PlayersList = playersList;
}

/**
 * Returns the player's card area from the game area HashMap.
 *
 * @param P the player
 * @return the player's card area
 */
public PlayerCardArea getPlayerCardArea(Player P) {
    return gameArea.get(P);
}

/**
 * Returns the index of the current player.
 * 
 * @return the index of the current player
 */
public int getCurrentPlayer() {
    return currentPlayer;
}

/**
 * Returns the game area HashMap, which maps players to their card areas.
 * 
 * @return the game area HashMap
 */
public HashMap<Player, PlayerCardArea> getGameArea() {
    return gameArea;
}

/**
 * Sets the list of public objective cards.
 * 
 * @param publicObjective the new list of public objective cards
 */
public void setPublicObjective(List<Card> publicObjective) {
    PublicObjective = publicObjective;
}


    // Listeners Zone

    /**
     * Adds a ModelChangeListener to the list.
     *
     * @param listener the listener to add
     */
    public void addModelChangeListener(ModelChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Called when something changes in the Model from the methods like Draw and Play.
     *
     * @param username the username of the player
     * @param specificMessage the specific message for the player
     * @param generalMessage the general message for all players
     * @throws RemoteException if a remote communication error occurs
     */
    public void notifyModelChange(String username, String specificMessage, String generalMessage) throws RemoteException {
        for (ModelChangeListener listener : listeners) {
            listener.onModelChange(username, specificMessage, generalMessage);
        }
    }

    /**
     * Notifies a specific player about a model change.
     *
     * @param username the username of the player
     * @param specificMessage the specific message for the player
     * @throws RemoteException if a remote communication error occurs
     */
    public void notifyModelSpecific(String username, String specificMessage) throws RemoteException {
        for (ModelChangeListener listener : listeners) {
            listener.onModelSpecific(username, specificMessage);
        }
    }

    /**
     * Notifies all players about a general model change.
     *
     * @param generalMessage the general message for all players
     * @throws RemoteException if a remote communication error occurs
     */
    public void notifyModelGeneric(String generalMessage) throws RemoteException {
        for (ModelChangeListener listener : listeners) {
            listener.onModelGeneric(generalMessage);
        }
    }

    /**
     * Adds a message to the chat.
     *
     * @param message the message to add
     */
    public void addChat(String message) {
        chat.add(message);
    }
}
