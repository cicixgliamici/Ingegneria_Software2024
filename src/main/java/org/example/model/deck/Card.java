package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.List;

/**
 * Class Card to abstract the four cards type of the game
 */

public class Card {
    private Type type;        // Type of the card (Res, Gold, Obj, Srt)
    private CardRes cardres;    // Permanent resource
                                //per le carte obbiettivo puo servire per la grafica, quelle con null sono grigie

    /**
     *  Array of requirment for Gold Cards, the order of resources
     *  is the same of the enum CardRes
     */
    private CardRes[] requireGold;    //array of requirement for place the goldcard

    private Integer points;           // Point given

    private GoldenPoint goldenPoint;    //x carte oro: valore che indica quale attributo da i punti
                                        //x carte iniziali: risorse permanenti

    private ObjectivePoints[] objectivePoints;

    private CardPosition cardposition;   // Position of the card

    private SideCard side;  // Reference to the object SideCard

    public void setRequireGold(CardRes[] jsonArray) {
        this.requireGold = jsonArray;

    }


    public Card(Type type, CardRes cardres, CardRes[] requireGold, Integer points, GoldenPoint goldenPoint, ObjectivePoints[] objectivePoints, CardPosition cardposition, SideCard side) {
        this.type = type;
        this.cardres = cardres;
        this.requireGold = requireGold;
        this.points = points;
        this.goldenPoint= goldenPoint;
        this.objectivePoints=objectivePoints;
        this.cardposition = cardposition;
        this.side = side;
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

    public void print(){
        if (this.getType()!= null){
            System.out.println("Type: " + this.getType());
        }
        if(this.getCardRes()!= null){
            System.out.println("Card Resource: " + this.getCardRes());
        }
        if(this.getRequireGold()!= null){
            System.out.print("Required Gold: ");
            CardRes[] requireGold = this.getRequireGold();
            if(requireGold!= null) {
                for (int i = 0; i < requireGold.length; i++) {
                    System.out.print(requireGold[i]);
                    if (i < requireGold.length - 1) {
                        System.out.print(", ");
                    }
                }
            }
            System.out.println();
        }

        if(this.getPoints() != null){
            System.out.println("Points: " + this.getPoints());
        }

        if (this.getGoldenPoint()!= null) {
            System.out.println("GoldenPoint: " + this.getGoldenPoint());
        }
        if(this.getCardPosition()!= null){
            System.out.println("Card Position: " + this.getCardPosition());

        }
        System.out.println("Side: " + this.getSide().getSide());
        if(this.getSide().getFrontCorners()!= null){
            System.out.println("Front Corners:");
            List<Corner> frontCorners = this.getSide().getFrontCorners();
            for (Corner corner : frontCorners) {
                System.out.println("Position: " + corner.getPosition() + ", PropertiesCorner: " + corner.getPropertiesCorner());
            }
        }
        if(this.getSide().getBackCorners()!=null) {
            System.out.println("Back Corners:");
            List<Corner> backCorners = this.getSide().getBackCorners();
            for (Corner corner : backCorners) {
                System.out.println("Position: " + corner.getPosition() + ", PropertiesCorner: " + corner.getPropertiesCorner());
            }
        }
        System.out.println();
    }

    public PropertiesCorner getPropCorn (int pos){
        return this.getSide().getFrontCorners().get(pos).getPropertiesCorner();
    }
}

