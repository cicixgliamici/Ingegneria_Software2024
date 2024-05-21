package org.example.client;

import org.example.view.View;

/**
 * The Client class is responsible for initializing and starting the client-side application.
 * It connects to the server using either TCP or RMI based on the user's choice.
 */
public class Client {
    private String ip;  // IP address of the server
    private int port;  // Port number for the server
    private View view;  // View interface for interacting with the user

    /**
     * Constructor for the Client class.
     *
     * @param ip The IP address of the server.
     * @param port The port number of the server.
     * @param view The view interface for interacting with the user.
     */
    public Client(String ip, int port, View view) {
        this.ip = ip;
        this.port = port;
        this.view = view;
    }

    /**
     * Starts the client application in TUI (Text User Interface) mode.
     * The user can select the connection mode: 0 for TCP, 1 for RMI.
     *
     * @param mode The connection mode selected by the user.
     */
    public void startClient(int mode) {
        if (mode == 0) {
            // If the mode is 0, start the TCP client.
            // A new instance of TCPClient is created, initialized with the server's IP, port, and view.
            // The startTCPClient method is called to establish the TCP connection and handle communication.
            new TCPClient(ip, port, view).startTCPClient();
        } else if (mode == 1) {
            // If the mode is 1, start the RMI client.
            // A new instance of RMIClient is created, initialized with the server's IP, port, and view.
            // The startRMIClient method is called to establish the RMI connection and handle communication.
            new RMIClient(ip, port, view).startRMIClient();
        } else {
            // If an invalid mode is provided, print an error message.
            System.out.println("Invalid mode. Please select 0 for TCP or 1 for RMI.");
        }
    }
}
