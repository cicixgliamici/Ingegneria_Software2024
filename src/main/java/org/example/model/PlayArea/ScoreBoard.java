package org.example.model.PlayArea;
import org.example.model.deck.enumeration.*;
import java.util.HashMap;
import  org.example.model.deck.*;


public class ScoreBoard {
    private final HashMap<Color, Token> tokens; // Associate a Token to a specific color
    public ScoreBoard(){
        tokens = new HashMap<>();
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

        if (tokens.size() == 1) {
            token.setFirst(true);       //if first player added to the game, first player to start
        }
    }

    public void addColorPoints(Color color, Card card) {
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

    public Token getToken(Color color) throws IllegalArgumentException{
        if(tokens.containsKey(color)){
            return tokens.get(color);
        }
        else {
            throw new IllegalArgumentException("Token doesn't exist!");
        }
    }

}
