package org.example.view.GUI;

import org.example.view.GUI.listener.EvListener;
import org.example.view.GUI.listener.Event;
import org.example.view.GUI.GameAreaFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameAreaPanel extends JPanel{
    private JLabel token1;
    private JLabel token2;
    private JLabel token3;
    private JLabel token4;
    private JButton gamearea;

    public GameAreaPanel() throws IOException {
        setLayout(new GridBagLayout());


        BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
        Icon icon1 = new ImageIcon(logo1);
        token1 = new JLabel(icon1);

        BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
        Icon icon2 = new ImageIcon(logo2);
        token2 = new JLabel(icon2);

        BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/red.png"));
        Icon icon3 = new ImageIcon(logo3);
        token3 = new JLabel(icon3);

        BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
        Icon icon4 = new ImageIcon(logo4);
        token4 = new JLabel(icon4);

        gamearea = new JButton("ebreo");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;



        add(token1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;



        add(token2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;

        add(token3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;

        add(token4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.LINE_START;

        add(gamearea, gbc);
    }

}
