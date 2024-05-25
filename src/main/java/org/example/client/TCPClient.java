package org.example.client;

import org.example.view.View;

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
     * Starts the TCP client, establishes a connection to the server, and manages input/output.
     */
    public void startTCPClient() throws Exception {
        // Establish a connection to the server.
        socket = new Socket(ip, port);
        System.out.println("Connected to " + ip + ":" + port);
        // Set up I/O streams.
        Scanner socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream(), true);
        // Thread to handle server messages.
        Thread serverListener = new Thread(() -> {
            while (socketIn.hasNextLine()) {
                handleServerMessages(socketIn);
            }
        });
        serverListener.start();
        Scanner stdin = new Scanner(System.in);
        if (view.getFlag() == 0) {
            Thread userInputThread = new Thread(() -> {
                while (stdin.hasNextLine()) {
                    String input = stdin.nextLine();
                    if (isValidInput(input)) {
                        socketOut.println(input);  // Send valid input to the server
                        lastSentMessage = input;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                    }
                }
            });
            userInputThread.start();
            userInputThread.join();
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

    public void sendSetObjStrater(int side, int choice){
        if (socketOut != null) {
            socketOut.println("setObjStarter:" + side + "," + choice);
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
            if (line.startsWith("setup:")) {
                processSetup(line);
            } else if(line.equals("Enter your username:")){
                System.out.println("Poly QuizShow1:");
                System.out.println("Enter your username:");
            }
            else {
                this.view.Interpreter(line);
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

    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
}