package org.example.view.gui;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.ViewGUI;
import org.example.view.gui.gamearea4.GameAreaFrame;
import org.example.view.gui.gamerules.GameRulesFrame;

import java.io.IOException;

public class TestGUI {
    public static void main(String[] args) throws IOException {
        ViewGUI view = new ViewGUI();
        TCPClient tcpClient=  new TCPClient("127.0.0.1", 50000, view );
        //new SelectObjStarter();
        new GameAreaFrame(tcpClient, "jima", "Red", "2");
        //new GameRulesFrame();
    }
}