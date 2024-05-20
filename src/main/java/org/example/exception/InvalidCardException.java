package org.example.exception;

public class InvalidCardException extends Exception {
    private final int cardId;
    private final int x;
    private final int y;

    public InvalidCardException(String message, int cardId, int x, int y) {
        super(message);
        this.cardId = cardId;
        this.x=x;
        this.y=y;

    }

    public int getCardId() {
        return cardId;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
