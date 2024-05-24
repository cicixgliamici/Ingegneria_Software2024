package org.example.view.gui;

import org.example.view.View;
import org.example.view.gui.mainmenu1.MainMenu;

import java.io.IOException;

public class GuiClient {
    public static void startClient(int mode, View view) throws IOException {
        new MainMenu(mode, view);
    }
}