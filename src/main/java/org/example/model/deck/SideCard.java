package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.List;
import org.example.model.deck.enumeration.Side;
import java.util.List;

public class SideCard {
    private Side side;
    private List<Corner> front;
    private List<Corner> back;

    public SideCard(Side side, List<Corner> front, List<Corner> back) {
        this.side = side;
        this.front = front;
        this.back = back;
    }

    public Side getSide() {
        return side;
    }

    public List<Corner> getFrontCorners() {
        return front;
    }

    public List<Corner> getBackCorners() {
        return back;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public void setFront(List<Corner> front) {
        this.front = front;
    }

    public void setBack(List<Corner> back) {
        this.back = back;
    }

    public List<Corner> getChoosenList(){
        if(this.side == Side.BACK){
            return getBackCorners();
        }
        else {
            return getFrontCorners();
        }
    }
}

