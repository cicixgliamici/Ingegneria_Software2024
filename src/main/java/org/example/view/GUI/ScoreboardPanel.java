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
        MyDrawImage myDrawImage = new MyDrawImage("src/main/resources/images/001.png", 0, 600);
        //Components
        BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/plateau.png"));
        Icon icon1 = new ImageIcon(logo1);
        //JLabel scoreboard = new JLabel(icon1);

        BufferedImage logo = ImageIO.read(new File("src/main/resources/images/001.png"));
        Icon icon = new ImageIcon(logo);
        //JLabel myDrawImage = new JLabel(icon);


        //Layout
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 1;
        gbc.gridx = 0;

        gbc.weighty = 0.25;
        gbc.weightx = 0.05;
        gbc.fill = GridBagConstraints.BOTH;

        add(myDrawImage, gbc);

        GridBagConstraints gbcScoreboard = new GridBagConstraints();

        gbcScoreboard.gridy = 0;
        gbcScoreboard.gridx = 0;

        gbcScoreboard.weighty = 0.9;
        gbcScoreboard.weightx = 1;

        gbcScoreboard.fill = GridBagConstraints.BOTH;

        add(scoreboard, gbcScoreboard);
    }
}
