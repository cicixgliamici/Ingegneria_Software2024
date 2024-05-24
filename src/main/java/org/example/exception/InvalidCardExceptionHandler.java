package org.example.exception;

import org.example.server.Server;


public class InvalidCardExceptionHandler implements ExceptionHandler {
    public void handle(Exception exception, String username, Server server) {
        InvalidCardException ice = (InvalidCardException) exception;
        server.onModelSpecific(username, "unplayable:" + ice.getCardId() + "," + ice.getX() + "," + ice.getY());
    }
}

