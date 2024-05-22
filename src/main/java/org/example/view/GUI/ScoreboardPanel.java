package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScoreboardPanel extends JPanel {


    public ScoreboardPanel() throws IOException {
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(382, 1000));
        MyDrawImage scoreboard = new MyDrawImage("src/main/resources/images/plateau.png", 0,0);

        //MyDrawImage myDrawImage = new MyDrawImage("C:\\Users\\jamie\\OneDrive\\Desktop\\001.png", 0, 600);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 1;
        gbc.gridx = 0;

        gbc.weighty = 0.25;
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.BOTH;

        //add(myDrawImage, gbc);

        GridBagConstraints gbca = new GridBagConstraints();

        gbca.gridy = 0;
        gbca.gridx = 0;

        gbca.weighty = 0.9;
        gbca.weightx = 1;

        gbca.fill = GridBagConstraints.BOTH;

        add(scoreboard, gbca);
    }
}
