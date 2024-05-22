package org.example.view.GUI;

import org.example.view.GUI.mainmenu.MainMenu;

import java.io.IOException;

public class GuiClient {
    public static void startClient(int mode) throws IOException {
        new MainMenu(mode);
    }

    public static void main(String[] args, int mode) throws IOException {
        startClient(mode);
        new GameAreaFrame("jima", "Red", "2");
    }


}
