package org.example.view;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import org.example.view.gui.utilities.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;

/**
 * Abstract base class for views in the application.
 * This class defines the common structure and behavior for different types of views
 * that display the game state and interact with the user.
 */
public abstract class View {
    protected List<Integer> cardsId;
    protected List<String> cardsPath;
    protected List<Integer> drawableCards;
    protected int flag;
    protected int numConnection;
    protected List<String> colors;
    protected List<String> players;
    protected boolean isFirst;
    protected volatile boolean matchStarted;
    protected List<Integer> Hand = new ArrayList<>();  // List to hold cards currently in the player's hand.
    protected List<Integer> PlayerCardArea = new ArrayList<>();  // List to hold cards placed in the play area.
    protected Integer SecretObjective;  // Variable to store secret objectives if any.
    final int N = 9;  // Size of the grid.
    final int M = (N - 1) / 2;  // Middle index of the grid, used for centering the play area.

    protected Map<Integer, Coordinates> map = new HashMap<>();

    public void addMapping(Integer integer, int x, int y) {
        map.put(integer, new Coordinates(x, y));
    }

    public Coordinates getPosition(JSONObject jsonObject) {
        return map.get(jsonObject);
    }
    /**
     * Constructor that initializes the grid.
     * Each cell of the grid is initialized to null indicating no card is placed.
     */

    public View() {
    }

    public void setMatchStarted(boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

    /**
     * Interprets commands received from the server and invokes the corresponding methods.
     * @param message The command message from the server.
     */
    // Server message interpreter, to be implemented by subclasses.
    public void Interpreter(String message) {
        // Parses and executes commands received from the server by reflecting the corresponding methods.
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
     * Retrieves card details from the JSON data source based on the card ID.
     * @param id The card identifier.
     * @return JSONObject containing card details.
     */
    public JSONObject getCardById(int id) {
        JSONParser parser = new JSONParser();
        String[] filePaths = {
                "src/main/resources/Card.json",
                "src/main/resources/GoldCard.json",
                "src/main/resources/ObjectiveCard.json",
                "src/main/resources/StarterCard.json"
        };
        for (String filePath : filePaths) {
            try {
                JSONArray cards = (JSONArray) parser.parse(new FileReader(filePath));
                for (Object cardObj : cards) {
                    JSONObject card = (JSONObject) cardObj;
                    if (((Long) card.get("id")).intValue() == id) {
                        return card;
                    }
                }
            } catch (IOException | ParseException e) {
                System.err.println("Error reading or parsing the JSON file: " + e.getMessage());
            }
        }
        return null;
    }

    // Methods to be implemented for handling messages from the server.
    public abstract void drawnCard(int id);
    public abstract void setFirst();
    public abstract void hasDrawn(String username, int id);
    public abstract void playedCard(int id, int x, int y);
    public abstract void hasPlayed(String username, int id);
    public abstract void unplayable(int id, int x, int y);
    public abstract void placeholder(int id, int x, int y);
    public abstract void firstHand(int id1, int id2, int id3, int id4, int id5, int id6);
    public abstract void visibleArea(int id1, int id2, int id3, int id4, int id5, int id6);
    public abstract void setHand(int side, int choice);
    public abstract void pubObj(int id1, int id2);
    public abstract void order(String us1, String us2, String us3, String us4);
    public abstract void points(String us1, int p1, String us2, int p2, String us3, int p3, String us4, int p4);

    public abstract void numCon(int num);
    /**
     * Adds a card to the player's hand.
     * @param id Card identifier.
     */
    public void updateHand(int id) {
        Hand.add(id);
    }

    /**
     * Adds a card to the play area.
     * @param id Card identifier.
     */
    public void updatePlayerCardArea(int id) {
        PlayerCardArea.add(id);
    }

    /**
     * Removes a card from the player's hand.
     * @param id Card identifier.
     */
    public void removeHand(int id) {
        Hand.removeIf(id1 -> id1.equals(id));
    }

    /**
     * Removes a card from the play area.
     * @param id Card identifier.
     */
    public void removePlayerCardArea(int id) {
        PlayerCardArea.remove(id);
    }

    public void chatC(String username, String message){
        System.out.println(message);
    };


    public abstract void printPlayerCardArea();

    public abstract void printHand();
    /**
     * Checks if a given position is valid within the grid boundaries.
     * @param gridX Horizontal position index.
     * @param gridY Vertical position index.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPosition(int gridX, int gridY) {
        return gridX >= 0 && gridX < N && gridY >= 0 && gridY < N;
    }

    /**
     * Prints the current state of the grid, showing the placement of cards.
     */
    public abstract void printGrid();

    // Method to handle generic server messages, to be implemented by subclasses.
    public abstract void message(int x);

    public abstract void updateSetupUI(String[] colors, boolean isFirst);

    public abstract void color(String color1, String color2, String color3, String color4);

    public int getFlag() {
        return flag;
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

    public List<Integer> getCardsId(){
        return cardsId;
    }

    public List<Integer> getDrawableCards() {
        return drawableCards;
    }

    public List<String> getPlayers() {
        return players;
    }
}