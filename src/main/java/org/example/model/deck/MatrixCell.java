package org.example.model.deck;

import org.example.model.deck.enumeration.Position;
import org.example.model.deck.enumeration.PropertiesCorner;

public class MatrixCell {
    private Card CardCell;
    private Position AvailabilityCorner;

    private SideCard sideCard;

    public MatrixCell(Card cardCell, Position availabilityCorner,   SideCard sideCard) {
        this.CardCell = cardCell;
        this.AvailabilityCorner = availabilityCorner;
        this.sideCard= sideCard;
    }

    public void setCardCell(Card cardCell){
        this.CardCell = cardCell;
    }

    public void setAvailabilityCorner(Position availabilityCorner){
        this.AvailabilityCorner = availabilityCorner;
    }

    public void setSideCard(SideCard sideCard) {
        this.sideCard = sideCard;
    }
    public Card getCardCell()
    {
        return this.CardCell;
    }

    public Position getAvailabilityCorner() {
        return AvailabilityCorner;
    }




}

