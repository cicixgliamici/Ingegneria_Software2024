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
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        socketOut.println("tryConnection");
        socketOut.flush();
        Scanner stdin = new Scanner(System.in);
        // Thread per la ricezione dei messaggi dal server
        Thread serverListener = new Thread(() -> {
            try {
                while (true) {
                    String socketLine = socketIn.nextLine();
                    System.out.println(socketLine);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Server closed the connection");
            }
        });
        serverListener.start();
        // Invio dei comandi predefiniti
        try {
            // Ciclo per l'invio di comandi aggiuntivi dall'utente
            while (true) {
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
            serverListener.interrupt(); // Interrompe il thread del listener quando il client viene chiuso
        }
    }
}
