package org.example.client;

import org.example.server.rmi.RMIClientCallbackInterface;
import org.example.server.rmi.RMIServerInterface;
import org.example.view.View;
import org.example.view.ViewTUI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * The RMIClient class is responsible for managing the client's connection to the server using RMI (Remote Method Invocation).
 * It handles the initialization, connection, and communication with the server, as well as interpreting messages using the view.
 */
public class RMIClient {
    private String ip;  // IP address of the server
    private int port;  // Port number for the server
    private View view;  // View interface for interacting with the user
    private RMIClientCallbackInterface rmiClientCallback;  // RMI callback interface

    /**
     * Constructor for RMIClient.
     *
     * @param ip   The IP address of the server.
     * @param port The port number of the server.
     * @param view The view interface for interacting with the user.
     */
    public RMIClient(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }

    /**
     * Starts the RMI client and connects to the RMI server.
     */
    public void startRMIClient() {
        try {
            // Locate the RMI registry at the specified IP address and port
            Registry registry = LocateRegistry.getRegistry(ip, port + 1);

            // Create an instance of RMIClientCallbackImpl to handle server callbacks
            rmiClientCallback = new RMIClientCallbackImpl(this);
            System.out.println("ciao");
            // Lookup the RMIServerInterface in the registry
            RMIServerInterface rmiServer = (RMIServerInterface) registry.lookup("RMIServer");
            System.out.println("ciao1");
            // Scanner to read user input from the console
            Scanner stdin = new Scanner(System.in);

            // Prompt the user to enter their username
            System.out.println("Enter your username:");
            String username = stdin.nextLine();

            // Connect to the server using the provided username and callback interface
            String response = rmiServer.connect(username, rmiClientCallback);
            System.out.println(response);  // Print the server's response to the console
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace in case of an exception
        }
    }

    /**
     * Handles incoming messages from the server.
     *
     * @param message The message received from the server.
     */
    public void handleMessage(String message) {
        synchronized (this) {
            // If the message indicates the match has started, initialize the view as TUI
            if (message.equals("Match started")) {
                System.out.println(message);
                view = new ViewTUI();
            }
            // If the view is not null, interpret the message using the view
            if (view != null) {
                view.Interpreter(message);
            } else {
                // If the view is null, print the message to the console
                System.out.println(message);
            }
        }
    }

    /**
     * Sets the RMI client callback interface.
     *
     * @param rmiClientCallback The RMI client callback interface.
     */
    public void setRmiClientCallback(RMIClientCallbackInterface rmiClientCallback) {
        this.rmiClientCallback = rmiClientCallback;
    }
}
