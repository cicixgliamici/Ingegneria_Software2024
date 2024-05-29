package org.example.client;

import org.example.server.rmi.RMIClientCallbackInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * The RMIClientCallbackImpl class implements the RMIClientCallbackInterface
 * and handles the communication between the RMI server and the RMI client.
 * It provides methods for receiving messages from the server and asking the client for input.
 */
public class RMIClientCallbackImpl extends UnicastRemoteObject implements RMIClientCallbackInterface {
    private final RMIClient client;  // Reference to the RMI client

    /**
     * Constructor for RMIClientCallbackImpl.
     *
     * @param client The RMI client.
     * @throws RemoteException If there is an issue with remote communication.
     */
    public RMIClientCallbackImpl(RMIClient client) throws RemoteException {
        this.client = client;
    }

    /**
     * Receives a message from the server and delegates it to the RMI client for handling.
     *
     * @param message The message received from the server.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public void receiveMessage(String message) throws RemoteException {
        client.handleMessage(message);
    }

    /**
     * Asks the client to enter the maximum number of players.
     *
     * @return The maximum number of players entered by the client.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public int askForMaxPlayers() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the maximum number of players (1-4):");
        return Integer.parseInt(scanner.nextLine());  // Read and return the user's input
    }

    /**
     * Asks the client to choose a color from the given message.
     *
     * @param message The message prompting the client to choose a color.
     * @return The color chosen by the client.
     * @throws RemoteException If there is an issue with remote communication.
     */
    @Override
    public String askForColor(String message) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);  // Display the message to the client
        return scanner.nextLine();  // Read and return the user's input
    }
}