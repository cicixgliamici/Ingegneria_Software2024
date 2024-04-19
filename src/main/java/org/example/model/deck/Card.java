package org.example.model.deck;

import org.example.enumeration.*;
import org.example.enumeration.*;

import java.util.List;

/**
 * Class Card to abstract the four card types of the game
 */

public class Card {
    private Type type;        // Type of the card (Res, Gold, Obj, Srt)
    private CardRes cardres;    // Permanent resource
                                //per le carte obbiettivo puo servire per la grafica, quelle con null sono grigie
    /**
     *  Array of requirements for Gold Cards, the order of the resources
     *  is the same as the enum CardRes
     */
    private CardRes[] requireGold;    //array of requirements for the placement of the gold cards
    private Integer points;           // Point given
    private GoldenPoint goldenPoint;    //for gold cards: point requirements
                                        //for starter cards: permanent resources
    private ObjectivePoints objectivePoints;
    private CardPosition cardposition;   // Position of the card
    private SideCard side;  // Reference to the object SideCard
    private int CoveredCornerByCard;
    public Card(Type type, CardRes cardres, CardRes[] requireGold, Integer points, GoldenPoint goldenPoint, ObjectivePoints objectivePoints, CardPosition cardposition, SideCard side) {
        this.type = type;
        this.cardres = cardres;
        this.requireGold = requireGold;
        this.points = points;
        this.goldenPoint= goldenPoint;
        this.objectivePoints=objectivePoints;
        this.cardposition = cardposition;
        this.side = side;
    }

    public Card(){
    }

    /**
     * Prints the card showing all it's properties, TUI Method
     */
    public void print(){
        //if (this.getType()!= null){
        //    System.out.println("Type: " + this.getType());
        //}
        //if(this.getCardRes()!= null){
        //    System.out.println("Card Resource: " + this.getCardRes());
        //}
        if(this.getRequireGold()!= null){
            //System.out.print("Gold Requirement: ");
            CardRes[] requireGold = this.getRequireGold();
            if(requireGold!= null) {
                for (int i = 0; i < requireGold.length; i++) {
                    //System.out.print(requireGold[i]);
                    if (i < requireGold.length - 1) {
                        //System.out.print(", ");
                    }
                }
            }
            //System.out.println();
        }

        if(this.getPoints() != null){
            //System.out.println("Points: " + this.getPoints());
        }

        if (this.getGoldenPoint()!= null) {
            //System.out.println("GoldenPoint: " + this.getGoldenPoint());
        }
        if(this.getCardPosition()!= null){
            //System.out.println("Card Position: " + this.getCardPosition());

        }
        System.out.println("Side: " + this.getSide().getSide());
        if(this.getSide().getFrontCorners()!= null){
            //System.out.println("Front Corners:");
            List<Corner> frontCorners = this.getSide().getFrontCorners();
            for (Corner corner : frontCorners) {
                //System.out.println("Position: " + corner.getPosition() + ", Corner Properties: " + corner.getPropertiesCorner());
            }
        }
        if(this.getSide().getBackCorners()!=null) {
            //System.out.println("Back Corners:");
            List<Corner> backCorners = this.getSide().getBackCorners();
            for (Corner corner : backCorners) {
                //System.out.println("Position: " + corner.getPosition() + ", Corner Properties: " + corner.getPropertiesCorner());
            }
        }
        //System.out.println();
    }

    public PropertiesCorner getFRONTPropCorn(int pos){
        //gets the card properties

        return this.getSide().getFrontCorners().get(pos).getPropertiesCorner();
    }

    public boolean TOPLCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(0).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
        else {
            return this.getSide().getBackCorners().get(0).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }
    public boolean TOPRCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(1).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
        else {
            return this.getSide().getBackCorners().get(1).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }
    public boolean BOTRCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(2).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
        else {
            return this.getSide().getBackCorners().get(2).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }
    public boolean BOTLCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(3).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
        else {
            return this.getSide().getBackCorners().get(3).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(type).append("\n");
        // Print cardres if not null
        if (cardres != null) {
            sb.append("Card Resources: ").append(cardres).append("\n");
        }
        if(objectivePoints!= null){
            sb.append("Objective: ").append(objectivePoints).append("\n");
        }
        // Print properties of all corners
        if (side != null && side.getFrontCorners() != null && side.getBackCorners() != null) {
            sb.append("Front Corners:\n");
            for (Corner corner : side.getFrontCorners()) {
                sb.append(corner.toString()).append("\n");
            }
            sb.append("Back Corners:\n");
            for (Corner corner : side.getBackCorners()) {
                sb.append(corner.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    public void toStringObjective(Card card){

    }


    /** Getter and Setter zone
     */
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
    public void setType(Type type) {
        this.type = type;
    }

    public void setCardRes(CardRes cardres) {
        this.cardres = cardres;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setGoldenPoint(GoldenPoint goldenPoint) {
        this.goldenPoint = goldenPoint;
    }

    public void setObjectivePoints(ObjectivePoints objectivePoints) {
        this.objectivePoints = objectivePoints;
    }

    public void setCardPosition(CardPosition cardposition) {
        this.cardposition = cardposition;
    }

    public void setSide(SideCard side) {
        this.side = side;
    }
    public void setSide(int x) {
        if(x==1){
            this.side.setSide(Side.FRONT);
        }
        if(x==2){
            this.side.setSide(Side.BACK);
        }
    }
    public void setRequireGold(CardRes[] jsonArray) {
        this.requireGold = jsonArray;

    }

    public void setCardposition(CardPosition cardposition) {
        this.cardposition = cardposition;
    }

    public void setCoveredCornerByCard(int coveredCornerByCard) {
        this.CoveredCornerByCard = coveredCornerByCard;
    }


    public ObjectivePoints getObjectivePoints() {
        return objectivePoints;
    }

    public CardPosition getCardposition() {
        return cardposition;
    }

    public int getCoveredCornerByCard() {
        return this.CoveredCornerByCard;
    }

}

