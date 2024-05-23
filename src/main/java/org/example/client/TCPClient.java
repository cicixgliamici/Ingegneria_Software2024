package org.example.client;

import org.example.view.View;
import org.example.view.ViewTUI;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The TCPClient class handles the client-side logic for connecting to a server using TCP.
 * It manages the connection, handles incoming messages, and sends user input to the server.
 */
public class TCPClient {
    private String ip;  // IP address of the server
    private int port;  // Port number of the server
    private View view;  // View interface for interacting with the user
    private volatile boolean gameStarted = false;  // Use volatile to ensure visibility across threads
    private String lastSentMessage = "";  // Last message sent by the client

    private Socket socket;
    private PrintWriter socketOut;

    /**
     * Constructor for TCPClient.
     *
     * @param ip The IP address of the server.
     * @param port The port number of the server.
     * @param view The view interface for interacting with the user.
     */
    public TCPClient(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }

    /**
     * Establishes a connection to the server.
     */
    public void connect() throws Exception {
        socket = new Socket(ip, port);  // Connect to the server using TCP
        System.out.println("Connected to " + ip + ":" + port);
        Scanner socketIn = new Scanner(socket.getInputStream());  // Scanner for server input
        socketOut = new PrintWriter(socket.getOutputStream(), true);  // PrintWriter for server output
        // Thread to handle server messages
        Thread serverListener = new Thread(() -> handleServerMessages(socketIn));
        serverListener.start();
    }

    /**
     * Starts the TCP client and manages the connection to the server.
     */
    public void startTCPClient() throws Exception {
        connect();

        Scanner stdin = new Scanner(System.in);  // Scanner for user input

        // Thread to handle user input
        Thread userInputThread = new Thread(() -> handleUserInput(stdin, socketOut));
        userInputThread.start();

        // Join threads to ensure the main thread waits for them to finish
        userInputThread.join();
    }

    /**
     * Handles messages received from the server.
     *
     * @param socketIn The Scanner for reading server input.
     */
    private void handleServerMessages(Scanner socketIn) {
        while (socketIn.hasNextLine()) {
            String line = socketIn.nextLine();
            if (line.startsWith("setup:")) {
                processSetup(line);
            } else {
                handleMessage(line);
            }
        }
    }

    private void processSetup(String setupMsg) {
        // Expected format: "setup:colors=Red,Blue;first=true"
        String[] parts = setupMsg.substring(6).split(";");
        String[] colors = parts[0].split("=")[1].split(",");
        boolean isFirst = Boolean.parseBoolean(parts[1].split("=")[1]);
        SwingUtilities.invokeLater(() -> view.updateSetupUI(colors, isFirst));
    }
    /**
     * Handles user input and sends it to the server.
     *
     * @param stdin The Scanner for reading user input.
     * @param socketOut The PrintWriter for sending output to the server.
     */
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

    /**
     * Validates the user input based on the last sent message or on the message present in the input.
     *
     * @param input The user input to validate.
     * @return true if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        // Various checks based on the type of input and last sent message
        // This function prevents sending invalid data to the server
        if (lastSentMessage.equals("Enter the maximum number of players (1-4):")) {
            try {
                int numPlayers = Integer.parseInt(input);
                return numPlayers >= 1 && numPlayers <= 4;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (input.startsWith("setObjStarter:")){
            try {
                String[] parts = input.substring(14).split(",");
                if (parts.length != 2) {
                    return false;
                }
                int StartSide = Integer.parseInt(parts[0]);
                int NumObj = Integer.parseInt(parts[1]);
                return (StartSide == 1 || StartSide == 2) && (NumObj == 1 || NumObj == 2);

            } catch (NumberFormatException e){
                return false;
            }

        }
        else if (input.startsWith("draw:")) {
            try {
                int drawNumber = Integer.parseInt(input.substring(5));
                return drawNumber >= 0 && drawNumber <= 5;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (input.startsWith("play:")) {
            try {
                String[] parts = input.substring(5).split(",");
                if (parts.length != 4) {
                    return false;
                }
                int cardNumber = Integer.parseInt(parts[0]);
                int side = Integer.parseInt(parts[1]);
                return (cardNumber >= 0 && cardNumber <= 5) && (side == 1 || side == 2);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Handles a message received from the server.
     *
     * @param message The message received from the server.
     */
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

    /**
     * Sends the username to the server after the connection is established.
     *
     * @param username The username to send.
     */
    public void sendUsername(String username) {
        if (socketOut != null) {
            socketOut.println(username);
            socketOut.flush();
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}