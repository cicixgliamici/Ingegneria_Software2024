package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class responsible for handling TCP connections to the server.
 */
public class TCPServer {
    private int port;  // Port number for the TCP server
    private Server mainServer;  // Reference to the main server

    /**
     * Constructor to initialize the TCPServer with the specified port and main server.
     *
     * @param port The port number on which the TCP server will listen.
     * @param mainServer The main server instance.
     */
    public TCPServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    /**
     * Starts the TCP server, listening for incoming client connections.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP server listening on port " + port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();  // Accept incoming client connections
                    handleConnection(clientSocket);  // Handle the new client connection
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    /**
     * Handles a new TCP client connection.
     *
     * @param clientSocket The socket associated with the client connection.
     * @throws IOException If an I/O error occurs while handling the connection.
     */
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // Create input stream to read client messages
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);  // Create output stream to send messages to the client
        out.println("Enter your username:");
        String username = in.readLine();  // Read the username from the client
        System.out.println("Received username: " + username);

        synchronized (mainServer) {
            if (mainServer.clientWriters.isEmpty()) {
                // If this is the first client, ask for the maximum number of players
                out.println("Enter the maximum number of players (1-4):");
                mainServer.numMaxConnections = Integer.parseInt(in.readLine());  // Read and set the maximum number of players
                System.out.println("Max players set to: " + mainServer.numMaxConnections);
            }
            if (!mainServer.clientWriters.containsKey(username)) {
                // If the username is not already taken, proceed with the connection
                mainServer.handleNewClient(username, out);  // Handle new client connection
                out.println("Connection successful");
                out.println("Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                String chosenColor = in.readLine();  // Read the color chosen by the client
                System.out.println("Received color: " + chosenColor);
                while (!mainServer.getAvailableColors().contains(chosenColor)) {
                    // If the chosen color is not available, ask the client to choose again
                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                    chosenColor = in.readLine();  // Read the new color choice
                    System.out.println("Received color: " + chosenColor);
                }
                mainServer.handleClientColorChoice(username, chosenColor);  // Handle the color choice
                mainServer.socketToUsername.put(clientSocket, username);  // Associate the client socket with the username
                // Submit a new task to handle client commands in a separate thread
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
            } else {
                // If the username is already taken, inform the client and close the connection
                out.println("Username already taken. Please reconnect with a different username.");
                clientSocket.close();
            }
        }
    }
}
