package org.example.model.deck;

public class Deck {
    
    private Type type;
    private int CardNumbers;

     public Deck(Type type) {
        this.type = type;
        switch (type) {
            case RESOURCES:
                this.cardNumbers = 40;
                // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case GOLD:
                this.cardNumbers = 40; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case OBJECT:
                this.cardNumbers = 16; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            case STARTER:
                this.cardNumbers = 6; 
                 // CHIAMARE FUNZIONE GENERA CARTE
                break;
            default:
                this.cardNumbers = 0; 
        }
    }
}

