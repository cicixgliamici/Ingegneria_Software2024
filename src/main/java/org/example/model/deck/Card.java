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
    private Integer points;           // Point given
    // REQUISITI CARTA OBIETTIVO MANCANTI
    private GoldenPoint goldenPoint;
    private CardPosition cardposition;   // Position of the card

    private SideCard side;  // Reference to the object SideCard

    public void setRequireGold(CardRes[] jsonArray) {
        this.requireGold = jsonArray;

    }


    public Card(Type type, CardRes cardres, CardRes[] requireGold,  Integer points, GoldenPoint goldenPoint, CardPosition cardposition, SideCard side) {
        this.type = type;
        this.cardres = cardres;
        this.requireGold = requireGold;
        this.points = points;
        this.goldenPoint= goldenPoint;
        this.cardposition = cardposition;
        this.side = side;
    }

    public PropertiesCorner CastCardRes(){
        if(this.cardres == CardRes.PLANT){
            return PropertiesCorner.PLANT;
        }
        else if(this.cardres == CardRes.ANIMAL){
            return PropertiesCorner.ANIMAL;
        }
        else if(this.cardres == CardRes.FUNGI){
            return PropertiesCorner.FUNGI;
        }
        else {
            return PropertiesCorner.INSECT;
        }
    }

    public Type getType() {
        return type;
    }

    public CardRes getCardRes() {
        return cardres;
    }

    public CardRes[] getRequireGold() {
        return requireGold;
    }

    public Integer getPoints() {
        return points;
    }

    public GoldenPoint getGoldenPoint() {
        return goldenPoint;
    }

    public CardPosition getCardPosition() {
        return cardposition;
    }

    public SideCard getSide() {
        return side;
    }

}

