package org.example.client;

import org.example.view.View;
import org.example.view.ViewGUI;
import org.example.view.ViewTUI;

import java.util.Scanner;

public class ClientPortSelection {

    public static void main(String[] args, int choice) {
        System.out.println("IP: digit one casual letter for local ip");
        Scanner in = new Scanner(System.in);
        String ip = in.nextLine();
        int port = -1;
        if(!ip.contains(".")){
            ip = "127.0.0.1";
            port = 50000;
        }


        boolean first = true;
        while (port <= 1023 || port >= 65535){
            if(first){
                System.out.println("PORT: ");
                first = false;
            }

            String s = in.nextLine();
            try {
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                port = 50000;
            }

            if(port <= 1023 || port >= 65535)
                System.out.println("Write a valid port number from 1024 to 65535");

        }
        View view = null;
        if (choice==0){
            view = new ViewTUI();
        }
        else {
            view = new ViewGUI();
        }
        Client clientCLI = new Client(ip, port, view);
        clientCLI.startClient();
    }
}
