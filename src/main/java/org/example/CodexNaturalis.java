package org.example;

import org.example.client.ClientPortSelection;
import org.example.server.PortSelection;

import java.util.Scanner;

/**
 * The entry point for the CodexNaturalis application.
 * Allows the user to choose to run a server or a client with either a TUI or GUI interface.
 */
public class CodexNaturalis {

    /**
     * Main method to start the application.
     *
     * @param args Command line arguments.
     * @throws Exception If an error occurs during execution.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("WELCOME! What do you want to launch?");
        System.out.println("1. SERVER\n2. CLIENT (TUI INTERFACE)\n3. CLIENT (GUI INTERFACE)");

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Insert your choice: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    PortSelection.main(null);
                    break;
                case "2":
                    selectClientMode(2);
                    break;
                case "3":
                    selectClientMode(3);
                    break;
                default:
                    System.out.println("Insert a valid number!");
                    break;
            }
        } while (!input.equals("1") && !input.equals("2") && !input.equals("3"));
    }

    /**
     * Prompts the user to select the client connection mode (TCP or RMI) and starts the client with the selected view.
     *
     * @param view The type of client view (2 for TUI, 3 for GUI).
     * @throws Exception If an error occurs during execution.
     */
    private static void selectClientMode(int view) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Press 0 to start a TCP Server");
        int mode;
        while (true) {
            String modeInput = in.nextLine();
            try {
                mode = Integer.parseInt(modeInput);
                if (mode == 0) {
                    break;
                } else {
                    System.out.println("Invalid mode. Please press 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid mode. Please press 0.");
            }
        }
        ClientPortSelection clientPortSelection = new ClientPortSelection();
        clientPortSelection.main(null, mode, view);  // This will set IP and port and start the client with its view
    }
}
