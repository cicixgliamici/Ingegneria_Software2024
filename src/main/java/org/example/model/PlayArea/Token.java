package org.example.model.PlayArea;
import  org.example.model.deck.*;
public class Token  {
    private boolean isFirst;
    private Color color;
    private int points;
    public Token(boolean isFirst, Color color, int points){
        this.isFirst = isFirst;
        this.color = color;
        this.points = points;
    }
    public boolean getisFirst(){
        return isFirst;
    }
    public Color getColor(){
        return color;
    }
    public int getPoints(){
        return points;
    }
    public void AddPoints(Card card){
        points = points + card.getPoints();
    }
}
