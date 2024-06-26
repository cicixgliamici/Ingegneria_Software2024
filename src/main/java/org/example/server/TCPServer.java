package org.example.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private int port;
    private int num;
    private Server mainServer;
    private ServerSocket serverSocket; // ServerSocket as an instance variable
    private String firstUsername;

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
 * 
 * This method manages the connection of a new client. It first checks if the server has reached the maximum number 
 * of allowed connections and, if so, informs the client and closes the connection. Otherwise, it prompts the client 
 * for a username, checks for username uniqueness, and if valid, proceeds with additional setup including color selection 
 * and notifying other clients of the new connection. If the client is the first to connect, it also sets up the maximum 
 * number of connections.
 * 
 *
 * @param clientSocket The socket representing the client connection.
 * @throws IOException If an I/O error occurs when handling the connection.
 */
    private void handleConnection(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        synchronized (mainServer) {
            if (mainServer.clientWriters.keySet().size() >= mainServer.numMaxConnections || mainServer.clientWriters.keySet().size()>4 ) {
                out.println("Server is actually full");
                clientSocket.close();
                return;
            }
            out.println("Enter your username:");
            String username = in.readLine();
            System.out.println("Received username: " + username);
            if (mainServer.clientWriters.containsKey(username)) {
                mainServer.onModelSpecific(username,"message:7");
                clientSocket.close();
            } else {
                boolean isFirst = mainServer.getPlayers().isEmpty();
                mainServer.addPlayer(username); // Add player early to synchronize player list and first check
                mainServer.socketToUsername.put(clientSocket, username);
                mainServer.clientWriters.put(username, out);
                if(!isFirst){
                    mainServer.onModelSpecific(username, "setFirst:" + firstUsername);
                    mainServer.onModelGeneric("numCon:"+ mainServer.numMaxConnections);
                    System.out.println("inviato il num max di player " + mainServer.numMaxConnections);
                }
                mainServer.onModelGeneric("message:8");
                mainServer.onModelGeneric("color:" + String.join(",", mainServer.generateColor()));
                String chosenColor = in.readLine();
                while (!mainServer.getAvailableColors().contains(chosenColor)) {
                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", mainServer.getAvailableColors()));
                    chosenColor = in.readLine();
                }
                mainServer.chooseColor(username, chosenColor);
                mainServer.onModelGeneric("newConnection:" + username +","+ chosenColor);
                System.out.println("newConnection:" + username +","+ chosenColor);
                if(isFirst) {
                    firstUsername=username;
                    mainServer.onModelSpecific(username, "setFirst:" + firstUsername);
                    String numPLayer = in.readLine();
                    num = Integer.parseInt(numPLayer);
                    mainServer.setNumMaxConnections(num);
                }
                mainServer.executor.submit(new ServerClientHandler(clientSocket, mainServer.commands, mainServer.model, mainServer.controller, mainServer.socketToUsername, mainServer));
                System.out.println(mainServer.getPlayers().toString());
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
