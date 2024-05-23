
package org.example.server.rmi;

import org.example.server.Server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The RMIServerImpl class is an implementation of the RMIServerInterface.
 * This class handles the RMI server-side logic, managing client connections
 * and facilitating communication between the server and clients.
 */
public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {
    private final Server server;  // Reference to the main server object
    private final Map<String, RMIClientCallbackInterface> clientCallbacks = new ConcurrentHashMap<>();  // Map of client callbacks keyed by username
    private int numConnections = 0;  // Number of active connections

    /**
     * Constructor for RMIServerImpl.
     * Initializes the server reference and exports the object.
     *
     * @param server The main server object.
     * @throws RemoteException If an RMI-related error occurs.
     */
    public RMIServerImpl(Server server) throws RemoteException {
        this.server = server;
    }

    /**
     * Handles a new client connection.
     * Adds the client to the server, manages the number of connections, and handles color selection.
     * It's the same for the TCP
     * @param username The username of the connecting client.
     * @param clientCallback The callback interface for communicating with the client.
     * @return A message indicating the connection status.
     * @throws RemoteException If an RMI-related error occurs.
     */
    @Override
    public String connect(String username, RMIClientCallbackInterface clientCallback) throws RemoteException {
        synchronized (server) {
            // Check if the username is already taken
            if (clientCallbacks.containsKey(username)) {
                return "Username already taken. Please reconnect with a different username.";
            }
            // If this is the first client, ask for the number of players
            if (server.getPlayers().isEmpty() && numConnections == 0) {
                server.numMaxConnections = clientCallback.askForMaxPlayers();
            }
            // Check if the number of connections is within the limit
            if (numConnections < server.numMaxConnections) {
                server.addPlayer(username);  // Add the player to the server
                clientCallbacks.put(username, clientCallback);  // Add the client callback to the map
                numConnections++;  // Increment the number of connections
                clientCallback.receiveMessage("Connection successful");
                // Handle the color selection process
                String chosenColor;
                do {
                    chosenColor = clientCallback.askForColor("Choose a color from the following list: " + String.join(", ", server.getAvailableColors()));
                    if (!server.getAvailableColors().contains(chosenColor)) {
                        clientCallback.receiveMessage("Color not available. Choose a color from the following list: " + String.join(", ", server.getAvailableColors()));
                    }
                } while (!server.getAvailableColors().contains(chosenColor));
                server.chooseColor(username, chosenColor);  // Assign the chosen color to the player
                clientCallback.receiveMessage("You have chosen the color " + chosenColor);
                // If the number of connections reaches the maximum, start the game
                if (numConnections == server.numMaxConnections) {
                    server.onModelGeneric("Match started");
                    server.getController().setPlayers(server.getPlayers());
                    server.getController().initializeController();
                    server.waitForSetObjStarter(numConnections);  // Start the game flow
                }
                return "Connection successful";
            } else {
                return "Connection failed: maximum number of players reached.";
            }
        }
    }

    /**
     * Handles the color selection for a client.
     * Delegates the color selection to the server.
     *
     * @param username The username of the client.
     * @param color The color chosen by the client.
     * @throws RemoteException If an RMI-related error occurs.
     */
    @Override
    public void chooseColor(String username, String color) throws RemoteException {
        server.chooseColor(username, color);
    }

    /**
     * Sends a message to all connected clients.
     *
     * @param message The message to send to all clients.
     */
    public void sendToAllClients(String message) {
        for (RMIClientCallbackInterface callback : clientCallbacks.values()) {
            try {
                callback.receiveMessage(message);  // Send the message to each client
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
