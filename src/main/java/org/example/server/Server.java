package org.example.server;

import org.example.controller.Controller;
import org.example.controller.Player;
import org.example.model.Model;
import org.example.server.rmi.RMIClientCallbackInterface;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Main server class that handles TCP and RMI client connections,
 * manages game state, and communicates with clients.
 */
public class Server implements ModelChangeListener {
    private int tcpPort;  // Port number for the TCP server
    private int rmiPort;  // Port number for the RMI server
    protected Model model;  // The game model
    protected Controller controller;  // The game controller
    private List<Player> players;  // List of players for the controller
    protected Map<String, JSONObject> commands = new HashMap<>();  // Map for the commands from the Client and the commands in the JSON
    protected Map<String, PrintWriter> clientWriters = new HashMap<>();  // Map that associates a username to a PrintWriter object
    protected Map<Socket, String> socketToUsername = new HashMap<>();  // Map to associate sockets with usernames
    protected Map<String, RMIClientCallbackInterface> rmiClientCallbacks = new HashMap<>();  // Map to associate RMI clients with usernames
    private List<String> availableColors;  // List of available colors
    private AtomicInteger setObjStarterCount = new AtomicInteger(0);  // Synchronized counter
    protected ExecutorService executor;  // Thread pool for handling client connections
    private TCPServer tcpServer;  // TCP server implementation
    private RMIServer rmiServer;  // RMI server implementation
    private int numConnections = 0;  // Number of active connections
    public int numMaxConnections = 4;  // Default maximum number of players

