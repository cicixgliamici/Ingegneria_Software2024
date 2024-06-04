package org.example.model.playarea;
import org.example.enumeration.Color;
import java.util.HashMap;
import java.util.Map;

import org.example.controller.*;

public class ScoreBoard {
    private final HashMap<Player, Integer> points;
    private final HashMap<String, Color> tokens; // Associate a Token to a specific color

    public ScoreBoard(){
        tokens = new HashMap<>();
        points = new HashMap<>();
    }


    /**
     * Adds a player to the scoreboard, initializing their points to zero.
     * @param player The player to be added.
     */
    public void addPlayer(Player player) {
        points.put(player, 0); // Initialize with zero points
    }

    /**
     * Adds a new token to the scoreboard, ensuring the color is not already used and
     * there are no more than the maximum number of players.
     * @param color The color of the token.
     * @param username The player associated with the token.
     * @throws IllegalArgumentException If the color is already used or the maximum number of players is exceeded.
     */
    public void addToken( String username, Color color) throws IllegalArgumentException {
        if (tokens.containsValue(color)) {
            throw new IllegalArgumentException("Select another color");
        }
        if (tokens.size() >= 4) {
            throw new IllegalArgumentException("max 4 players");
        }
        tokens.put(username, color);
        System.out.println("Negri neri");
    }

    /**
     * Updates the points of a given player.
     * @param player The player whose points are to be updated.
     * @param point The new point value.
     */
    public void updatePlayerPoint(Player player, int point){
        points.put(player, point);
    }

    /**
     * Retrieves the points of a given player. If the player is not present, initializes their points to zero.
     * @param player The player whose points are to be retrieved.
     * @return The points of the player.
     */
    public int getPlayerPoint(Player player) {
        Integer point = points.get(player);
        if (point == null) {
            points.put(player, 0); // Initialize points if not present
            return 0;
        }
        return point;
    }

    /**
     * Gets the map of all players and their points.
     * @return A hashmap of players and their points.
     */
    public HashMap<Player, Integer> getPoints() {
        return points;
    }

    /**
     * Gets the map of all tokens and their associated players.
     * @return A hashmap of tokens and their associated players.
     */
    public HashMap<String, Color> getTokens() {
        return tokens;
    }

    /**
     * Finds the player with the most points.
     * @return The player with the most points.
     */
    public Player winner(){
        Player playerWithMaxPoints = null;
        int maxPoints = Integer.MIN_VALUE;

        for (Map.Entry<Player, Integer> entry : points.entrySet()) {
            if (entry.getValue() > maxPoints) {
                maxPoints = entry.getValue();
                playerWithMaxPoints = entry.getKey();
            }
        }
        return playerWithMaxPoints;
    }
}
