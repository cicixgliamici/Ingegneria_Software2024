package org.example.client;

import org.example.view.View;
import org.example.view.ViewGUI;
import org.example.view.ViewTUI;

import java.util.Scanner;

public class ClientPortSelection {
    private String ip;
    private int port;

    public void main(String[] args, int mode) {
        System.out.println("IP: digit one casual letter for local ip");
        Scanner in = new Scanner(System.in);
        ip = in.nextLine();
        port = -1;
        if (!ip.contains(".")) {
            ip = "127.0.0.1";
            port = 50000;
        }
        boolean first = true;
        while (port <= 1023 || port >= 65535) {
            if (first) {
                System.out.println("PORT: ");
                first = false;
            }
            String s = in.nextLine();
            try {
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                port = 50000;
            }
            if (port <= 1023 || port >= 65535)
                System.out.println("Write a valid port number from 1024 to 65535");
        }

        View view;
        if (mode == 0) {
            view = new ViewTUI();
        } else {
            view = new ViewGUI();
        }

        Client client = new Client(ip, port, view);
        client.startClientTUI(mode);
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
