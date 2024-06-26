package org.example.model.deck;

import java.util.ArrayList;
import java.util.List;
import org.example.enumeration.Side;

/**
 * Class for handling card sides.
 */
public class SideCard {
    private Side side;
    private List<Corner> front;
    private List<Corner> back;

    /**
     * Constructs a SideCard with the specified side, front corners, and back corners.
     *
     * @param side the side of the card (FRONT or BACK)
     * @param front the list of front corners
     * @param back the list of back corners
     */
    public SideCard(Side side, List<Corner> front, List<Corner> back) {
        this.side = side;
        this.front = front;
        this.back = back;
    }

    /**
     * Constructs a default SideCard with default values.
     */
    public SideCard() {
        this.side = Side.FRONT; // Default side
        this.front = new ArrayList<>(); // Empty list for front corners
        this.back = new ArrayList<>(); // Empty list for back corners
    }

    /**
     * Getter for the side of the card.
     *
     * @return the side of the card
     */
    public Side getSide() {
        return side;
    }

    /**
     * Getter for the front corners of the card.
     *
     * @return the list of front corners
     */
    public List<Corner> getFrontCorners() {
        return front;
    }

    /**
     * Getter for the back corners of the card.
     *
     * @return the list of back corners
     */
    public List<Corner> getBackCorners() {
        return back;
    }

    /**
     * Returns the corners of the chosen side of the card.
     *
     * @return the list of corners for the chosen side
     */
    public List<Corner> getChosenList() {
        if (this.side == Side.BACK) {
            return getBackCorners();
        } else {
            return getFrontCorners();
        }
    }

    /**
     * Setter for the side of the card.
     *
     * @param side the side of the card
     */
    public void setSide(Side side) {
        this.side = side;
    }

    /**
     * Setter for the front corners of the card.
     *
     * @param front the list of front corners
     */
    public void setFront(List<Corner> front) {
        this.front = front;
    }

    /**
     * Setter for the back corners of the card.
     *
     * @param back the list of back corners
     */
    public void setBack(List<Corner> back) {
        this.back = back;
    }
}
