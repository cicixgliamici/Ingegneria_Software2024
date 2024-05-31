package org.example.exception;

import org.example.server.Server;

public class PlaceholderNotValidHandler implements ExceptionHandler {
    @Override
    public void handle(Exception exception, String username, Server server) {
        if (exception instanceof PlaceholderNotValid) {
            PlaceholderNotValid pnv = (PlaceholderNotValid) exception;
            System.err.println("PlaceholderNotValid: " + pnv.getMessage());
            // Invia una notifica al client specifico con dettagli utili
            server.onModelSpecific(username, "placeholder:" + pnv.getId() + "," + pnv.getX() + "," + pnv.getY());
        } else {
            // Gestisci il caso in cui l'eccezione non sia di tipo PlaceholderNotValid
            System.err.println("Unhandled exception type: " + exception.getClass().getName());
        }
    }
}