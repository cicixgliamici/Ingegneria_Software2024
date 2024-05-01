package org.example.server;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/**
 * The main class of the server. It simply creates a new server class and runs it.
 *
 */


public class PortSelection {
    public static void main( String[] args )
    {
        System.out.println("insert port number or digit one casual letter for default (50000)");
        Scanner in = new Scanner(System.in);
        int port;
        do{
            String s = in.nextLine();
            try {
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                port = 50000;
            }

            if(port <= 1023 || port >= 65535)
                System.out.println("Write a valid port number from 1024 to 65535");

        } while (port <= 1023 || port >= 65535);
        in.close();
        Server server;
        try {
            server = new Server(port);
            server.startServer();
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}