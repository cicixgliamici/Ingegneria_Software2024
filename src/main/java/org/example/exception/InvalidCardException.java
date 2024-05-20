package org.example.exception;

public class InvalidCardException extends Exception {
    private final int cardId;

    public InvalidCardException(String message, int cardId) {
        super(message);
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}
