package org.example.model.deck;

class Card {
    private Side frontSide;
    private Side backSide;
    private Type typeCard;

    public Card(Side frontSide, Side backSide, Type typeCard) {
        this.frontSide = frontSide;
        this.backSide = backSide;
        this.typeCard = typeCard;
    }

    public Side getFrontSide() {
        return frontSide;
    }

    public Side getBackSide() {
        return backSide;
    }
}
