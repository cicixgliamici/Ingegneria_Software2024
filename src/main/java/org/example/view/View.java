package org.example.view;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Abstract base class for views in the application.
 * This class defines the common structure and behavior for different types of views
 * that display the game state and interact with the user.
 */
public abstract class View {
    protected List<Integer> Hand = new ArrayList<>();  // List to hold cards currently in the player's hand.
    protected List<Integer> PlayerCardArea = new ArrayList<>();  // List to hold cards placed in the play area.
    protected Integer SecretObjective;  // Variable to store secret objectives if any.
    protected Integer[][] grid;  // 2D array to represent the spatial layout of cards on the game board.
    final int N = 9;  // Size of the grid.
    final int M = (N - 1) / 2;  // Middle index of the grid, used for centering the play area.

    /**
     * Constructor that initializes the grid.
     * Each cell of the grid is initialized to null indicating no card is placed.
     */

    public View() {
        grid = new Integer[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(grid[i], null);  // Fill each row of the grid with null.
        }
    }

    // Server message interpreter, to be implemented by subclasses.
    public abstract void Interpreter(String message);

    // Retrieves a card by its ID, to be implemented by subclasses.
    public abstract JSONObject getCardById(int id);

    // Methods to be implemented for handling messages from the server.
    public abstract void drawnCard(int id);
    public abstract void hasDrawn(String username, int id);
    public abstract void playedCard(int id, int x, int y);
    public abstract void hasPlayed(String username, int id);
    public abstract void unplayable(int id, int x, int y);
    public abstract void firstHand(int id1, int id2, int id3, int id4, int id5, int id6);
    public abstract void setHand(int side, int choice);
    public abstract void pubObj(int id1, int id2);
    public abstract void order(String us1, String us2, String us3, String us4);
    public abstract void points(String us1, int p1, String us2, int p2, String us3, int p3, String us4, int p4);

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

    /**
     * Places a card in the grid.
     * @param x Horizontal position of the card.
     * @param y Vertical position of the card.
     * @param cardId Identifier of the card to place.
     */
    public void playCardInGrid(int x, int y, int cardId) {
        int gridX =M-y;
        int gridY = x+M;
        if (isValidPosition(gridX, gridY)) {
            grid[gridX][gridY] = cardId;
            System.out.println("Card " + cardId + " played at grid position: (" + x + ", " + y + ")");
        } else {
            System.out.println("Invalid grid position: (" + x + ", " + y + ")");
        }
    }

    public abstract void printPlayerCardArea();

    public abstract void printHand();
    /**
     * Checks if a given position is valid within the grid boundaries.
     * @param gridX Horizontal position index.
     * @param gridY Vertical position index.
     * @return true if the position is valid, false otherwise.
     */
    private boolean isValidPosition(int gridX, int gridY) {
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

    public abstract void setPlayers();
}