package org.example.model.PlayArea;
import  org.example.model.deck.*;
import  org.example.model.deck.enumeration.*;
public class Token  {
    private boolean isFirst;
    private Color color;
    private int points;
    public Token(Color color){
        this.isFirst = false;
        this.color = color;
        this.points = 0;
    }
    public boolean isFirst(){
        return isFirst;
    }
    public Color getColor(){
        return color;
    }
    public int getPoints(){
        return points;
    }
    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }
    public void addPoints(Card card){
        points = points + card.getPoints();
    }
}
