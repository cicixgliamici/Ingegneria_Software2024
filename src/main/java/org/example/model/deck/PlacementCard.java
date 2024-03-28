package org.example.model.deck;

import org.example.model.deck.enumeration.*;


public class PlacementCard {
    private boolean CoverHide;
    private CardRes CardProperties;

    public PlacementCard(){
        this.CardProperties = null;
        this.CoverHide = true;
    }

    //inserire un metodo che quanto posizioni una carta ti dice i punti finali di quella carta
    //una volta posiziona la carta avrà quei punti per sempre quindi calcoliamoli subito

    public void setCoverHide(boolean bool){
        this.CoverHide = bool;
    }

    public void setCardProperties(Card card){
        this.CardProperties = card.getCardRes();
    }

    public CardRes getCardProperties(){
        return this.CardProperties;
    }
}

