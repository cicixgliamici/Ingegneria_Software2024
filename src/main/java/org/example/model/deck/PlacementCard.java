package org.example.model.deck;

import org.example.model.deck.enumeration.*;


public class PlacementCard {
    private boolean CoverHide;
    private CardRes CardProperties;

    public PlacementCard(){
        this.CardProperties = null;
        this.CoverHide = true;
    }

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

