
package org.example.model.playarea;
import org.example.enumeration.Color;
import java.util.HashMap;
import java.util.Map;

import org.example.controller.*;


public class ScoreBoard {
    private final HashMap<Player, Integer> Points;
    private final HashMap<Color, Player> tokens; // Associate a Token to a specific color
    public ScoreBoard(){
        tokens = new HashMap<>();
        Points = new HashMap<>();
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
        return Points.get(p);
    }
    public void updatePlayerPoint(Player p, int point){
        Points.put(p,point);
    }

    public HashMap<Player, Integer> getPoints() {
        return Points;
    }

    public HashMap<Color, Player> getTokens() {
        return tokens;
    }

    public Player Winner(){
        Player playerWithMaxPoints = null;
        int maxPoints = Integer.MIN_VALUE;

        for (Map.Entry<Player, Integer> entry : Points.entrySet()) {
            if (entry.getValue() > maxPoints) {
                maxPoints = entry.getValue();
                playerWithMaxPoints = entry.getKey();
            }
        }
        return playerWithMaxPoints;
    }

}