package org.example.view;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.*;

import org.example.view.gui.gamearea4.DrawingCardPanel;
import org.example.view.gui.gamearea4.GameAreaPanel;
import org.example.view.gui.listener.DrawingCardPanelListener;
import org.example.view.gui.listener.GameAreaPanelListener;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.utilities.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Abstract class representing the view component in the MVC architecture.
 * Handles the interpretation of commands, management of game state, and UI updates.
 */
public abstract class View {

    protected int flag;
    protected int numConnection;
    protected int validPlay;
    protected int turn;
    final int N = 9;
    final int M = (N - 1) / 2;
    protected Integer SecretObjective;
    protected boolean isFirst;
    protected volatile boolean matchStarted;
    protected List<Integer> cardsId;
    protected List<Integer> drawableCards;
    protected List<Integer> Hand = new ArrayList<>();
    protected List<Integer> PlayerCardArea = new ArrayList<>();
    protected List<Integer> PublicObjectives = new ArrayList<>();
    protected List<String> cardsPath;
    protected List<String> colors;
    protected List<EvListener> listeners = new ArrayList<>();
    protected Map<String, String> colorPlayer = new HashMap<>();
    protected Map<Integer, Coordinates> map = new HashMap<>();
    protected Map<String, Integer> points;
    protected DrawingCardPanel drawingCardPanel;
    protected GameAreaPanel gameAreaPanel;
    protected EvListener evListener;
    protected static String firstPlayer=null;

    /**
     * Default constructor for the View class.
     */
    public View() {
    }

/**
 * Removes a card from the visible area based on its ID.
 *
 * @param id The ID of the card to be removed from the visible area.
 */
    public void removeVisibleArea(int id) {}

/**
 * Sets the first player in the game.
 *
 * @param username The username of the player to be set as the first player.
 */
public abstract void setFirst(String username);
    
    /**
     * Abstract method to print the game grid.
     */
    public abstract void printGrid();

    /**
     * Abstract method to print the player's card area.
     */
    public abstract void printPlayerCardArea();

    /**
     * Abstract method to print the player's hand.
     */
    public abstract void printHand();

    /**
     * Abstract method to set the number of connections.
     *
     * @param num The number of connections.
     */
    public abstract void numCon(int num);

    /**
     * Abstract method to handle a drawn card.
     *
     * @param id The ID of the drawn card.
     */
    public abstract void drawnCard(int id);

    /**
     * Abstract method to display a message.
     *
     * @param x The message code.
     */
    public abstract void message(int x);

    /**
     * Abstract method to set the player's hand.
     *
     * @param side   The side of the card.
     * @param choice The choice index.
     */
    public abstract void setHand(int side, int choice);

    /**
     * Abstract method to set the public objectives.
     *
     * @param id1 The ID of the first objective.
     * @param id2 The ID of the second objective.
     */
    public abstract void pubObj(int id1, int id2);

    /**
     * Abstract method to handle a played card.
     *
     * @param id    The ID of the played card.
     * @param side  The side of the card.
     * @param x     The x-coordinate of the card placement.
     * @param y     The y-coordinate of the card placement.
     */
    public abstract void playedCard(int id, int side, int x, int y);

    /**
     * Abstract method to handle an unplayable card.
     *
     * @param id The ID of the unplayable card.
     * @param x  The x-coordinate of the attempted placement.
     * @param y  The y-coordinate of the attempted placement.
     */
    public abstract void unplayable(int id, int x, int y);

    /**
     * Abstract method to handle a placeholder.
     *
     * @param id The ID of the placeholder.
     * @param x  The x-coordinate of the placeholder.
     * @param y  The y-coordinate of the placeholder.
     */
    public abstract void placeholder(int id, int x, int y);

    /**
     * Abstract method to notify when a card has been drawn by another player.
     *
     * @param username The username of the player who drew the card.
     * @param id       The ID of the drawn card.
     */
    public abstract void hasDrawn(String username, int id);

    /**
     * Abstract method to notify when a card has been played by another player.
     *
     * @param username The username of the player who played the card.
     * @param id       The ID of the played card.
     */
    public abstract void hasPlayed(String username, int id);

    /**
     * Abstract method to update the points of a player.
     *
     * @param username The username of the player.
     * @param points   The points of the player.
     */
    public abstract void points(String username, int points);

    /**
     * Abstract method to handle a new player connection.
     *
     * @param player The username of the new player.
     * @param color  The color chosen by the new player.
     */
    public abstract void newConnection(String player, String color);

    /**
     * Abstract method to update the setup UI.
     *
     * @param colors   The colors available for selection.
     * @param isFirst  Indicates if the player is the first player.
     */
    public abstract void updateSetupUI(String[] colors, boolean isFirst);

    /**
     * Abstract method to handle the initial hand of a player.
     *
     * @param id1 The ID of the first card.
     * @param id2 The ID of the second card.
     * @param id3 The ID of the third card.
     * @param id4 The ID of the fourth card.
     * @param id5 The ID of the fifth card.
     * @param id6 The ID of the sixth card.
     */
    public abstract void firstHand(int id1, int id2, int id3, int id4, int id5, int id6);

