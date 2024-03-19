package org.example.model.deck;

public class Deck {

    private Type type;
    private int CardNumbers;

     public Deck(Type type) {
        this.type = type;
        switch (type) {
            case RESOURCES:
                this.CardNumbers = 40;
                // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case GOLD:
                this.CardNumbers = 40; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case OBJECT:
                this.CardNumbers = 16; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case STARTER:
                this.CardNumbers = 6; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            default:
                this.CardNumbers = 0; 
        }
    }

    public Type getType() {
        return type;
    }

    public int getCardNumbers() {
        return CardNumbers;
    }
}

