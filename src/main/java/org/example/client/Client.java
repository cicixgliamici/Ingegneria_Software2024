package org.example.client;

import org.example.server.rmi.RMIServerInterface;
import org.example.view.View;
import org.example.view.ViewTUI;
import org.example.server.rmi.RMIClientCallbackInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    private String ip;  // IP address of the server
    private int port;  // Port number for the server
    private View view;  // View interface for interacting with the user
    private volatile boolean gameStarted = false;  // Use volatile to ensure visibility across threads
    private String lastSentMessage = "";  // Last message sent by the client
    private RMIClientCallbackInterface rmiClientCallback;  // RMI callback interface

    private static final List<String> AVAILABLE_COLORS = Arrays.asList("Red", "Blue", "Green", "Yellow");  // List of available colors

    public Client(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }

    public void startClientTUI(int mode) {
        if (mode == 0) {
            startTCPClient();  // Start TCP client if mode is 0
        } else if (mode == 1) {
            startRMIClient();  // Start RMI client if mode is 1
        } else {
            System.out.println("Invalid mode. Please select 0 for TCP or 1 for RMI.");
        }
    }

    /** TCP ZONE */
    private void startTCPClient() {
        try (Socket socket = new Socket(ip, port)) {  // Connect to the server using TCP
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
            handleMessage(line);  // Handle the message received from the server
        }
    }

    private void handleUserInput(Scanner stdin, PrintWriter socketOut) {
        while (stdin.hasNextLine()) {
            String input = stdin.nextLine();
            if (isValidInput(input)) {
                socketOut.println(input);  // Send valid input to the server
                lastSentMessage = input;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private boolean isValidInput(String input) {
        if (lastSentMessage.equals("Enter the maximum number of players (1-4):")) {
            try {
                int numPlayers = Integer.parseInt(input);
                return numPlayers >= 1 && numPlayers <= 4;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (lastSentMessage.contains("Choose a color from the following list:")) {
            return AVAILABLE_COLORS.contains(input);
        }
        return true;
    }

    /** RMI ZONE */
    private void startRMIClient() {
        try {
            Registry registry = LocateRegistry.getRegistry(ip, port + 1);  // Connect to the RMI registry
            RMIClientCallbackInterface rmiClientCallback = new RMIClientCallbackImpl(this);  // Create an RMI client callback
            RMIServerInterface rmiServer = (RMIServerInterface) registry.lookup("RMIServer");  // Look up the RMI server
            Scanner stdin = new Scanner(System.in);

            System.out.println("Enter your username:");
            String username = stdin.nextLine();
            rmiServer.connect(username, rmiClientCallback);  // Connect to the RMI server

            System.out.println("Choose a color from the following list: " + String.join(", ", AVAILABLE_COLORS));
            String chosenColor = stdin.nextLine();
            rmiServer.chooseColor(username, chosenColor);  // Choose a color
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleMessage(String message) {
        synchronized (this) {
            lastSentMessage = message;
            if (message.equals("Match started")) {
                System.out.println(message);
                gameStarted = true;
                this.view = new ViewTUI();  // Initialize the view as TUI when the game starts
            } else if (gameStarted) {
                if (this.view != null) {
                    this.view.Interpreter(message);  // Interpret the message using the view
                }
            } else {
                System.out.println(message);  // Print the message if the game has not started
            }
        }
    }

    public void setRmiClientCallback(RMIClientCallbackInterface rmiClientCallback) {
        this.rmiClientCallback = rmiClientCallback;  // Set the RMI client callback
    }
}
