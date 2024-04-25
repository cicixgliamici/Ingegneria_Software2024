package org.example.server;

import java.io.IOException;

public class ExecutorServer {
    public static void main(String[] args){
        System.out.println("Default PORT: 4780"); //la scelta della porta deve essere di default oppure a scelta?
        Server server;
        try{
            server = new Server(4780);
            //todo implementare il run del server
        }   catch(IOException error){
            System.out.println("ERROR! Impossible to start Server: " + error.getMessage());
        }

    }
}
