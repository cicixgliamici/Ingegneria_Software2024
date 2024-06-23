

package org.example.server;

import java.rmi.RemoteException;

/**
 * The ModelChangeListener interface defines the methods that must be implemented
 * by any class that wants to be notified of changes to the model.
 * This is typically used in an MVC (Model-View-Controller) architecture where
 * the view or controller needs to be updated when the model changes.
 */
public interface ModelChangeListener {

    /**
     * Called when the model changes.
     * This method notifies the listener of a change in the model that involves a specific player
     * and also provides a general message for other players.
     *
     * @param username The username of the player who triggered the model change.
     * @param specificMessage The specific message for the player who triggered the change.
     * @param generalMessage The general message for all other players.
     * @throws RemoteException If an RMI-related error occurs.
     */
    void onModelChange(String username, String specificMessage, String generalMessage) throws RemoteException;

    /**
     * Called to send a specific message to a specific player.
     * This method notifies the listener with a message intended for a single player.
     *
     * @param username The username of the player to whom the message is addressed.
     * @param specificMessage The specific message for the player.
     * @throws RemoteException If an RMI-related error occurs.
     */
    void onModelSpecific(String username, String specificMessage) throws RemoteException;

    /**
     * Called to send a general message to all players.
     * This method notifies the listener with a message intended for all players.
     *
     * @param generalMessage The general message for all players.
     * @throws RemoteException If an RMI-related error occurs.
     */
    void onModelGeneric(String generalMessage) throws RemoteException;
}
