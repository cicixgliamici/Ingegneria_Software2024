package org.example.server;

import org.example.controller.Controller;
import org.example.controller.GameFlow;
import org.example.controller.Player;
import org.example.enumeration.Color;
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
 * manages the game state, and communicates with clients.
 */
public class Server implements ModelChangeListener {
    private int tcpPort; // TCP port for client connections.
    private int rmiPort; // RMI port for client connections.
    protected Model model; // Game model that maintains the state.
    protected Controller controller; // Controller that manages the game logic.
    private List<Player> players; // List of players, both RMI and TCP.
    protected Map<String, JSONObject> commands = new HashMap<>(); // Commands supported by the server.
    protected Map<String, PrintWriter> clientWriters = new HashMap<>(); // Map to manage outputs to TCP clients.
    protected Map<Socket, String> socketToUsername = new HashMap<>(); // Map socket to username for identification.
    protected Map<String, RMIClientCallbackInterface> rmiClientCallbacks = new HashMap<>(); // Callbacks for RMI clients.
    private List<String> availableColors; // Available colors for player selection.
    private AtomicInteger setObjStarterCount = new AtomicInteger(0); // Counter to track how many players have chosen the initial card.
    protected ExecutorService executor; // Executor to manage multiple threads.
    private TCPServer tcpServer; // TCP server.
    private RMIServer rmiServer; // RMI server.
    private int numConnections = 0; // Counter for active connections.
    public int numMaxConnections = 4; // Maximum number of connections.
    public GameFlow gameFlow; // Game flow that manages the game phases.

    /**
     * Server class constructor.
     *
     * @param tcpPort TCP port.
     * @param rmiPort RMI port.
     * @throws IOException
     * @throws ParseException
     */
    public Server(int tcpPort, int rmiPort) throws IOException, ParseException {
        this.tcpPort = tcpPort;
        this.rmiPort = rmiPort;
        this.model = new Model();
        this.model.addModelChangeListener(this);
        this.players = new ArrayList<>();
        loadCommands(); // Load commands from the JSON file.
        this.controller = new Controller(model);
        this.availableColors = new ArrayList<>(Arrays.asList("Red", "Blue", "Green", "Yellow"));
    }

    /**
     * Starts the server, both TCP and RMI.
     */
    public void startServer() {
        executor = Executors.newFixedThreadPool(128); // Create a thread pool.
        tcpServer = new TCPServer(tcpPort, this); // Initialize the TCP server.
        rmiServer = new RMIServer(rmiPort, this); // Initialize the RMI server.
        executor.submit(tcpServer::start); // Start the TCP server.
        executor.submit(rmiServer::start); // Start the RMI server.
    }

    /**
     * Stops the server, both TCP and RMI.
     */
    public void stopServer() {
        // Stop TCP server
        if (tcpServer != null) {
            tcpServer.stop();
        }
        // Stop RMI server
        if (rmiServer != null) {
            rmiServer.stop();
        }
        // Shutdown executor service
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
        System.out.println("Server stopped.");
    }

    /**
     * Handles the arrival of a new TCP client.
     *
     * @param username The username of the new client.
     * @param out The PrintWriter for the client.
     * @throws RemoteException
     */
    public void handleNewTCPClient(String username, PrintWriter out) throws RemoteException {
        checkForGameStart(); // Check if the game can start.
    }

    /**
     * Handles the arrival of a new RMI client.
     *
     * @param username The username of the new client.
     * @param clientCallback The callback interface for the client.
     * @throws RemoteException
     */
    public void handleNewRMIClient(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        addPlayer(username); // Add the player to the list.
        rmiClientCallbacks.put(username, clientCallback); // Register the RMI callback for the client.
        checkForGameStart(); // Check if the game can start.
    }

    /**
     * Verifies if the game can start, based on the number of connections.
     *
     * @throws RemoteException
     */
    public void checkForGameStart() throws RemoteException {
        if (numConnections == numMaxConnections) {
            onModelGeneric("message:10"); // Notify clients that the match has started.
            controller.setPlayers(players); // Set the players in the controller.
            controller.initializeController(); // Initialize the controller.
            showDrawCardArea();
            showPubObj();
            String orderMessage = generatePlayerOrderMessage();
            System.out.println("checkForGameStarter in Server: " + orderMessage);
            onModelGeneric(orderMessage);
            for (Player player : players) {
                System.out.println("points in Server:" + player.getUsername() + "," + "0");
                onModelGeneric("points:" + player.getUsername() + "," + "0");
            }
            System.out.println("uscito dal for");
            gameFlow = new GameFlow(players, model, this); // Create the game flow.
            System.out.println("GameFlow started.");
            gameFlow.setMaxTurn(new AtomicInteger(numConnections * 2)); // Set the maximum number of turns.
            waitForSetObjStarter(numConnections); // Wait for all players to choose the initial card.
        } else {
            onModelGeneric("message:9");
            System.out.println("message:9");
        }
    }

