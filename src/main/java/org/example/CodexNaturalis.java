package org.example;

import org.example.client.ClientPortSelection;
import org.example.server.PortSelection;
import org.example.view.View;
import org.example.view.ViewTUI;


import java.util.Scanner;

/** First class runned when you start the CodexNaturalis.jar
*   So that you can choose to run a server or a client (TUI or GUI)
*/

public class CodexNaturalis {
    public static void main(String[] args) {
        System.out.println("WELCOME! What do you want to launch?");
        System.out.println("1. SERVER\n2. CLIENT (TUI INTERFACE)\n3. CLIENT (GUI INTERFACE)");
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.println("Insert your choice: ");
            input = scanner.nextLine();
        }while (!(input.equals("1") || input.equals("2") || input.equals("3")));
        switch (input){
            case "1":
                PortSelection.main(null);
                break;
            case "2":
                ClientPortSelection.main(null, 0);
                break;
            case "3":
                ClientPortSelection.main(null, 1);
                break;
            default:
                System.out.println("invalid input!\n");
        }
    }

}



