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

    public TCPServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    // Called from startServer, go to handleConnection
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP server listening on port " + port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleConnection(clientSocket);
                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    // Called from start, go to handleNewTPCClient and start the ServerClientHandler
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        synchronized (mainServer){
            if(mainServer.clientWriters.keySet().size()>= mainServer.numMaxConnections) {
                out.println("Server is actually full");
                return;
            }
        }
        out.println("Enter your username:");
        String username = in.readLine();
        System.out.println("Received username: " + username);
        synchronized (mainServer) {
            if (mainServer.clientWriters.isEmpty()) {
                out.println("Enter the maximum number of players (1-4):");
                mainServer.numMaxConnections = Integer.parseInt(in.readLine());
                System.out.println("Max players set to: " + mainServer.numMaxConnections);
            }
            if (!mainServer.clientWriters.containsKey(username)) {
                out.println("Connection successful");
                out.println("Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                String chosenColor = in.readLine();
                System.out.println("Received color: " + chosenColor);
                while (!mainServer.getAvailableColors().contains(chosenColor)) {
                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                    chosenColor = in.readLine();
                    System.out.println("Received color: " + chosenColor);
                }
                mainServer.chooseColor(username, chosenColor);
                mainServer.socketToUsername.put(clientSocket, username);
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
                mainServer.handleNewTCPClient(username, out);
            } else {
                out.println("Username already taken. Please reconnect with a different username.");
                clientSocket.close();
            }
        }
    }
}
