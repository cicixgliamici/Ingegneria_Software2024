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
    private PlayCardArea playCardArea;

    public GameAreaPanel(String color, String num) throws IOException {
        setLayout(new GridBagLayout());

        switch (num){
            case "1":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon2 = new ImageIcon(logo2);
                token2 = new JLabel(icon2);
                token2.setVisible(false);
                BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon3 = new ImageIcon(logo3);
                token3 = new JLabel(icon3);
                token3.setVisible(false);
                BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon4 = new ImageIcon(logo4);
                token4 = new JLabel(icon4);
                token4.setVisible(false);
                break;

            case "2":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo5 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon5 = new ImageIcon(logo5);
                token2 = new JLabel(icon5);
                BufferedImage logo6 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon6 = new ImageIcon(logo6);
                token3 = new JLabel(icon6);
                token3.setVisible(false);
                BufferedImage logo7 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon7 = new ImageIcon(logo7);
                token4 = new JLabel(icon7);
                token4.setVisible(false);
                break;

            case "3":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo8 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon8 = new ImageIcon(logo8);
                token2 = new JLabel(icon8);
                BufferedImage logo9 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon9 = new ImageIcon(logo9);
                token3 = new JLabel(icon9);
                BufferedImage logo10 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon10 = new ImageIcon(logo10);
                token4 = new JLabel(icon10);
                token4.setVisible(false);
                break;

            case "4":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo11 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon11 = new ImageIcon(logo11);
                token2 = new JLabel(icon11);
                BufferedImage logo12 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon12 = new ImageIcon(logo12);
                token3 = new JLabel(icon12);
                BufferedImage logo13 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon13 = new ImageIcon(logo13);
                token4 = new JLabel(icon13);
                break;
        }

        gamearea = new JButton("prova");

        //PlayCardArea

        playCardArea = new PlayCardArea();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 2;

        gbc.weightx = 0.00025;
        gbc.weighty = 0.00025;

        add(token1, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;

        add(token2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.weightx = 0.00025;
        gbc.weighty = 0.00025;

        add(token3, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;

        gbc.weightx = 0.001;
        gbc.weighty = 0.001;

        add(token4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        gbc.weightx = 0.75;
        gbc.weighty = 0.75;

        gbc.fill = GridBagConstraints.BOTH;
        //gbc.anchor = GridBagConstraints.LINE_START;

        add(playCardArea, gbc);
    }

}
