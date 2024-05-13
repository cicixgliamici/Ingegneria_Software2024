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
            socket = new Socket(ip, port);
            System.out.println("Attempting to connect to " + ip + ":" + port);
            try (Scanner socketIn = new Scanner(socket.getInputStream());
                 PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                 Scanner stdin = new Scanner(System.in)) {
                Thread serverListener = new Thread(() -> handleServerMessages(socketIn));
                Thread userInputThread = new Thread(() -> handleUserInput(stdin, socketOut));
                serverListener.start();
                userInputThread.start();
                serverListener.join();
                userInputThread.join();
            }
        } catch (Exception e) {
            System.out.println("Error connecting or communicating: " + e.getMessage());
        } finally {
            closeSocket(socket);
        }
    }

    private void handleServerMessages(Scanner socketIn) {
        while (socketIn.hasNextLine()) {
            String line = socketIn.nextLine();
            if (line.contains("Match started")) {
                System.out.println(line.substring(8)); // Process start message
                synchronized (this) {
                    gameStarted = true;
                    this.view = new ViewTUI(); // Safely assign within synchronized block
                }
            } else if (gameStarted) {
                synchronized (this) {
                    if (this.view != null) {
                        this.view.Interpreter(line.substring(8));
                    }
                }
            } else {
                System.out.println(line.substring(8)); // Print other messages
            }
        }
    }

    private void handleUserInput(Scanner stdin, PrintWriter socketOut) {
        while (stdin.hasNextLine()) {
            String input = stdin.nextLine();
            socketOut.println(input);
        }
    }

    private void closeSocket(Socket socket) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}



