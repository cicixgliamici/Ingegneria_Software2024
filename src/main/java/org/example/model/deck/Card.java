package org.example.model.deck;

import org.example.model.deck.enumeration.*;

/**
 * Class Card to abstract the four cards type of the game
 */

public class Card {
    private Type type;        // Type of the card
    private CardRes cardres;    // Permanent resource

    /**
     *  Array of requirment for Gold Cards, the order of resources
     *  is the same of the enum CardRes
     */
    private CardRes[] requireGold;
    private int points;           // Point given
    // REQUISITI CARTA OBIETTIVO MANCANTI
    private CardPosition cardposition;   // Position of the card
    private SideCard side;  // Reference to the object SideCard

    public void setRequireGold(CardRes[] jsonArray) {
        this.requireGold = jsonArray;

    }


    public Card(Type type, CardRes cardres, CardRes[] requireGold, int points, CardPosition cardposition, SideCard side) {
        this.type = type;
        this.cardres = cardres;
        this.requireGold = requireGold;
        this.points = points;
        this.cardposition = cardposition;
        this.side = side;
    }

    public CardRes getCardRes() {
        return this.cardres;
    } //grazie jj

}