    /**
     * Abstract method to handle the visible area cards.
     *
     * @param id1 The ID of the first visible card.
     * @param id2 The ID of the second visible card.
     * @param id3 The ID of the third visible card.
     * @param id4 The ID of the fourth visible card.
     * @param id5 The ID of the fifth visible card.
     * @param id6 The ID of the sixth visible card.
     */
    public abstract void visibleArea(int id1, int id2, int id3, int id4, int id5, int id6);

    /**
     * Abstract method to handle color assignments.
     *
     * @param color1 The first color.
     * @param color2 The second color.
     * @param color3 The third color.
     * @param color4 The fourth color.
     */
    public abstract void color(String color1, String color2, String color3, String color4);

    /**
     * Abstract method to set the order of players.
     *
     * @param us1 The first username.
     * @param us2 The second username.
     * @param us3 The third username.
     * @param us4 The fourth username.
     * @param us5 The fifth username.
     * @param us6 The sixth username.
     * @param us7 The seventh username.
     * @param us8 The eighth username.
     */
    public abstract void order(String us1, String us2, String us3, String us4, String us5, String us6, String us7, String us8);

    /**
 * Initiates the last round of the game.
 */
public abstract void lastRound();

/**
 * Declares the winner of the game.
 *
 * @param string A string containing the information about the winner (e.g., username or other details).
 */
public abstract void Winner(String string);

/**
 * Declares a tie in the game.
 *
 * @param string A string containing the information about the tie (e.g., players involved in the tie or other details).
 */
public abstract void Tie(String string);

    /**
     * Gets the color to player mapping.
     *
     * @return The map of colors to players.
     */
    public Map<String, String> getColorPlayer() {
        return colorPlayer;
    }

