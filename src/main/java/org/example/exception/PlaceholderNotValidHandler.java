package org.example.exception;

import org.example.server.Server;

/**
 * Handles PlaceholderNotValid exceptions by notifying the server and user about the invalid placeholder.
 */
public class PlaceholderNotValidHandler implements ExceptionHandler {
    /**
     * Handles the given PlaceholderNotValid exception.
     * 
     * @param exception The exception to be handled.
     * @param username The username of the user who encountered the exception.
     * @param server The server instance to interact with.
     */
    public void handle(Exception exception, String username, Server server) {
        PlaceholderNotValid pnv = (PlaceholderNotValid) exception;
        System.err.println("PlaceholderNotValidHandler: " + pnv.getMessage());
        server.onModelSpecific(username, "placeholder:" + pnv.getCardId() + "," + pnv.getX() + "," + pnv.getY());
    }
}
