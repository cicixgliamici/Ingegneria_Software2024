package org.example.model.PlayArea;

import org.example.model.deck.Card;

import java.util.List;

public class PlaceHolder {
    public int x;
    public int y;
    public PlaceHolder(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Placeholder " + x + " : " + y;
    }

    public void setTopL(PlaceHolder topL){}

    public void setTopR(PlaceHolder topR) {
    }

    public void setBotL(PlaceHolder botL) {}

    public void setBotR(PlaceHolder botR) {
    }
    public Card getCard() {
        return null;
    }

    public PlaceHolder getTopL() {
        return null;
    }

    public PlaceHolder getTopR() {
        return null;
    }

    public PlaceHolder getBotL() {
        return null;
    }

    public PlaceHolder getBotR() {
        return null;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void SetCardNode(Card card, List<PlaceHolder> placeHolders, List<PlaceHolder> availableNodes, List<PlaceHolder> allNodes) {
    }
}

