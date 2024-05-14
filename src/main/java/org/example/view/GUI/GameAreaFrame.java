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

    private JButton button1;
    private JButton button2;

    public GameAreaFrame() throws IOException {
        super("Codex Naturalis");
        setSize(1300, 840);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);
        setLayout(new GridBagLayout());
        button1 = new JButton("negro");
        button2 = new JButton("frocio");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx=0.2;

        add(button1, gbc);

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
        gbc.weightx=0.8;
        gbc.fill = GridBagConstraints.BOTH;

        add(gameAreaPanel, gbc);

        gbc.gridx=2;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx=0.2;
        gbc.fill = GridBagConstraints.NONE;
        add(button2, gbc);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    }
