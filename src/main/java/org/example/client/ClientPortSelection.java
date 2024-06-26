package org.example.client;

import org.example.view.ViewGUI;
import org.example.view.gui.GuiClient;
import org.example.view.View;
import org.example.view.ViewTUI;

import java.util.Scanner;

/**
 * The ClientPortSelection class is responsible for managing the selection of IP address and port
 * for connecting to the server, as well as initializing the client with the appropriate view mode.
 */
public class ClientPortSelection {
    private String ip;  // IP address of the server
    private int port;  // Port number for the server

/**
 * Main method for starting the client with IP and port selection.
 *
 * This method initializes and starts the client based on the user's selection of interface type
 * (Text User Interface or Graphical User Interface) and the chosen view type. For the TUI, it prompts
 * the user for IP and port, ensuring valid inputs. For the GUI, it directly starts the client with the GUI settings.
 *
 * @param args Command-line arguments (not used in this method).
 * @param mode The mode of the client: 0 for TUI (Text User Interface), 1 for GUI (Graphical User Interface).
 * @param ChosenView The view type chosen: 2 for TUI, 3 for GUI.
 * @throws Exception if an error occurs during client initialization or startup.
 */
    public void main(String[] args, int mode,int ChosenView) throws Exception {
        View view;
        if(ChosenView==2){
            view = new ViewTUI();
            System.out.println("IP: digit one casual letter for local ip");
            Scanner in = new Scanner(System.in);
            ip = in.nextLine();  // Read the IP address input
            port = -1;  // Initialize the port to an invalid value
            // If the IP address does not contain a dot, assume it's a local connection
            if (!ip.contains(".")) {
                ip = "127.0.0.1";  // Default to localhost
                port = 50000;  // Default port for localhost
            }
            boolean first = true;
            // Loop until a valid port number is provided
            while (port <= 1023 || port >= 65535) {
                if (first) {
                    System.out.println("PORT: ");  // Prompt for port input
                    first = false;
                }
                String s = in.nextLine();
                try {
                    port = Integer.parseInt(s);  // Try to parse the port number
                } catch (NumberFormatException e) {
                    port = 50000;  // Default port if parsing fails
                }
                // Check if the port is within the valid range
                if (port <= 1023 || port >= 65535) {
                    System.out.println("Write a valid port number from 1024 to 65535");
                }
            }
            // Create a new client instance with the selected IP, port, and view
            Client client = new Client(ip, port, view);
            client.startClient(mode);  // Start the client with the specified mode
        }
        else if(ChosenView==3){
            view = new ViewGUI();
            GuiClient.startClient(mode, view);
        }
    }

    /**
     * Getter for the IP address.
     *
     * @return The IP address.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Getter for the port number.
     *
     * @return The port number.
     */
    public int getPort() {
        return port;
    }
}
