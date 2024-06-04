package org.example.exception;

/**
 * Custom exception class for handling invalid placeholder exceptions.
 */
public class PlaceholderNotValid extends Exception {
    private final int cardId;
    private final int x;
    private final int y;

    /**
     * Constructor for PlaceholderNotValid.
     * 
     * @param message The error message for the exception.
     * @param cardId The ID of the card that caused the exception.
     * @param x The x-coordinate of the invalid placeholder.
     * @param y The y-coordinate of the invalid placeholder.
     */
    public PlaceholderNotValid(String message, int cardId, int x, int y) {
        super(message);
        this.cardId = cardId;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the ID of the card that caused the exception.
     * 
     * @return The ID of the card.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Gets the x-coordinate of the invalid placeholder.
     * 
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the invalid placeholder.
     * 
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
}