    /**
     * Constructor to initialize the server with TCP and RMI ports.
     *
     * @param tcpPort The TCP port number.
     * @param rmiPort The RMI port number.
     * @throws IOException if an I/O error occurs.
     * @throws ParseException if a parsing error occurs.
     */
    public Server(int tcpPort, int rmiPort) throws IOException, ParseException {
        this.tcpPort = tcpPort;
        this.rmiPort = rmiPort;
        this.model = new Model();
        this.model.addModelChangeListener(this);  // Add the server as a listener to the model
        this.players = new ArrayList<>();
        loadCommands();  // Load the commands from resources->Commands.json
        this.controller = new Controller(model);  // Subscribes the Server to the model listeners list
        this.availableColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));  // Initialize available colors
    }

    /**
     * Starts the server by initializing the TCP and RMI servers.
     */
    public void startServer() {
        executor = Executors.newFixedThreadPool(128);  // Create a thread pool with a fixed number of threads
        tcpServer = new TCPServer(tcpPort, this);  // Initialize the TCP server
        rmiServer = new RMIServer(rmiPort, this);  // Initialize the RMI server
        executor.submit(tcpServer::start);  // Start the TCP server
        executor.submit(rmiServer::start);  // Start the RMI server
    }

    /**
     * Handles new TCP client connections.
     *
     * @param username The username of the client.
     * @param out The PrintWriter for the client.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void handleNewClient(String username, PrintWriter out) throws RemoteException {
        synchronized (this) {
            clientWriters.put(username, out);  // Add client writer to map
            players.add(new Player(username));  // Add player to list
            numConnections++;
            if (numConnections == numMaxConnections) {  // If maximum number of connections is reached
                onModelGeneric("Match started");  // Notify all clients
                controller.setPlayers(players);  // Set the players in the controller
                controller.initializeController();  // Initialize the controller
                try {
                    gameFlow(numConnections);  // Start the game
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles new RMI client connections.
     *
     * @param username The username of the client.
     * @param clientCallback The RMI client callback interface.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void handleNewRMIClient(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        synchronized (this) {
            rmiClientCallbacks.put(username, clientCallback);  // Add RMI client callback to map
            players.add(new Player(username));  // Add player to list
            numConnections++;
            if (numConnections == numMaxConnections) {  // If maximum number of connections is reached
                onModelGeneric("Match started");  // Notify all clients
                controller.setPlayers(players);  // Set the players in the controller
                controller.initializeController();  // Initialize the controller
                try {
                    gameFlow(numConnections);  // Start the game
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handles the color choice from the client.
     *
     * @param username The username of the client.
     * @param chosenColor The chosen color.
     */
    public void handleClientColorChoice(String username, String chosenColor) {
        synchronized (this) {
            if (availableColors.contains(chosenColor)) {
                availableColors.remove(chosenColor);  // Remove the chosen color from available colors
                System.out.println(username + " has chosen the color " + chosenColor);
            } else {
                System.out.println("Color " + chosenColor + " not available.");
            }
        }
    }

    /**
     * Game flow logic.
     *
     * @param numConnections The number of active connections.
     * @throws IOException if an I/O error occurs.
     */
    public void gameFlow(int numConnections) throws IOException {
        waitForSetObjStarter(numConnections);  // Wait for all players to set their starter objects
    }

    /**
     * Waits for all players to set their starter objects.
     *
     * @param numConnections The number of active connections.
     */
    private void waitForSetObjStarter(int numConnections) {
        while (setObjStarterCount.get() < numConnections) {  // Wait until all players have set their starter objects
            try {
                Thread.sleep(100);  // Check every 100ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("All clients have set correctly");
        try {
            onModelGeneric("message:3");  // Notify all clients
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Increments the setObjStarterCount.
     */
    public void incrementSetObjStarterCount() {
        setObjStarterCount.incrementAndGet();
    }

    /**
     * Loads commands from a JSON file.
     *
     * @throws IOException if an I/O error occurs.
     */
    public void loadCommands() throws IOException {
        String path = "src/main/resources/Commands.json";
        String text = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject obj = new JSONObject(text);
        JSONObject jsonCommands = obj.getJSONObject("commands");
        for (String key : jsonCommands.keySet()) {
            commands.put(key, jsonCommands.getJSONObject(key));  // Load commands into the map
        }
    }

    /**
     * Sends messages to clients when the model changes.
     *
     * @param username The username of the player who triggered the change.
     * @param specificMessage The specific message for the player.
     * @param generalMessage The general message for all other players.
     * @throws RemoteException if a remote communication error occurs.
     */
    public void onModelChange(String username, String specificMessage, String generalMessage) throws RemoteException {
        synchronized (this) {
            // Send messages to TCP clients
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);  // Send specific message to the player who triggered the change
                } else {
                    writer.println(generalMessage);  // Send a general message to all other players
                }
                writer.flush();
            }
            // Send messages to RMI clients
            for (Map.Entry<String, RMIClientCallbackInterface> entry : rmiClientCallbacks.entrySet()) {
                try {
                    if (entry.getKey().equals(username)) {
                        entry.getValue().receiveMessage(specificMessage);  // Send specific message to the player who triggered the change
                    } else {
                        entry.getValue().receiveMessage(generalMessage);  // Send a general message to all other players
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sends a specific message to a specific player.
     *
     * @param username The username of the player.
     * @param specificMessage The specific message.
     */
    @Override
    public void onModelSpecific(String username, String specificMessage) {
        synchronized (this) {
            // Send specific message to the specified TCP client
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);
                    writer.flush();
                }
            }
            // Send specific message to the specified RMI client
            for (Map.Entry<String, RMIClientCallbackInterface> entry : rmiClientCallbacks.entrySet()) {
                try {
                    if (entry.getKey().equals(username)) {
                        entry.getValue().receiveMessage(specificMessage);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sends a general message to all players.
     *
     * @param generalMessage The general message.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public void onModelGeneric(String generalMessage) throws RemoteException {
        synchronized (this) {
            // Send a general message to all TCP clients
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(generalMessage);
                writer.flush();
            }
            // Send a general message to all RMI clients
            for (RMIClientCallbackInterface callback : rmiClientCallbacks.values()) {
                try {
                    callback.receiveMessage(generalMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Adds a player to the game.
     *
     * @param username The username of the player.
     */
    public void addPlayer(String username) {
        synchronized (this) {
            players.add(new Player(username));
        }
    }

    /**
     * Handles a player's color choice.
     *
     * @param username The username of the player.
     * @param color The chosen color.
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

    /**
     * Gets the controller instance.
     *
     * @return The controller instance.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Gets the list of players.
     *
     * @return The list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the list of available colors.
     *
     * @return The list of available colors.
     */
    public List<String> getAvailableColors() {
        return availableColors;
    }
}