    /**
     * Interprets and executes commands sent as messages.
     * This method parses a command message, identifies the corresponding method to execute based on the command name,
     * and invokes the method with the provided parameters. It utilizes reflection to dynamically invoke methods
     * on the current object.
     *
     * @param message The command message.
     */
    public void Interpreter(String message) {
        String[] parts = message.split(":");
        if (parts.length < 1) {
            System.out.println("Invalid command received.");
            return;
        }
        String command = parts[0];
        String[] parameters = parts.length > 1 ? parts[1].split(",") : new String[0];
        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(command) && method.getParameterCount() == parameters.length) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] paramValues = new Object[parameters.length];
                    for (int i = 0; parameters != null && i < parameters.length; i++) {
                        if (paramTypes[i] == int.class) {
                            paramValues[i] = Integer.parseInt(parameters[i]);
                        } else if (paramTypes[i] == String.class) {
                            paramValues[i] = parameters[i];
                        }
                    }
                    method.invoke(this, paramValues);
                    return;
                }
            }
            System.out.println("No such method exists: " + command);
        } catch (Exception e) {
            System.out.println("Error executing command " + command + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a card by its ID from JSON files.
     *
     * @param id The ID of the card.
     * @return The JSON object representing the card.
     */
    public JSONObject getCardById(int id) {
        JSONParser parser = new JSONParser();
        String[] resourcePaths = {
                "/Card.json",
                "/GoldCard.json",
                "/ObjectiveCard.json",
                "/StarterCard.json"
        };
        for (String resourcePath : resourcePaths) {
            try (InputStream inputStream = getClass().getResourceAsStream(resourcePath);
                 InputStreamReader reader = new InputStreamReader(inputStream)) {
                if (inputStream == null) {
                    System.err.println("Resource not found: " + resourcePath);
                    continue;
                }
                JSONArray cards = (JSONArray) parser.parse(reader);
                for (Object cardObj : cards) {
                    JSONObject card = (JSONObject) cardObj;
                    if (((Long) card.get("id")).intValue() == id) {
                        return card;
                    }
                }
            } catch (IOException | ParseException e) {
                System.err.println("Error reading or parsing the JSON resource: " + resourcePath + ", " + e.getMessage());
            }
        }
        return null;
    }

    //public abstract void players(String username1, String username2, String username3, String username4);

    /**
     * Updates the player's hand with a new card ID.
     *
     * @param id The ID of the new card.
     */
    public void updateHand(int id) {
        Hand.add(id);
        notifyListeners(new Event(this, "handUpdated"));
    }

    /**
     * Updates the player's card area with a new card ID.
     *
     * @param id The ID of the new card.
     */
    public void updatePlayerCardArea(int id) {
        PlayerCardArea.add(id);
    }

    /**
     * Removes a card ID from the player's hand.
     *
     * @param id The ID of the card to remove.
     */
    public void removeHand(int id) {
        Hand.removeIf(id1 -> id1.equals(id));
    }

    /**
     * Removes a card ID from the player's card area.
     *
     * @param id The ID of the card to remove.
     */
    public void removePlayerCardArea(int id) {
        PlayerCardArea.remove(id);
    }

    /**
     * Handles chat messages.
     *
     * @param username The username of the sender.
     * @param message  The chat message.
     */
    public void chatC(String username, String message) {
        System.out.println(message);
    }

    /**
     * Checks if the given grid position is valid.
     *
     * @param gridX The x-coordinate.
     * @param gridY The y-coordinate.
     * @return True if the position is valid, false otherwise.
     */
    public boolean isValidPosition(int gridX, int gridY) {
        return gridX >= 0 && gridX < N && gridY >= 0 && gridY < N;
    }

/**
 * Returns the flag value.
 * 
 * @return the flag value
 */
public int getFlag() {
    return flag;
}

/**
 * Returns the position coordinates based on the given JSON object.
 * 
 * @param jsonObject the JSON object containing position data
 * @return the position coordinates
 */
public Coordinates getPosition(JSONObject jsonObject) {
    return map.get(jsonObject);
}

/**
 * Returns the list of card IDs in the player's hand.
 * 
 * @return the list of card IDs in the hand
 */
public List<Integer> getHand() {
    return Hand;
}

/**
 * Returns the list of card IDs in the player's card area.
 * 
 * @return the list of card IDs in the player's card area
 */
public List<Integer> getPlayerCardArea() {
    return PlayerCardArea;
}

/**
 * Returns the secret objective card ID.
 * 
 * @return the secret objective card ID
 */
public Integer getSecretObjective() {
    return SecretObjective;
}

/**
 * Returns the value of N.
 * 
 * @return the value of N
 */
public int getN() {
    return N;
}

/**
 * Returns the value of M.
 * 
 * @return the value of M
 */
public int getM() {
    return M;
}

/**
 * Returns true if it is the first turn.
 * 
 * @return true if it is the first turn, false otherwise
 */
public boolean isFirst() {
    return isFirst;
}

/**
 * Returns true if the match has started.
 * 
 * @return true if the match has started, false otherwise
 */
public boolean isMatchStarted() {
    return matchStarted;
}

/**
 * Returns the list of player colors.
 * 
 * @return the list of player colors
 */
public List<String> getColors() {
    return colors;
}

/**
 * Returns the number of connections.
 * 
 * @return the number of connections
 */
public int getNumConnection() {
    return numConnection;
}

/**
 * Returns the list of card paths.
 * 
 * @return the list of card paths
 */
public List<String> getCardsPath() {
    return cardsPath;
}

/**
 * Returns the list of card IDs.
 * 
 * @return the list of card IDs
 */
public List<Integer> getCardsId() {
    return cardsId;
}

/**
 * Returns the list of drawable card IDs.
 * 
 * @return the list of drawable card IDs
 */
public List<Integer> getDrawableCards() {
    return drawableCards;
}

/**
 * Returns the list of public objective card IDs.
 * 
 * @return the list of public objective card IDs
 */
public List<Integer> getPublicObjectives() {
    return PublicObjectives;
}

/**
 * Returns the map of player points.
 * 
 * @return the map of player points
 */
public Map<String, Integer> getPoints() {
    return points;
}

/**
 * Returns the valid play count.
 * 
 * @return the valid play count
 */
public int getValidPlay() {
    return validPlay;
}

/**
 * Returns the current turn number.
 * 
 * @return the current turn number
 */
public int isTurn() {
    return turn;
}

/**
 * Sets the current turn number.
 * 
 * @param turn the current turn number
 */
public void setTurn(int turn) {
    this.turn = turn;
}

/**
 * Sets the match started status.
 * 
 * @param matchStarted the match started status
 */
public void setMatchStarted(boolean matchStarted) {
    this.matchStarted = matchStarted;
}

/**
 * Sets the game area panel.
 * 
 * @param gameAreaPanel the game area panel
 */
public void setGameAreaPanel(GameAreaPanel gameAreaPanel) {
    this.gameAreaPanel = gameAreaPanel;
}

/**
 * Sets the drawing card panel.
 * 
 * @param drawingCardPanel the drawing card panel
 */
public void setDrawingCardPanel(DrawingCardPanel drawingCardPanel) {
    this.drawingCardPanel = drawingCardPanel;
}

/**
 * Adds an event listener.
 * 
 * @param listener the event listener to add
 */
public void addListener(EvListener listener) {
    listeners.add(listener);
}

    /**
     * Notifies all listeners of an event.
     *
     * @param event The event to notify listeners about.
     */
    public void notifyListeners(Event event) {
        for (EvListener listener : listeners) {
            try {
                listener.eventListener(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a mapping from an integer to coordinates.
     *
     * @param integer The integer key.
     * @param x       The x-coordinate.
     * @param y       The y-coordinate.
     */
    public void addMapping(Integer integer, int x, int y) {
        map.put(integer, new Coordinates(x, y));
    }
    
/**
 * Sets the event listener.
 * 
 * @param evListener the event listener to set
 */
public void setEvListener(EvListener evListener) {
    this.evListener = evListener;
}

/**
 * Closes resources or connections related to this object.
 */
public abstract void close();
}
