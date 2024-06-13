package org.example.server;

import org.example.controller.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class handles TCP connections for the server, accepting client connections and managing game state.
 */
public class TCPServer {
    private int port;
    private int num;
    private Server mainServer;
    private ServerSocket serverSocket; // ServerSocket as an instance variable

    /**
     * Constructor to initialize the TCP server.
     *
     * @param port The port number on which the server will listen for connections.
     * @param mainServer A reference to the main server managing the game state and connections.
     */
    public TCPServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    /**
     * Starts the TCP server that listens for client connections on a specified port.
     * This method continuously listens and accepts new client connections.
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port); // Create a new ServerSocket
            System.out.println("TCP server listening on port " + port);
            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept new client connections
                    handleConnection(clientSocket); // Handle the new connection
                } catch (IOException e) {
                    if (serverSocket.isClosed()) {
                        System.out.println("Server socket closed, stopping server.");
                        break;
                    }
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    /**
     * Handles individual client connections by setting up input and output streams
     * and processing initial communication such as username and player settings.
     *
     * @param clientSocket The socket representing the client connection.
     */
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Setup input stream
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // Setup output stream
        synchronized (mainServer) {
            // Check if server is full
            if (mainServer.clientWriters.keySet().size() >= mainServer.numMaxConnections || mainServer.clientWriters.keySet().size() > 4) {
                out.println("Server is actually full"); // Notify client that server is full
                clientSocket.close();
                return;
            }
            out.println("Enter your username:"); // Ask for username
            String username = in.readLine(); // Read username
            System.out.println("Received username: " + username);
            if (mainServer.clientWriters.containsKey(username)) {
                mainServer.onModelSpecific(username, "message:7"); // Notify client that username is taken
                clientSocket.close();
            } else {
                boolean isFirst = mainServer.getPlayers().isEmpty(); // Check if first player
                mainServer.addPlayer(username); // Add player to server
                mainServer.socketToUsername.put(clientSocket, username); // Map socket to username
                mainServer.clientWriters.put(username, out); // Add client writer to map

                if (!isFirst) {
                    mainServer.onModelGeneric("numCon:" + mainServer.numMaxConnections);
                    System.out.println("Sent max player count: " + mainServer.numMaxConnections);
                }
                mainServer.onModelGeneric("message:8");
                mainServer.onModelGeneric("color:" + String.join(",", mainServer.generateColor())); // Send available colors
                String chosenColor = in.readLine();
                // Validate chosen color
                while (!mainServer.getAvailableColors().contains(chosenColor)) {
                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                    chosenColor = in.readLine();
                }
                mainServer.chooseColor(username, chosenColor); // Assign chosen color
                mainServer.onModelGeneric("newConnection:" + username + "," + chosenColor);
                System.out.println("newConnection:" + username + "," + chosenColor);
                if (isFirst) {
                    mainServer.onModelSpecific(username, "setFirst");
                    String numPLayer = in.readLine();
                    num = Integer.parseInt(numPLayer);
                    mainServer.setNumMaxConnections(num); // Set max connections
                }
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
                System.out.println(mainServer.getPlayers().toString());
                mainServer.handleNewTCPClient(username, out); // Handle new client
            }
        }
    }

    /**
     * Stops the TCP server by closing the server socket.
     */
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Close the server socket
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
