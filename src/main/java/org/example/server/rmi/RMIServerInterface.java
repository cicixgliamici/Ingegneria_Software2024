
package org.example.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMIServerInterface defines the methods that the RMI server will expose to clients.
 * This interface extends the Remote interface, indicating that its methods can be called
 * from another Java virtual machine.
 */
public interface RMIServerInterface extends Remote {

    /**
     * Connects a client to the RMI server.
     * This method is called by the client to establish a connection with the server.
     * It registers the client with the server and returns a message indicating the connection status.
     *
     * @param username The username of the client attempting to connect.
     * @param clientCallback The callback interface for communicating with the client.
     * @return A message indicating the connection status (e.g., "Connection successful" or "Username already taken").
     * @throws RemoteException If an RMI-related error occurs.
     */
    String connect(String username, RMIClientCallbackInterface clientCallback) throws RemoteException;

    /**
     * Allows a client to choose a color.
     * This method is called by the client to select a color for their player.
     * The server checks the availability of the chosen color and assigns it to the client if available.
     *
     * @param username The username of the client choosing a color.
     * @param color The color chosen by the client.
     * @throws RemoteException If an RMI-related error occurs.
     */
    void chooseColor(String username, String color) throws RemoteException;
}
