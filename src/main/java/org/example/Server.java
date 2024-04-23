package org.example;

import org.example.model.deck.Card;

public class Server {
    private boolean chooseMode;
    private int numPlayers;
    
    public Card getCard(){
        //todo metodo che chiede al client una carta dalla mano
        return new Card();
    }
    public int getNumPlayers() {
        return numPlayers;
    }
    public void setChooseMode(boolean chooseMode) {
        this.chooseMode = chooseMode;
    }
    public boolean isChooseMode() {
        return chooseMode;
    }
}
