package org.example;

import org.example.client.Client;
import org.example.client.ClientPortSelection;
import org.example.server.PortSelection;
import org.example.view.GUI.GuiClient;
import org.example.view.View;
import org.example.view.ViewTUI;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/** First class run when you start the CodexNaturalis.jar
 *   So that you can choose to run a server or a client (TUI or GUI)
 */

public class CodexNaturalis {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("WELCOME! What do you want to launch?");
        System.out.println("1. SERVER\n2. CLIENT (TUI INTERFACE)\n3. CLIENT (GUI INTERFACE)");
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.println("Insert your choice: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    //called from Codex Naturalis, go to PortSelection
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
        } while (!(input.equals("1") || input.equals("2") || input.equals("3")));
    }

    private static void selectClientMode(int view) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Select connection mode: 0 for TCP, 1 for RMI");
        int mode;
        while (true) {
            String modeInput = in.nextLine();
            try {
                mode = Integer.parseInt(modeInput);
                if (mode == 0 || mode == 1) {
                    break;
                } else {
                    System.out.println("Invalid mode. Please select 0 for TCP or 1 for RMI.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid mode. Please select 0 for TCP or 1 for RMI.");
            }
        }

        ClientPortSelection clientPortSelection = new ClientPortSelection();
        clientPortSelection.main(null, mode, view);  // This will set IP and port and start the client
    }
}
