package org.example.model.deck;

public class MatrixCell {
    private Card CardCell;
    int AvailabilityCorner;

    public MatrixCell(Card cardCell, int availabilityCorner) {
        this.CardCell = cardCell;
        this.AvailabilityCorner = availabilityCorner;
    }

    public void setCardCell(Card cardCell){
        this.CardCell = cardCell;
    }

    public void setAvailabilityCorner(int availabilityCorner){
        this.AvailabilityCorner = availabilityCorner;
    }

    public Card getCardCell(){
        return this.CardCell;
    }

    public int getAvailabilityCorner() {
        return AvailabilityCorner;
    }
}

