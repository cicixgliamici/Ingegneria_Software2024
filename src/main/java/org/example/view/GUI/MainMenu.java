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

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);

        boxMenu = new BoxMenu(){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/background.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };

        setLayout(new BorderLayout()); //layout manager che si occuperà di posizionare i componenti

        add(boxMenu, BorderLayout.CENTER);

        pack();
        setSize(810, 625);
        setLocationRelativeTo(null); // visualizzare la finestra al centro dello schermo
        setResizable(false); //non permette di ridimensionare la finestra
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
