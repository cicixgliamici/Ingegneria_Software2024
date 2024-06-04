package org.example.exception;

import org.example.server.Server;

/**
 * Interface for handling exceptions.
 */
public interface ExceptionHandler {
    /**
     * Handles the given exception.
     * 
     * @param exception The exception to be handled.
     * @param username The username of the user who encountered the exception.
     * @param server The server instance to interact with.
     */
    void handle(Exception exception, String username, Server server);
}
