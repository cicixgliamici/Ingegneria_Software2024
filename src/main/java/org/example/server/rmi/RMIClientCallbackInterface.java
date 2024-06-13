
package org.example.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMIClientCallbackInterface is an RMI (Remote Method Invocation) interface
 * that defines the methods the server can call on the client.
 * This interface is used for server-to-client communication in an RMI-based architecture.
 */
public interface RMIClientCallbackInterface extends Remote {

    /**
     * Sends a message to the client.
     * This method allows the server to communicate with the client by sending messages.
     *
     * @param message The message to send to the client.
     * @throws RemoteException If a communication-related exception occurs during the remote method call.
     */
    void receiveMessage(String message) throws RemoteException;

    /**
     * Asks the client for the maximum number of players.
     * This method prompts the client to input the maximum number of players for the game.
     *
     * @return The maximum number of players input by the client.
     * @throws RemoteException If a communication-related exception occurs during the remote method call.
     */
    int askForMaxPlayers() throws RemoteException;

    /**
     * Asks the client to choose a color.
     * This method prompts the client to choose a color from the available options.
     *
     * @param message The message to display to the client, typically containing the list of available colors.
     * @return The color chosen by the client.
     * @throws RemoteException If a communication-related exception occurs during the remote method call.
     */
    String askForColor(String message) throws RemoteException;
}