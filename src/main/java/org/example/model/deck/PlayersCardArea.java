package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.List;

public class PlayersCardArea {
    private Corner[][] PlayedCard;
    private List<CardRes>  PlayerRes;
    private List<PropertiesCorner> PlayerObject;

    public Corner[][] InitializedPlayerArea(Corner[][] PlayedCard){
        //inizializzo la matrice dove dovrò andare a salvare gli angoli
        this.PlayedCard = new Corner[20][20];
        //devo prendere il cuore della matrice per inserire gli angolai della carta iniziale


        return this.PlayedCard;
    }
    public void GetStarted(Card StarterCard, Corner[][] PlayedCard){
        //Corner[]
        return;
    }
    */
}
