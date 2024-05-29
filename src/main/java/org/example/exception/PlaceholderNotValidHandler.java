package org.example.exception;

import org.example.server.Server;


public class PlaceholderNotValidHandler implements ExceptionHandler {
    @Override
    public void handle(Exception exception, String username, Server server) {
        PlaceholderNotValid pnv = (PlaceholderNotValid) exception;
        System.err.println("PlaceholderNotValid: " + pnv.getMessage());
        server.onModelSpecific(username, "invalid_placeholder:" + pnv.getX() + "," + pnv.getY());
    }
}
