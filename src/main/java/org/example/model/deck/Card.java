package org.example.model.deck;

import org.example.enumeration.*;

import java.util.List;

/**
 * Class representing a Card to abstract the four card types of the game.
 */
public class Card {
    private int id;
    private Type type; // Type of the card (Res, Gold, Obj, Srt)
    private CardRes cardres; // Permanent resource

    // Array of requirements for Gold Cards, the order of the resources is the same as the enum CardRes
    private CardRes[] requireGold;

    private Integer points; // Points given by the card
    private GoldenPoint goldenPoint; // For gold cards: point requirements, for starter cards: permanent resources
    private ObjectivePoints objectivePoints; // Objective points for the card
    private CardPosition cardposition; // Position of the card
    private SideCard side; // Reference to the object SideCard
    private int coveredCornerByCard; // Covered corner by the card

    /**
     * Constructs a Card with the specified properties.
     *
     * @param id the card ID
     * @param type the type of the card
     * @param cardres the card resource
     * @param requireGold array of requirements for gold cards
     * @param points points given by the card
     * @param goldenPoint golden points of the card
     * @param objectivePoints objective points of the card
     * @param cardposition the position of the card
     * @param side the side card
     */
    public Card(int id, Type type, CardRes cardres, CardRes[] requireGold, Integer points, GoldenPoint goldenPoint, ObjectivePoints objectivePoints, CardPosition cardposition, SideCard side) {
        this.id = id;
        this.type = type;
        this.cardres = cardres;
        this.requireGold = requireGold;
        this.points = points;
        this.goldenPoint = goldenPoint;
        this.objectivePoints = objectivePoints;
        this.cardposition = cardposition;
        this.side = side;
    }

    /**
     * Default constructor for Card.
     */
    public Card() {
    }

    /**
     * Constructs a Card with the specified SideCard.
     *
     * @param side the side card
     */
    public Card(SideCard side) {
        this.side = side;
    }

    /**
     * Constructs a Card with the specified type and requirements for gold cards.
     *
     * @param type the type of the card
     * @param requireGold array of requirements for gold cards
     */
    public Card(Type type, CardRes[] requireGold) {
        this.type = type;
        this.requireGold = requireGold;
    }

    /**
     * Constructs a Card with the specified type and card resource.
     *
     * @param type the type of the card
     * @param cardres the card resource
     */
    public Card(Type type, CardRes cardres) {
        this.type = type;
        this.cardres = cardres;
    }

    /**
     * Prints the card showing all its properties (TUI Method).
     */
    public void print() {
        if (this.getRequireGold() != null) {
            CardRes[] requireGold = this.getRequireGold();
            if (requireGold != null) {
                for (int i = 0; i < requireGold.length; i++) {
                    // Print each requirement
                    if (i < requireGold.length - 1) {
                        // Print separator
                    }
                }
            }
        }
    }

    /**
     * Gets the front properties corner at the specified position.
     *
     * @param pos the position
     * @return the properties corner at the specified position
     */
    public PropertiesCorner getFRONTPropCorn(int pos) {
        return this.getSide().getFrontCorners().get(pos).getPropertiesCorner();
    }

