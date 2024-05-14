package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.view.View;
import org.example.server.rmi.RMIServerImpl;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Server class that starts the server and waits for the clients
 * Subscribed to the listeners list of model (and actually the only one)
 * and tells the Clients when something changes
 */
public class Server implements ModelChangeListener {
    private int port;  // Port number for the server
    private Model model;  // The game model
    private Controller controller;  // The game controller
    private List<Player> players;  // List of players for the controller
    private Map<String, JSONObject> commands = new HashMap<>();  // Map for the commands written by the Client and the commands in the JSON
    private Map<String, PrintWriter> clientWriters = new HashMap<>();  // Map that associates a username (unique, from client) to a PrintWriter object
    private Map<String, View> clientView = new HashMap<>();  // Map of client views
    private Map<Socket, String> socketToUsername = new HashMap<>();  // Map to associate sockets with usernames
    private List<String> availableColors;  // List of available colors
    private AtomicInteger setObjStarterCount = new AtomicInteger(0);  // Synchronized counter
    private ExecutorService executor;  // Thread pool for handling client connections
    private RMIServerImpl rmiServer;  // RMI server implementation

    public Server(int port) throws IOException, ParseException {
        this.port = port;
        this.model = new Model();
        this.model.addModelChangeListener(this);
        this.players = new ArrayList<>();
        loadCommands();  // Load the commands from resources->Commands.json
        this.controller = new Controller(model);  // Subscribes the Server to the model listeners list
        this.availableColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));  // Initialize available colors
    }

    public void startServer() {
        executor = Executors.newFixedThreadPool(128);  // Create a thread pool with a fixed number of threads
        executor.submit(this::startTCPServer);  // Start the TCP server
        executor.submit(this::startRMIServer);  // Start the RMI server
    }

    /**
     * TCP ZONE
     * Starts the TCP server to accept client connections
     */
    private void startTCPServer() {
        int numConnections = 0;
        int numMaxConnections = 4;  // Default maximum number of players
        boolean maxConnectionsReached = false;
        try (ServerSocket serverSocket = new ServerSocket(port)) {  // Create a server socket on the specified port
            System.out.println("TCP server listening on port " + port);
            while (!maxConnectionsReached) {
                try {
                    Socket clientSocket = serverSocket.accept();  // Accept incoming client connections
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
                                socketToUsername.put(clientSocket, username);  // Associate the socket with the username
                                players.add(new Player(username));
                                out.println("Connection successful");
                                out.println("Choose a color from the following list: " + String.join(", ", availableColors));
                                String chosenColor = in.readLine();
                                while (!availableColors.contains(chosenColor)) {
                                    out.println("Color not available. Choose a color from the following list: " + String.join(", ", availableColors));
                                    chosenColor = in.readLine();
                                }
                                availableColors.remove(chosenColor);  // Remove chosen color from the list
                                out.println("You have chosen the color " + chosenColor);
                                executor.submit(new ServerClientHandler(clientSocket, commands, model, controller, socketToUsername, this));  // Handle the client connection
                                numConnections++;
                                if (numConnections == numMaxConnections) {
                                    maxConnectionsReached = true;
                                    onModelGeneric("Match started");
                                    controller.setPlayers(players);
                                    controller.initializeController();
                                    gameFlow(numConnections);  // Start the game flow
                                }
                            } else {
                                out.println("Username already taken. Please reconnect with a different username.");
                                clientSocket.close();
                            }
                        } else {
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
        } catch (IOException e) {
            System.out.println("Could not listen on port " + port + ": " + e.getMessage());
        }
    }

    /**
     * Game flow logic
     */
    public void gameFlow(int numConnections) throws IOException {
        waitForSetObjStarter(numConnections);  // Wait for all players to set their starter objects
    }

    /**
     * Wait for all players to set their starter objects
     */
    private void waitForSetObjStarter(int numConnections) {
        while (setObjStarterCount.get() < numConnections) {
            try {
                Thread.sleep(100);  // Check every 100ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All clients have set correctly");
        onModelGeneric("message:3");
    }

    public void incrementSetObjStarterCount() {
        setObjStarterCount.incrementAndGet();
    }

    /**
     * Load commands from a JSON file
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
     * Send messages to clients when the model changes
     */
    public void onModelChange(String username, String specificMessage, String generalMessage) {
        synchronized (this) {
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);  // Send specific message to the player who triggered the change
                } else {
                    writer.println(generalMessage);  // Send a general message to all other players
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
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);  // Send specific message to the specified player
                    writer.flush();
                }
            }
        }
    }

    @Override
    public void onModelGeneric(String generalMessage) {
        synchronized (this) {
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(generalMessage);  // Send a general message to all players
                writer.flush();
            }
            if (rmiServer != null) {
                rmiServer.sendToAllClients(generalMessage);  // Send a general message to all RMI clients
            }
        }
    }

    /**
     * RMI ZONE
     * Starts the RMI server to accept client connections
     */
    private void startRMIServer() {
        try {
            rmiServer = new RMIServerImpl(this);
            Registry registry = LocateRegistry.createRegistry(port + 1);  // Create a registry on a different port for RMI
            registry.bind("RMIServer", rmiServer);  // Bind the RMI server
            System.out.println("RMI server started on port " + (port + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a player to the game
     */
    public void addPlayer(String username) {
        synchronized (this) {
            players.add(new Player(username));
        }
    }

    /**
     * Player chooses a color
     */
    public void chooseColor(String username, String color) {
        synchronized (this) {
            if (availableColors.contains(color)) {
                availableColors.remove(color);
                System.out.println(username + " has chosen the color " + color);
            } else {
                System.out.println("Color " + color + " not available.");
            }
        }
    }
}
