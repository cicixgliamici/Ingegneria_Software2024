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
    protected static String firstPlayer;

    /**
     * Default constructor for the View class.
     */
    public View() {
    }


    public void removeVisibleArea(int id) {}

    /**
     * Abstract method to set the first player.
     */
    public abstract void setFirst();

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

    public abstract void lastRound();

    public abstract void Winner(String string);

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

    public int getFlag() {
        return flag;
    }

    public Coordinates getPosition(JSONObject jsonObject) {
        return map.get(jsonObject);
    }

    public List<Integer> getHand() {
        return Hand;
    }

    public List<Integer> getPlayerCardArea() {
        return PlayerCardArea;
    }

    public Integer getSecretObjective() {
        return SecretObjective;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isMatchStarted() {
        return matchStarted;
    }

    public List<String> getColors() {
        return colors;
    }

    public int getNumConnection() {
        return numConnection;
    }

    public List<String> getCardsPath() {
        return cardsPath;
    }

    public List<Integer> getCardsId() {
        return cardsId;
    }

    public List<Integer> getDrawableCards() {
        return drawableCards;
    }

    public List<Integer> getPublicObjectives() {
        return PublicObjectives;
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public int getValidPlay() {
        return validPlay;
    }

    public int isTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setMatchStarted(boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

    public void setGameAreaPanel(GameAreaPanel gameAreaPanel) {
        this.gameAreaPanel = gameAreaPanel;
    }

    public void setDrawingCardPanel(DrawingCardPanel drawingCardPanel) {
        this.drawingCardPanel = drawingCardPanel;
    }

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

    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    public abstract void close();
}