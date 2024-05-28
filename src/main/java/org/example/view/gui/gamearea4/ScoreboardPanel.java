package org.example.view.gui.gamearea4;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScoreboardPanel extends JPanel {
    private JLabel scoreboard;
    private JLabel objective1;
    private JLabel objective2;
    public ScoreboardPanel() throws IOException {
        setLayout(new GridBagLayout());
        BufferedImage logo = ImageIO.read(new File("src/main/resources/images/plateau.png"));
        Icon icon = new ImageIcon(logo);
        scoreboard = new JLabel(icon);

        BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/mid/back/081.png"));
        Icon icon1 = new ImageIcon(logo1);
        objective1 = new JLabel(icon1);

        BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/mid/back/081.png"));
        Icon icon2 = new ImageIcon(logo2);
        objective2 = new JLabel(icon2);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;

        gbc.weighty = 0.8;
        gbc.weightx = 1;

        gbc.gridwidth = 2;

        add(scoreboard, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;

        add(objective1, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;

        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;

        add(objective2, gbc);
    }


}