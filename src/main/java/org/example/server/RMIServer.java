package org.example.server;

import org.example.server.rmi.RMIServerImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class representing the RMI server component of the application.
 */
public class RMIServer {
    private int port;  // Port number for the RMI server
    private Server mainServer;  // Reference to the main server instance
    private RMIServerImpl rmiServerImpl;  // Implementation of the RMI server interface

    /**
     * Constructor to initialize the RMI server with a specified port and main server instance.
     *
     * @param port The port number on which the RMI server will listen.
     * @param mainServer The main server instance to which this RMI server is linked.
     */
    public RMIServer(int port, Server mainServer) {
        this.port = port;
        this.mainServer = mainServer;
    }

    /**
     * Starts the RMI server.
     * Creates an RMI registry on the specified port and binds the RMIServerImpl instance to it.
     */
    public void start() {
        try {
            // Create an instance of the RMI server implementation, passing the main server reference
            rmiServerImpl = new RMIServerImpl(mainServer);

            // Create an RMI registry on the specified port
            Registry registry = LocateRegistry.createRegistry(port);

            // Bind the RMIServerImpl instance to the registry with the name "RMIServer"
            registry.bind("RMIServer", rmiServerImpl);

            // Print a message indicating that the RMI server has started
            System.out.println("RMI server started on port " + port);
        } catch (Exception e) {
            // Print the stack trace if an exception occurs during the server startup
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to all connected RMI clients.
     *
     * @param message The message to send to all clients.
     */
    public void sendToAllClients(String message) {
        // Delegate the message sending to the RMIServerImpl instance
        rmiServerImpl.sendToAllClients(message);
    }
}
