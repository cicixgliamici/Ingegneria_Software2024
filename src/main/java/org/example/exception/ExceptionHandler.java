package org.example.exception;

import org.example.server.Server;

public interface ExceptionHandler {
    void handle(Exception exception, String username, Server server);
}
