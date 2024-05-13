package org.example.client;

import org.example.view.View;
import org.example.view.ViewTUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private View view;
    private boolean gameStarted = false;

    public Client(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }
    public void startClientTUI() {
        Socket socket = null;
        try {
            System.out.println("Attempting to connect to " + ip + ":" + port);
            socket = new Socket(ip, port);
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            Scanner stdin = new Scanner(System.in);
            // Thread per la ricezione dei messaggi dal server
            Thread serverListener = new Thread(() -> {
                try {
                    while (true) {
                        String socketLine = socketIn.nextLine();
                        if (gameStarted) {
                            view.Interpreter(socketLine);  // Use view to interpret messages once game has started
                        } else {
                            System.out.println(socketLine);  // Print directly until the game starts
                            if (socketLine.contains("Match started")) {
                                gameStarted = true;  // Change flag when the game starts
                                view = new ViewTUI();  // Initialize or switch to the game-specific view
                            }
                        }
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
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + ip);
        } catch (IOException e) {
            System.out.println("Connection refused: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public View getView() {
        return view;
    }
}


