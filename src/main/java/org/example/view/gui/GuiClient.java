package org.example.view.gui;

import org.example.view.View;
import org.example.view.gui.mainmenu1.MainMenu;

import java.io.IOException;

/**
 * The GuiClient class provides a method to start the graphical user interface (GUI) client.
 */
public class GuiClient {

    /**
     * Starts the GUI client by creating a new instance of MainMenu.
     *
     * @param mode The connection mode (0 for TCP, 1 for RMI).
     * @param view The view object containing the game state and logic.
     * @throws IOException If an error occurs while initializing the GUI components.
     */
    public static void startClient(int mode, View view) throws IOException {
        new MainMenu(mode, view);
    }
}