    /**
     * Shows the drawable area.
     */
    public void showDrawCardArea() {
        controller.drawableArea();
    }

    /**
     * Shows the public objectives.
     */
    public void showPubObj() {
        controller.publicObj();
    }

    /**
     * Waits for all players to choose the initial card.
     *
     * @param numConnections The number of connections.
     */
    public void waitForSetObjStarter(int numConnections) {
        while (setObjStarterCount.get() < numConnections) {
            try {
                Thread.sleep(100); // Pause briefly to reduce CPU load while waiting.
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Properly handle thread interruption.
            }
        }
        System.out.println("All clients have set correctly");
        try {
            // String orderMessage = generatePlayerOrderMessage();
            // onModelGeneric(orderMessage);
            onModelGeneric("message:3"); // Notify clients that all have set their starter cards.
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a message indicating the player order.
     *
     * @return The player order message.
     */
    public String generatePlayerOrderMessage() {
        StringBuilder message = new StringBuilder("order:");
        int count = 0;
        for (String player : model.getScoreBoard().getTokens().keySet()) {
            if (count < 4) {
                Color color = model.getScoreBoard().getTokens().get(player);
                String colored = color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1).toLowerCase();
                message.append(player).append(",").append(colored).append(",");
                count++;
            }
        }
        // Fill the remaining spots with "null"
        for (int i = count; i < 4; i++) {
            message.append("null,null,");
        }
        // Remove the last comma
        message.setLength(message.length() - 1);
        System.out.println(message.toString());
        return message.toString();
    }

    /**
     * Generates a color for a player.
     *
     * @return The generated color.
     */
    public String generateColor() {
        String[] colors = new String[4]; // Array to hold up to four usernames or "null".
        // Fill the array with usernames or "null" based on the number of connected players.
        for (int i = 0; i < colors.length; i++) {
            if (i < availableColors.size()) {
                colors[i] = availableColors.get(i);
            } else {
                colors[i] = "null"; // Use "null" as a placeholder if there are fewer than four players.
            }
        }
        // Join the player names (or "nulls") with commas to create the final message.
        return String.join(",", colors);
    }

    /**
     * Increments the counter of initial card choices.
     */
    public void incrementSetObjStarterCount() {
        setObjStarterCount.incrementAndGet();
    }

    /**
     * Loads the available commands from the JSON file.
     *
     * @throws IOException
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
     * Handles model changes and notifies clients.
     *
     * @param username The username of the player.
     * @param specificMessage The specific message for the player.
     * @param generalMessage The general message for all players.
     * @throws RemoteException
     */
    public void onModelChange(String username, String specificMessage, String generalMessage) throws RemoteException {
        synchronized (this) {
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);
                } else {
                    writer.println(generalMessage);
                }
                writer.flush();
            }
            for (Map.Entry<String, RMIClientCallbackInterface> entry : rmiClientCallbacks.entrySet()) {
                try {
                    if (entry.getKey().equals(username)) {
                        entry.getValue().receiveMessage(specificMessage);
                    } else {
                        entry.getValue().receiveMessage(generalMessage);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onModelSpecific(String username, String specificMessage) {
        synchronized (this) {
            for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {
                PrintWriter writer = entry.getValue();
                if (entry.getKey().equals(username)) {
                    writer.println(specificMessage);
                    writer.flush();
                }
            }
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

    @Override
    public void onModelGeneric(String generalMessage) throws RemoteException {
        synchronized (this) {
            for (PrintWriter writer : clientWriters.values()) {
                writer.println(generalMessage);
                writer.flush();
            }
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
     * Adds a player to the list.
     *
     * @param username The username of the player.
     */
    public void addPlayer(String username) {
        synchronized (this) {
            players.add(new Player(username));
            numConnections++;
        }
    }

    /**
     * Allows a player to choose a color.
     *
     * @param username The username of the player.
     * @param color The chosen color.
     */
    public void chooseColor(String username, String color) {
        synchronized (this) {
            if (availableColors.contains(color)) {
                availableColors.remove(color);
                System.out.println(username + " has chosen the color " + color);
                System.out.println(model.getScoreBoard());
                model.getScoreBoard().addToken(username, Color.valueOf(color.toUpperCase()));
            } else {
                System.out.println("Color " + color + " not available.");
            }
        }
    }

    // Getter and Setter methods

    public Controller getController() {
        return controller;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<String> getColor() {
        return availableColors;
    }

    public List<String> getAvailableColors() {
        return availableColors;
    }

    public GameFlow getGameFlow() {
        return gameFlow;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public Model getModel() {
        return model;
    }

    public void setNumMaxConnections(int numConnections) {
        System.out.println("Con max:" + numConnections);
        this.numMaxConnections = numConnections;
    }
}
