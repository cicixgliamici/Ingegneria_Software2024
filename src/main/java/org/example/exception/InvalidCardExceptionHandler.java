package org.example.exception;

import org.example.server.Server;

/**
 * Handles InvalidCardException by notifying the server and user about the invalid card play.
 */
public class InvalidCardExceptionHandler implements ExceptionHandler {
    /**
     * Handles the given InvalidCardException.
     * 
     * @param exception The exception to be handled.
     * @param username The username of the user who encountered the exception.
     * @param server The server instance to interact with.
     */
    public void handle(Exception exception, String username, Server server) {
        InvalidCardException ice = (InvalidCardException) exception;
        System.err.println("InvalidCardExceptionHandler: " + ice.getMessage());
        server.onModelSpecific(username, "unplayable:" + ice.getCardId() + "," + ice.getX() + "," + ice.getY());
    }
}
