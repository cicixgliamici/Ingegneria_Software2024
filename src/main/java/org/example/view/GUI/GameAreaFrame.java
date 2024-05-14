package org.example.view.GUI;



import org.example.view.GUI.mainmenu.BoxMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameAreaFrame extends JFrame {
    GameAreaPanel gameAreaPanel;
    private JPanel background;

    public GameAreaFrame() throws IOException {
        super("Codex Naturalis");
        setSize(810, 660);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);


        gameAreaPanel = new GameAreaPanel(){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/gamearea.jpg")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };

        setLayout(new BorderLayout());
        add(gameAreaPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    }
