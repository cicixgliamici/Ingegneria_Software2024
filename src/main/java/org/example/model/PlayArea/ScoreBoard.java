package org.example.model.PlayArea;
import org.example.model.deck.enumeration.*;
import java.util.HashMap;
import  org.example.model.deck.*;


public class ScoreBoard {
    private HashMap<Color, Token> tokens; // Associate a Token to a specific color
    private HashMap<Token, Integer> personalScoarboard;
    public ScoreBoard(){
        tokens = new HashMap<>();
        personalScoarboard = new HashMap<>();
    }
    public void addToken(Color color) throws IllegalArgumentException {
        if (tokens.containsKey(color)) {
            throw new IllegalArgumentException("Select another color");
        }

        if (tokens.size() >= 4) {
            throw new IllegalArgumentException("max 4 players");
        }

        Token token = new Token(color);
        tokens.put(color, token);
        personalScoarboard.put(token, 0);

        if (tokens.size() == 1) {
            token.setFirst(true);       //if first player added to the game, first player to start
        }
    }

    public void addColorPoints(Color color, int points, Card card) {
        Token token = tokens.get(color);
        if (token != null) {
            token.addPoints(card);
        } else {
            throw new IllegalArgumentException("Color isn't present on the scoreboard");
        }
    }
    public Color getFirstPlayer() {
        for (Token token : tokens.values()) {
            if (token.isFirst()) {
                return token.getColor();
            }
        }
        return null;
    }

    public void moveonScoreBoard(int valuePoint, ){

    }

}
