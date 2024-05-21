package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private int port;
    private Server mainServer;

    /**
     * Constructor to initialize the TCP server.
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
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP server listening on port " + port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept a new client connection
                    handleConnection(clientSocket); // Handle the newly accepted connection
                } catch (IOException e) {
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
     * @param clientSocket The socket representing the client connection.
     */
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Ensure that the server does not exceed the maximum number of connections
        synchronized (mainServer) {
            if (mainServer.clientWriters.keySet().size() >= mainServer.numMaxConnections) {
                out.println("Server is actually full");
                return;
            }
        }

        // Prompt for and receive the username
        out.println("Enter your username:");
        String username = in.readLine();
        System.out.println("Received username: " + username);

        synchronized (mainServer) {
            // Set maximum number of players if this is the first client
            if (mainServer.clientWriters.isEmpty()) {
                out.println("Enter the maximum number of players (1-4):");
                mainServer.numMaxConnections = Integer.parseInt(in.readLine());
                System.out.println("Max players set to: " + mainServer.numMaxConnections);
            }

            // Check if username is already taken
            if (!mainServer.clientWriters.containsKey(username)) {
                out.println("Connection successful");
                out.println("Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                String chosenColor = in.readLine();
                System.out.println("Received color: " + chosenColor);

                // Ensure chosen color is available
                while (!mainServer.getAvailableColors().contains(chosenColor)) {
                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                    chosenColor = in.readLine();
                    System.out.println("Received color: " + chosenColor);
                }

                // Set the chosen color for the player
                mainServer.chooseColor(username, chosenColor);
                mainServer.socketToUsername.put(clientSocket, username);
                // Handle the new TCP client in a separate thread
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
                mainServer.handleNewTCPClient(username, out);
            } else {
                out.println("Username already taken. Please reconnect with a different username.");
                clientSocket.close();
            }
        }
    }
}
