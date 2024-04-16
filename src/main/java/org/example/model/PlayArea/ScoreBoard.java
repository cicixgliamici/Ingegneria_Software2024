package org.example.model.PlayArea;
import org.example.enumeration.Color;
import org.example.enumeration.*;
import java.util.HashMap;
import  org.example.model.deck.*;
import org.example.controller.*;


public class ScoreBoard {
    private final HashMap<Color, Token> tokens; // Associate a Token to a specific color
    public ScoreBoard(){
        tokens = new HashMap<>();
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

        Token token = new Token(color, player);
        tokens.put(color, token);

        if (tokens.size() == 1) {
            token.setFirst(true);       //if first player added to the game, first player to start
        }
    }

    /**
     * Adds points to each player based on the color of their token
     */
    public void addColorPoints(Color color, Card card) {
        Token token = tokens.get(color);
        if (token != null) {
            token.addPoints(card);
        } else {
            throw new IllegalArgumentException("Color isn't present on the scoreboard");
        }
    }

    /**
     * Gets the first player of the match
     */
    public Color getFirstPlayer() {
        for (Token token : tokens.values()) {
            if (token.isFirst()) {
                return token.getColor();
            }
        }
        return null;
    }

    public Token getToken(Color color) throws IllegalArgumentException{
        if(tokens.containsKey(color)){
            return tokens.get(color);
        }
        else {
            throw new IllegalArgumentException("Token doesn't exist!");
        }
    }
    public Color getTokenColor(Player player) {
        for (Token token : tokens.values()) {
            if (token.getPlayer().equals(player)) {
                return token.getColor();
            }
        }
        return null;
    }


}
