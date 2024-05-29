package org.example.model.deck;

import java.util.List;
import org.example.enumeration.Side;

/**
 * Class for handling card sides
 */
public class SideCard {
    private Side side;
    private List<Corner> front;
    private List<Corner> back;

    public SideCard(Side side, List<Corner> front, List<Corner> back) {
        this.side = side;
        this.front = front;
        this.back = back;
    }

    /** Getter and Setter zone
     */
    public Side getSide() {
        return side;
    }
    public List<Corner> getFrontCorners() {
        return front;
    }

    public List<Corner> getBackCorners() {
        return back;
    }
    public List<Corner> getChosenList(){   //returns the corners of the chosen side
        if(this.side == Side.BACK){
            return getBackCorners();
        }
        else {
            return getFrontCorners();
        }
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
}

