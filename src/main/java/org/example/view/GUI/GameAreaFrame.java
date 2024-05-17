package org.example.view.GUI;



import org.example.view.GUI.mainmenu.BoxMenu;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameAreaFrame extends JFrame {
    GameAreaPanel gameAreaPanel;
    ScoreboardPanel scoreboardPanel;
    Chat chat;

    public GameAreaFrame() throws IOException {
        super("Codex Naturalis");
        setSize(1300, 830);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);
        setLayout(new GridBagLayout());



        GridBagConstraints gbc = new GridBagConstraints();

        scoreboardPanel = new ScoreboardPanel(){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/plateau.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };

        gbc.gridx=0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx=0.385;
        gbc.fill = GridBagConstraints.BOTH;
        add(scoreboardPanel, gbc);

        gameAreaPanel = new GameAreaPanel(){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/gamearea.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };
        gbc.gridx=1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx=0.58;
        gbc.fill = GridBagConstraints.BOTH;

        add(gameAreaPanel, gbc);

        gbc.gridx=2;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx=0.15;
        gbc.fill = GridBagConstraints.BOTH;
        chat = new Chat();
        add(chat, gbc);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
