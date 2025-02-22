
package org.example.server;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Main class of the server, started when you select option 1 in CodexNaturalis.java.
 * This class handles the selection of TCP and RMI ports for the server to listen on.
 * It ensures that valid ports are chosen and that TCP and RMI ports do not overlap.
 */
public class PortSelection {


    /**
 * Main method to start the server with specified TCP and RMI ports.
 *
 * This method prompts the user to insert the TCP port number or type a letter for the default value (50000).
 * It then initializes and starts the server with the specified ports. After starting the server, it attempts
 * to find and print the local IP address of the machine.
 *
 * @param args command-line arguments (not used in this method)
 */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tcpPort;
        int rmiPort;

        // Prompt the user to insert the TCP port number or type a letter for the default value (50000).
        System.out.println("Insert TCP port number or digit one casual letter for default (50000)");
        tcpPort = getValidPort(in, "TCP");


        // Cleanup scanner resource
        in.close();

        // Initialize and start the server with the specified ports.
        try {
            Server server = new Server(tcpPort);
            server.startServer();
            try {
                boolean ipFound = false;
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements() && !ipFound) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                            System.out.println("IP Address: " + inetAddress.getHostAddress());
                            ipFound = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a specified port is already in use.
     *
     * @param port The port number to check.
     * @return true if the port is in use, false otherwise.
     * This helps ensure that the server does not attempt to bind to an already occupied port.
     */
    private static boolean isPortInUse(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            return false; // If the port was successfully opened, it's not in use.
        } catch (IOException e) {
            return true; // If an IOException was thrown, the port is already in use.
        }
    }

    /**
     * Prompts the user to input a valid port number and validates it.
     * If the input is not a number or outside the valid range (1024-65535),
     * a default port is assigned (50000 for TCP, 50001 for RMI).
     *
     * @param in The Scanner instance for user input.
     * @param portType The type of port (TCP or RMI) to provide a suitable default value.
     * @return A valid port number.
     */
    private static int getValidPort(Scanner in, String portType) {
        int port;
        while (true) {
            String input = in.nextLine();
            try {
                port = Integer.parseInt(input);  // Try to parse the input as an integer.
            } catch (NumberFormatException e) {
                // Assign default port if input is not a valid number.
                port = portType.equalsIgnoreCase("TCP") ? 50000 : 50001;
            }
            // Check if the port is within the valid range and not in use.
            if (port >= 1024 && port <= 65535 && !isPortInUse(port)) {
                break;  // Valid port found.
            } else if (isPortInUse(port)) {
                System.out.println("Port is already in use. Please choose a different port.");
            } else {
                System.out.println("Write a valid port number from 1024 to 65535");
            }
        }
        return port;
    }
}
