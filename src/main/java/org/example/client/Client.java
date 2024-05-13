package org.example.client;

import org.example.view.View;
import org.example.view.ViewTUI;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private View view;
    private volatile boolean gameStarted = false;  // Use volatile to ensure visibility across threads

    public Client(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }

    public void startClientTUI() {
        try (Socket socket = new Socket(ip, port)) {
            System.out.println("Attempting to connect to " + ip + ":" + port);
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
            Scanner stdin = new Scanner(System.in);

            // Thread to handle server messages
            Thread serverListener = new Thread(() -> handleServerMessages(socketIn));
            serverListener.start();

            // Thread to handle user input
            Thread userInputThread = new Thread(() -> handleUserInput(stdin, socketOut));
            userInputThread.start();

            // Join threads to ensure the main thread waits for them to finish
            serverListener.join();
            userInputThread.join();

        } catch (Exception e) {
            System.out.println("Error connecting or communicating: " + e.getMessage());
        }
    }

    private void handleServerMessages(Scanner socketIn) {
        while (socketIn.hasNextLine()) {
            String line = socketIn.nextLine();
            synchronized (this) {
                if (line.equals("Match started")) {
                    System.out.println(line); // Process start message
                    gameStarted = true;
                    this.view = new ViewTUI(); // Safely assign within synchronized block
                } else if (gameStarted) {
                    if (this.view != null) {
                        this.view.Interpreter(line); // Pass the whole message to the view
                    }
                } else {
                    System.out.println(line); // Print other messages
                }
            }
        }
    }

    private void handleUserInput(Scanner stdin, PrintWriter socketOut) {
        while (stdin.hasNextLine()) {
            String input = stdin.nextLine();
            socketOut.println(input);
        }
    }
}
