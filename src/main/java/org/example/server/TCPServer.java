
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
    private ServerSocket serverSocket; // ServerSocket as an instance variable

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
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP server listening on port " + port);
            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleConnection(clientSocket);
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
     * @param clientSocket The socket representing the client connection.
     */
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        synchronized (mainServer) {
            if (mainServer.clientWriters.keySet().size() >= mainServer.numMaxConnections) {
                out.println("Server is actually full");
                clientSocket.close();
                return;
            }
            out.println("Enter your username:");
            String username = in.readLine();
            System.out.println("Received username: " + username);
            if (mainServer.clientWriters.containsKey(username)) {
                out.println("Username already taken. Please reconnect with a different username.");
                clientSocket.close();
            } else {
                boolean isFirst = mainServer.getPlayers().isEmpty();
                mainServer.addPlayer(username); // Add player early to synchronize player list and first check
                mainServer.onModelSpecific(username,"message:8");
                mainServer.socketToUsername.put(clientSocket, username);
                mainServer.clientWriters.put(username, out);
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
                mainServer.handleNewTCPClient(username, out);
            }
        }
    }

    /**
     * Stops the TCP server by closing the server socket.
     */
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

