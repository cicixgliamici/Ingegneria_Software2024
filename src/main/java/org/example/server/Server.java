package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.view.View;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Server class that start the server and wait for the clients
 *   Subscribed to the listeners list of model (and actually the only one
 *   and tell the Clients when something changes
 */

public class Server implements ModelChangeListener {
    private int port;
    private Model model;
    private Controller controller;
    private List<Player> players ; // List of players for the controller
    private Map<String, JSONObject> commands = new HashMap<>();        // Map for the commands written by the Client and the commands in the JSON
    private Map<String, PrintWriter> clientWriters = new HashMap<>();  // Map that associates a username (unique, from client) to a PrintWriter object
    private Map<String, View> clientView = new HashMap<>();
    private Map<Socket, String> socketToUsername = new HashMap<>();

    public Server(int port) throws IOException, ParseException {
        this.port = port;
        this.model = new Model();
        this.model.addModelChangeListener(this);
        this.players = new ArrayList<>();
        loadCommands();       // Load the commands from resources->Commands.json
        this.controller = new Controller(model);  // Subscribes the Server to the model listeners list
    }

  public void startServer() {
    int numConnections = 0;
    int numMaxConnections = 4; // Default maximum number of players
    boolean maxConnectionsReached = false;
    ExecutorService executor = Executors.newFixedThreadPool(128);
    try (ServerSocket serverSocket = new ServerSocket(port)) {
        System.out.println("Server listening on port " + port);
        while (!maxConnectionsReached) {
            try {
                Socket clientSocket = serverSocket.accept();
                synchronized (this) {
                    if (numConnections < numMaxConnections) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println("Enter your username:");
                        String username = in.readLine();
                        if (clientWriters.isEmpty()) {
                            out.println("Enter the maximum number of players (1-4):");
                            numMaxConnections = Integer.parseInt(in.readLine());
                        }
                        if (!clientWriters.containsKey(username)) {
                            clientWriters.put(username, out);
                            socketToUsername.put(clientSocket, username);  // Memorizza il socket associato all'username
                            players.add(new Player(username));
                            out.println("Connection successful");
                            executor.submit(new ServerClientHandler(clientSocket, commands, model, controller, socketToUsername)); // pass the map to the handler
                            numConnections++;
                            if (numConnections == numMaxConnections) {
                                maxConnectionsReached = true;
                            }
                        } else {
                            out.println("Username already taken. Please reconnect with a different username.");
                            clientSocket.close();
                        }
                    } else {
                        // Notify client and close socket
                        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                            out.println("Connection failed: maximum number of players reached.");
                        }
                        clientSocket.close();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            }
        }

        // Notify all connected clients that the match is starting
        for (PrintWriter writer : clientWriters.values()) {
            writer.println("Match started");
            writer.flush();
        }
        System.out.println("Match started with " + numConnections + " connections.");
        controller.setPlayers(players);
        controller.initializeController();
    } catch (IOException e) {
        System.out.println("Could not listen on port " + port + ": " + e.getMessage());
    }
}

    /*** Load command from a JSON, where we can choose what parameters do we
     * need from a client and what we use from the server
     */
    public void loadCommands() throws IOException {
        String path = "src/main/resources/Commands.json";
        String text = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject obj = new JSONObject(text);
        JSONObject jsonCommands = obj.getJSONObject("commands");
        for (String key : jsonCommands.keySet()) {
            commands.put(key, jsonCommands.getJSONObject(key));
        }
    }

    /**
     * For every client added, we send them a message when
     * the listener tell us something in the model is changed
     */
    @Override
public void onModelChange(String username, String specificMessage, String generalMessage) {
    synchronized (this) {
        for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
            PrintWriter writer = entry.getValue();
            if (entry.getKey().equals(username)) {
                writer.println(specificMessage);  // Send specific message to the player who drew the card
            } else {
                writer.println(generalMessage);  // Send a generic message to all other players
            }
            writer.flush();
        }
    }
}

@Override
public void onModelSpecific(String username, String specificMessage) {
    synchronized (this) {
        for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
            PrintWriter writer = entry.getValue();
            if (entry.getKey().equals(username)) { // specific message only to one client
                writer.println(specificMessage);
                writer.flush();
            }
        }
    }
}

@Override
public void onModelGeneric(String generalMessage) {
    synchronized (this) {
        for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
            PrintWriter writer = entry.getValue();
            writer.println(generalMessage);  // general message to everyone
            writer.flush();
        }
    }
}

    public Map<String, PrintWriter> getClientWriters() {
        return clientWriters;
    }
}
