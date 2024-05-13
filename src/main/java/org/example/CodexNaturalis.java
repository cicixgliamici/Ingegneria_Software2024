package org.example;

import org.example.client.ClientPortSelection;
import org.example.server.PortSelection;
import org.example.view.GUI.GuiClient;
import org.example.view.View;
import org.example.view.ViewTUI;


import java.io.IOException;
import java.util.Scanner;

/** First class runned when you start the CodexNaturalis.jar
*   So that you can choose to run a server or a client (TUI or GUI)
*/

public class CodexNaturalis {
    public static void main(String[] args) throws IOException {
        System.out.println("WELCOME! What do you want to launch?");
        System.out.println("1. SERVER\n2. CLIENT (TUI INTERFACE)\n3. CLIENT (GUI INTERFACE)");
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Insert your choice: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    PortSelection.main(null);
                    break;
                case "2":
                    ClientPortSelection.main(null, 0);
                    break;
                case "3":
                    GuiClient.main(null);
                    break;
                default:
                    System.out.println("Insert a valid number!");
                    break;
            }
        } while (!(input.equals("1") || input.equals("2") || input.equals("3"))) ;
    }
}



