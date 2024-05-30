package org.example.exception;

import org.example.server.Server;


public class InvalidCardExceptionHandler implements ExceptionHandler {
    //eccezione lanciata quando selezioni una carta oro senza avere le risorse
    public void handle(Exception exception, String username, Server server) {
        InvalidCardException ice = (InvalidCardException) exception;
        System.err.println("InvalidCardExceptionHandler: " + ice.getMessage());
        server.onModelSpecific(username, "unplayable:" + ice.getCardId() + "," + ice.getX() + "," + ice.getY());
    }
}

