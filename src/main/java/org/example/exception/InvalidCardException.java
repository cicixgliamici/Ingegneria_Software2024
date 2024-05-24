package org.example.exception;

import org.example.server.Server;

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
    public void handle(Throwable exception, String username, Server server) {
        InvalidCardException ice = (InvalidCardException) exception;
        System.err.println("InvalidCardException: " + ice.getMessage());
        server.onModelSpecific(username, "unplayable:" + ice.getCardId() + "," + ice.getX() + "," + ice.getY());
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