package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.List;

public class SideCard {
    private Side side;
    private List<Corner> front;
    private List<Corner> back;


    public Side getSide() {
        return side;
    }

}
