package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame{

    BoxMenu boxMenu;

    public MainMenu() throws IOException {
        super("Codex Naturalis");

        boxMenu = new BoxMenu();
        JLabel background = new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\acall\\Documents\\Docuni\\Grafiche Codex\\backround.png"))));

        setLayout(new BorderLayout()); //layout manager che si occuperà di posizionare i componenti

        add(boxMenu, BorderLayout.CENTER);
        add(background);

        setSize(800, 600);
        setLocationRelativeTo(null); // visualizzare la finestra al centro dello schermo
        setResizable(false); //non permette di ridimensionare la finestra
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
