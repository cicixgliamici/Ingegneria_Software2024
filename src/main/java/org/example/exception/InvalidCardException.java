package org.example.exception;

/**
 * Custom exception class for handling invalid card exceptions.
 */
public class InvalidCardException extends Exception {
    private final int cardId;
    private final int x;
    private final int y;

    /**
     * Constructor for InvalidCardException.
     * 
     * @param message The error message for the exception.
     * @param cardId The ID of the invalid card.
     * @param x The x-coordinate where the card was attempted to be played.
     * @param y The y-coordinate where the card was attempted to be played.
     */
    public InvalidCardException(String message, int cardId, int x, int y) {
        super(message);
        this.cardId = cardId;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the ID of the invalid card.
     * 
     * @return The ID of the invalid card.
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Gets the x-coordinate where the card was attempted to be played.
     * 
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate where the card was attempted to be played.
     * 
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }
}
