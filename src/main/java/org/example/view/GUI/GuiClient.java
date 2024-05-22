package org.example.view.GUI;

import org.example.view.GUI.mainmenu.MainMenu;
import org.example.view.GUI.mainmenu.SetInitialGame;

import java.io.IOException;

public class GuiClient {
    public static void main(String[] args) throws IOException {
        //new MainMenu();
        //new GameRulesFrame();
        //new GameAreaFrame("jima", "Red", "2");
        new SetInitialGame("jima");
    }
}