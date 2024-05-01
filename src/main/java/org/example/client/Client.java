package org.example.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;


public class Client {
    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void startClient() throws IOException {
        Socket socket = new Socket(ip, port);
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        // Thread per la ricezione dei messaggi dal server
        Thread serverListener = new Thread(() -> {
            try {
                System.out.println("Connection established");
                while (true) {
                    String socketLine = socketIn.nextLine();
                    System.out.println(socketLine);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Server closed the connection");
            }
        });
        serverListener.start();

        // Thread per l'invio delle stringhe inserite dall'utente al server
        Thread userInputThread = new Thread(() -> {
            try {
                while (true) {
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush(); // Assicura che il messaggio venga inviato immediatamente
                }
            } catch (NoSuchElementException e) {
                System.out.println("Connection closed");
            }
        });
        userInputThread.start();

        // Attende che entrambi i thread terminino prima di chiudere le risorse
        try {
            userInputThread.join();
            serverListener.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
