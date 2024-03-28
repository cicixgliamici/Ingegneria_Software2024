package org.example.model.deck;

import org.example.model.deck.enumeration.Position;

public class MatrixCell {
    private Card CardCell;
    private Position AvailabilityCorner;

    public MatrixCell(Card cardCell, Position availabilityCorner) {
        this.CardCell = cardCell;
        this.AvailabilityCorner = availabilityCorner;
    }

    public void setCardCell(Card cardCell){
        this.CardCell = cardCell;
    }

    public void setAvailabilityCorner(Position availabilityCorner){
        this.AvailabilityCorner = availabilityCorner;
    }

    public Card getCardCell(){
        return this.CardCell;
    }

    public Position getAvailabilityCorner() {
        return AvailabilityCorner;
    }
}