    /**
     * Checks if the top left corner is hidden.
     *
     * @return true if the top left corner is hidden, false otherwise
     */
    public boolean TOPLCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(0).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        } else {
            return this.getSide().getBackCorners().get(0).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }

    /**
     * Checks if the top right corner is hidden.
     *
     * @return true if the top right corner is hidden, false otherwise
     */
    public boolean TOPRCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(1).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        } else {
            return this.getSide().getBackCorners().get(1).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }

    /**
     * Checks if the bottom right corner is hidden.
     *
     * @return true if the bottom right corner is hidden, false otherwise
     */
    public boolean BOTRCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(2).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        } else {
            return this.getSide().getBackCorners().get(2).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }

    /**
     * Checks if the bottom left corner is hidden.
     *
     * @return true if the bottom left corner is hidden, false otherwise
     */
    public boolean BOTLCornerIsHidden() {
        if (this.getSide().getSide() == Side.FRONT) {
            return this.getSide().getFrontCorners().get(3).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        } else {
            return this.getSide().getBackCorners().get(3).getPropertiesCorner() == PropertiesCorner.HIDDEN;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ").append(type).append("\n");

        if (cardres != null) {
            sb.append("Card Resources: ").append(cardres).append("\n");
        }
        if (objectivePoints != null) {
            sb.append("Objective: ").append(objectivePoints).append("\n");
        }
        if (side != null && side.getSide() != null) {
            sb.append("Side: ").append(side.getSide()).append("\n");
        }

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

    /**
     * Getter for type.
     *
     * @return the type of the card
     */
    public Type getType() {
        return type;
    }

    /**
     * Getter for card resource.
     *
     * @return the card resource
     */
    public CardRes getCardRes() {
        return cardres;
    }

    /**
     * Getter for requirements for gold cards.
     *
     * @return the requirements for gold cards
     */
    public CardRes[] getRequireGold() {
        return requireGold;
    }

    /**
     * Getter for points.
     *
     * @return the points given by the card
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Getter for golden points.
     *
     * @return the golden points of the card
     */
    public GoldenPoint getGoldenPoint() {
        return goldenPoint;
    }

    /**
     * Getter for card position.
     *
     * @return the position of the card
     */
    public CardPosition getCardPosition() {
        return cardposition;
    }

    /**
     * Getter for side card.
     *
     * @return the side card
     */
    public SideCard getSide() {
        return side;
    }

    /**
     * Getter for objective points.
     *
     * @return the objective points of the card
     */
    public ObjectivePoints getObjectivePoints() {
        return objectivePoints;
    }

    /**
     * Getter for card ID.
     *
     * @return the card ID
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for type.
     *
     * @param type the type of the card
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Setter for card resource.
     *
     * @param cardres the card resource
     */
    public void setCardRes(CardRes cardres) {
        this.cardres = cardres;
    }

    /**
     * Setter for points.
     *
     * @param points the points given by the card
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Setter for golden points.
     *
     * @param goldenPoint the golden points of the card
     */
    public void setGoldenPoint(GoldenPoint goldenPoint) {
        this.goldenPoint = goldenPoint;
    }

    /**
     * Setter for objective points.
     *
     * @param objectivePoints the objective points of the card
     */
    public void setObjectivePoints(ObjectivePoints objectivePoints) {
        this.objectivePoints = objectivePoints;
    }

    /**
     * Setter for card position.
     *
     * @param cardposition the position of the card
     */
    public void setCardPosition(CardPosition cardposition) {
        this.cardposition = cardposition;
    }

    /**
     * Setter for card ID.
     *
     * @param id the card ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for side card.
     *
     * @param side the side card
     */
    public void setSide(SideCard side) {
        this.side = side;
    }

    /**
     * Sets the side of the card.
     *
     * @param x the side of the card (1 for FRONT, 2 for BACK)
     */
    public void setSide(int x) {
        if (x == 1) {
            this.side.setSide(Side.FRONT);
        }
        if (x == 2) {
            this.side.setSide(Side.BACK);
        }
    }

    /**
     * Setter for requirements for gold cards.
     *
     * @param requireGold the requirements for gold cards
     */
    public void setRequireGold(CardRes[] requireGold) {
        this.requireGold = requireGold;
    }

    /**
     * Setter for the covered corner by the card.
     *
     * @param coveredCornerByCard the covered corner by the card
     */
    public void setCoveredCornerByCard(int coveredCornerByCard) {
        this.coveredCornerByCard = coveredCornerByCard;
    }

    /**
     * Getter for the covered corner by the card.
     *
     * @return the covered corner by the card
     */
    public int getCoveredCornerByCard() {
        return this.coveredCornerByCard;
    }
}
