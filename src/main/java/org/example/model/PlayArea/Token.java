package org.example.model.PlayArea;
import org.example.enumeration.Color;
import  org.example.model.deck.*;
import org.example.enumeration.*;
import org.example.controller.Player;

/** Used to associate a player with a specific color and their points.
 *  If the color is black, the player is the first.
 */
public class Token  {
    private boolean isFirst;
    private Color color;
    private int points;
    private Player player;
    public Token(Color color, Player player){
        this.isFirst = false;
        this.color = color;
        this.points = 0;
        this.player = player;
    }
    public boolean isFirst(){
        return isFirst;
    }
    public void addPoints(Card card){
        points = points + card.getPoints();
    }

    /** Getter and Setter zone
     */
    public int getPoints(){
        return points;
    }
    public Color getColor() {
        return color;
    }
    public Player getPlayer() {
        return player;
    }
    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }
}
