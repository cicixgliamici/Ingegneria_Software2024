package org.example.view.gui.gamearea4;

import org.example.view.View;

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
    public ScoreboardPanel(View view) throws IOException {
        setLayout(new GridBagLayout());
        BufferedImage logo = ImageIO.read(new File("src/main/resources/images/plateau.png"));
        Icon icon = new ImageIcon(logo);
        scoreboard = new JLabel(icon);
        if(view.getPublicObjectives().get(0) < 100) {
            System.out.println(view.getPublicObjectives().get(0));
            BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
            Icon icon1 = new ImageIcon(logo1);
            objective1 = new JLabel(icon1);
        }
        else if(view.getPublicObjectives().get(0) > 100){
            System.out.println(view.getPublicObjectives().get(0));
            BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
            Icon icon1 = new ImageIcon(logo1);
            objective1 = new JLabel(icon1);
        }
        if(view.getPublicObjectives().get(1) < 100) {
            System.out.println(view.getPublicObjectives().get(1));
            BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
            Icon icon2 = new ImageIcon(logo2);
            objective2 = new JLabel(icon2);
        }
        else if(view.getPublicObjectives().get(1) > 100){
            System.out.println(view.getPublicObjectives().get(1));
            BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
            Icon icon2 = new ImageIcon(logo2);
            objective2 = new JLabel(icon2);
        }
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