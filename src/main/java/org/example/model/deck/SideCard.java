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

    public List<Corner> getFront() {
        return front;
    }

    public List<Corner> getBack() {
        return back;
    }
}

