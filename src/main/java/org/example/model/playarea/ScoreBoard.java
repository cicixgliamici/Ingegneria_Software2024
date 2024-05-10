
package org.example.model.playarea;
import org.example.enumeration.Color;
import java.util.HashMap;
import java.util.Map;

import org.example.controller.*;


public class ScoreBoard {
    private final HashMap<Player, Integer> points;
    private final HashMap<Color, Player> tokens; // Associate a Token to a specific color
    public ScoreBoard(){
        tokens = new HashMap<>();
        points = new HashMap<>();
    }

    /**
     * Adds a new token to the scoreboard, throwing an exception
     * when the color is already used and when there is already
     * the maximum amount of players
     */
    public void addToken(Color color, Player player) throws IllegalArgumentException {
        if (tokens.containsKey(color)) {
            throw new IllegalArgumentException("Select another color");
        }

        if (tokens.size() >= 4) {
            throw new IllegalArgumentException("max 4 players");
        }

        tokens.put(color, player);
    }

    public int getPlayerPoint(Player p){
        return points.get(p);
    }
    public void updatePlayerPoint(Player p, int point){
        points.put(p,point);
    }

    public HashMap<Player, Integer> getPoints() {
        return points;
    }

    public HashMap<Color, Player> getTokens() {
        return tokens;
    }

    /**
     * Finds the winner of the game by calculating
     * the player with the most points
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