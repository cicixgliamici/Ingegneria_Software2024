package org.example.exception;

import java.util.HashMap;
import java.util.Map;
import org.example.server.Server;

public class ExceptionManager {
    private Map<Class<? extends Exception>, ExceptionHandler> handlers;

    public ExceptionManager() {
        handlers = new HashMap<>();
        registerHandlers();
    }

    private void registerHandlers() {
        handlers.put(InvalidCardException.class, new InvalidCardExceptionHandler());
        handlers.put(PlaceholderNotValid.class, new PlaceholderNotValidHandler());
    }

    public void handleException(Exception exception, String username, Server server) {
        ExceptionHandler handler = handlers.get(exception.getClass());
        if (handler != null) {
            handler.handle(exception, username, server);
        } else {
            server.onModelSpecific(username, "error:UnhandledException," + exception.getMessage());
        }
    }
}