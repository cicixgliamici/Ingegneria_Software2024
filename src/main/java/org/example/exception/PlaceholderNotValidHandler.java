package org.example.exception;

import org.example.server.Server;

public class PlaceholderNotValidHandler implements ExceptionHandler {
    public void handle(Exception exception, String username, Server server) {
        PlaceholderNotValid pnv = (PlaceholderNotValid) exception;
        System.err.println("PlaceholderNotValidHandler: " + pnv.getMessage());
        server.onModelSpecific(username, "placeholder:" + pnv.getCardId() + "," + pnv.getX() + "," + pnv.getY());
    }
}