package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.ArrayList;
import java.util.List;

public class PlayersCardArea {
    private MatrixCell[][] PlayedCard;
    private List<CardRes>  PlayerRes;
    private List<PropertiesCorner> PlayerObject;

    public PlayersCardArea() {
        this.PlayerRes = new ArrayList<CardRes>();
        this.PlayerObject = new ArrayList<PropertiesCorner>();
    }

    public void ModifyPlayedCard(int Line, int Column, int Value, Card LinkCard){
        this.PlayedCard[Line][Column].setAvailabilityCorner(Value);
        this.PlayedCard[Line][Column].setCardCell(LinkCard);
    }

    public void InitializedPlayerArea(int[][] PlayedCard, Card cardStarter){
        ModifyPlayedCard(10,10, 1, cardStarter);
        ModifyPlayedCard(10,11, 1, cardStarter);
        ModifyPlayedCard(11,10, 1, cardStarter);
        ModifyPlayedCard(11,11, 1, cardStarter);
        if (cardStarter.getSide().getSide() == Side.FRONT){
            //inserimento valore ricavato dagli angoli nella lista
        }

        else if(cardStarter.getSide().getSide() == Side.BACK){
            //inserimento valore ricavato dagli angoli nella lista
        }
    }

    public void InsertNewCard(Card TouchedCard, Card NewCard, Corner ChoosenCorner){
        //cerco la carta, individuo l'angolo dove devo inserire la carta nuova giocando con gli indici della matrice
    }

}
