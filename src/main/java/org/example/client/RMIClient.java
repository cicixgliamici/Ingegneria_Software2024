package org.example.client;

import org.example.server.rmi.RMIClientCallbackInterface;
import org.example.server.rmi.RMIServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    private RMIServerInterface rmiServer;

    /**
     * Constructor for the RMIClient class.
     * Sets up the RMI registry and looks up the RMI server interface.
     * Also, initializes the RMI client callback.
     *
     * @param host The IP address or hostname of the RMI server.
     * @param port The port number of the RMI server.
     * @param client A reference to the Client instance.
     */
    public RMIClient(String host, int port, Client client) {
        try {
            Registry registry = LocateRegistry.getRegistry(host, port + 1); // Use the RMI port
            rmiServer = (RMIServerInterface) registry.lookup("RMIServer");
            RMIClientCallbackInterface callback = new RMIClientCallbackImpl(client);
            client.setRmiClientCallback(callback); // Save the callback in the client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects the client to the RMI server with the provided username and callback.
     *
     * @param username The username of the client.
     * @param callback The callback interface for receiving messages from the server.
     */
    public void connect(String username, RMIClientCallbackInterface callback) {
        try {
            rmiServer.connect(username, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the chosen color to the RMI server for the specified username.
     *
     * @param username The username of the client.
     * @param color The color chosen by the client.
     */
    public void chooseColor(String username, String color) {
        try {
            rmiServer.chooseColor(username, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
