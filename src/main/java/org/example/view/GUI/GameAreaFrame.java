package org.example.view.GUI;

import javax.swing.*;
import java.awt.*;

public class GameAreaFrame extends JFrame {
    GameAreaPanel gameAreaPanel;

    public GameAreaFrame(){
        super("Codex Naturalis");
        setSize(810, 660);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
