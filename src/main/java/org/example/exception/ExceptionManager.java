package org.example.exception;

import java.util.HashMap;
import java.util.Map;
import org.example.server.Server;

/**
 * Manages exception handling by mapping exceptions to their corresponding handlers.
 */
public class ExceptionManager {
    private Map<Class<? extends Exception>, ExceptionHandler> handlers;

    /**
     * Constructor for ExceptionManager.
     * Initializes the handlers map and registers the handlers.
     */
    public ExceptionManager() {
        handlers = new HashMap<>();
        registerHandlers();
    }

    /**
     * Registers the exception handlers.
     * Maps specific exceptions to their corresponding handlers.
     */
    private void registerHandlers() {
        handlers.put(InvalidCardException.class, new InvalidCardExceptionHandler());
        handlers.put(PlaceholderNotValid.class, new PlaceholderNotValidHandler());
    }

    /**
     * Handles the given exception.
     * 
     * @param exception The exception to be handled.
     * @param username The username of the user who encountered the exception.
     * @param server The server instance to interact with.
     */
    public void handleException(Exception exception, String username, Server server) {
        System.out.println("Handling exception: " + exception.getClass().getSimpleName());
        ExceptionHandler handler = handlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(exception, username, server);
        } else {
            System.err.println("No handler registered for: " + exception.getClass().getName());
            server.onModelSpecific(username, "error:UnhandledException," + exception.getMessage());
        }
    }
}
