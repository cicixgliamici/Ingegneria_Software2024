package org.example.client;

import org.example.exception.PlaceholderNotValid;
import org.example.view.View;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The TCPClient class handles the client-side logic for connecting to a server using TCP.
 * It manages the connection, handles incoming messages, and sends user input to the server.
 */
public class TCPClient {

    private int port;  // Port number of the server
    private String ip;  // IP address of the server
    private String lastSentMessage = "";  // Last message sent by the client
    private View view;  // View interface for interacting with the user
    private volatile boolean gameStarted = false;  // Use volatile to ensure visibility across threads
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
     * Starts the TCP client, establishes a connection to the server, and manages input/output.
     */
    public void startTCPClient() throws Exception {
        try {
            // Establish a connection to the server.
            socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + ":" + port);
            // Set up I/O streams.
            Scanner socketIn = new Scanner(socket.getInputStream());
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            // Thread to handle server messages.
            Thread serverListener = new Thread(() -> {
                try {
                    while (socketIn.hasNextLine()) {
                        handleServerMessages(socketIn);
                    }
                } catch (Exception e) {
                    System.out.println("Connection to server lost. Closing client.");
                    closeConnection();
                }
            });
            serverListener.start();
            Scanner stdin = new Scanner(System.in);
            if (view.getFlag() == 0) {
                Thread userInputThread = new Thread(() -> {
                    try {
                        while (stdin.hasNextLine()) {
                            String input = stdin.nextLine();
                            if (isValidInput(input)) {
                                socketOut.println(input);  // Send valid input to the server
                                lastSentMessage = input;
                            } else {
                                System.out.println("Invalid input. Please try again.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error in user input thread. Closing client.");
                        closeConnection();
                    }
                });
                userInputThread.start();
                userInputThread.join();
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to server: " + e.getMessage());
            closeConnection();
        }
    }

    /**
     * Closes the connection and exits the application.
     */
    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (socketOut != null) {
                socketOut.close();
            }
            this.view.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    /**
     * Sends the username to the server after the connection is established.
     *
     * @param username The username to send.
     */
    public void sendUsername(String username) {
        if (socketOut != null) {
            System.out.println("Sending username: " + username);
            socketOut.println(username);
            socketOut.flush();
        }
    }

    /**
     * Sends the chosen color and number of players to the server.
     *
     * @param color The chosen color.
     * @param numPlayers The number of players.
     */
    public void sendColorAndNumPlayers(String color, String numPlayers) {
        if (socketOut != null) {
            socketOut.println(color);
            socketOut.flush();
            socketOut.println(numPlayers);
            socketOut.flush();
        }
    }

    /**
     * Sends the object starter set command to the server.
     *
     * @param side The side for setting the object starter.
     * @param choice The choice made for the object starter.
     */
    public void sendSetObjStarter(int side, int choice) {
        if (socketOut != null) {
            socketOut.println("setObjStarter:" + side + "," + choice);
            socketOut.flush();
        }
    }

    /**
     * Sends a chat message to the server.
     *
     * @param message The chat message to send.
     */
    public void sendChat(String message) {
        if (socketOut != null) {
            socketOut.println("chatS:" + message);
            socketOut.flush();
        }
    }

    /**
     * Sends the draw command to the server.
     *
     * @param choice The choice for the draw action.
     */
    public void sendDraw(int choice) {
        if (socketOut != null) {
            socketOut.println("draw:" + choice);
            socketOut.flush();
        }
    }

    /**
     * Sends the play command to the server.
     *
     * @param choice The choice of the play.
     * @param side The side for the play.
     * @param x The x-coordinate for the play.
     * @param y The y-coordinate for the play.
     */
    public void sendPlay(int choice, int side, int x, int y) {
        if (socketOut != null) {
            socketOut.println("play:" + choice + "," + side + "," + x + "," + y);
            socketOut.flush();
        }
    }

    /**
     * Handles messages received from the server.
     *
     * @param socketIn The Scanner for reading server input.
     */
    private void handleServerMessages(Scanner socketIn) {
        while (socketIn.hasNextLine()) {
            String line = socketIn.nextLine();
            if (line.equals("serverShutdown")) {
                System.out.println("Server is shutting down. Closing client.");
                closeConnection();
                return;
            }
            if (line.startsWith("setup:")) {
                processSetup(line);
            } else if(line.equals("Enter your username:")){
                System.out.println("Enter your username:");
            } else {
                this.view.Interpreter(line);
            }
        }
        // Se il loop termina, significa che il server non invia più dati
        System.out.println("Server has closed the connection. Closing client.");
        closeConnection();
    }

    /**
     * Processes the setup message received from the server.
     *
     * @param setupMsg The setup message received from the server.
     */
    private void processSetup(String setupMsg) {
        // Split the setup message into its components
        String[] parts = setupMsg.substring(6).split(";");
        // Extract the colors from the setup message
        String[] colors = parts[0].split("=")[1].split(",");
        // Extract and parse the first player indicator
        boolean isFirst = Boolean.parseBoolean(parts[1].split("=")[1]);
        // Update the UI with the setup information on the Event Dispatch Thread
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
     * Checks if the client is currently connected to the server.
     *
     * @return true if the socket is not null, connected, and not closed; false otherwise.
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}
