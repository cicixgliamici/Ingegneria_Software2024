package org.example.view.gui.gamearea4;

import org.example.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ScoreboardPanel extends JPanel {
    /*private JLabel scoreboard;
    private JLabel objective1;
    private JLabel objective2;

    private JLabel token1;

    private JLabel token2;

    private JLabel token3;

    private JLabel token4;*/

    private BufferedImage scoreboard;

    private BufferedImage obj1;

    private BufferedImage obj2;

    private BufferedImage token1;

    public ScoreboardPanel(View view) throws IOException {
        //setLayout(new GridBagLayout());

        HashMap<Integer, List<Integer>> coordinates = new HashMap<>();

        coordinates.put(0, List.of(60, 575));
        coordinates.put(1, List.of(130, 575));
        coordinates.put(2, List.of(200, 575));
        coordinates.put(3, List.of(240, 510));
        coordinates.put(4, List.of(170, 510));
        coordinates.put(5, List.of(100, 510));
        coordinates.put(6, List.of(30, 510));
        coordinates.put(7, List.of(30, 445));
        coordinates.put(8, List.of(100, 445));
        coordinates.put(9, List.of(170, 445));
        coordinates.put(10, List.of(240, 445));
        coordinates.put(11, List.of(240, 380));
        coordinates.put(12, List.of(170, 380));
        coordinates.put(13, List.of(100, 380));
        coordinates.put(14, List.of(30, 380));
        coordinates.put(15, List.of(30, 315));
        coordinates.put(16, List.of(100, 315));
        coordinates.put(17, List.of(170, 315));
        coordinates.put(18, List.of(240, 315));
        coordinates.put(19, List.of(240, 250));
        coordinates.put(20, List.of(135, 220));
        coordinates.put(21, List.of(30, 250));
        coordinates.put(22, List.of(30, 185));
        coordinates.put(23, List.of(30, 120));
        coordinates.put(24, List.of(70, 65));
        coordinates.put(25, List.of(135, 55));
        coordinates.put(26, List.of(200, 65));
        coordinates.put(27, List.of(240, 120));
        coordinates.put(28, List.of(240, 185));
        coordinates.put(29, List.of(135, 135));


        scoreboard = ImageIO.read(new File("src/main/resources/images/plateau.png"));
        //Icon icon = new ImageIcon(logo);
        //scoreboard = new JLabel(icon);

        if(view.getPublicObjectives().get(0) < 100) {
            System.out.println(view.getPublicObjectives().get(0));
            obj1 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
            //Icon icon1 = new ImageIcon(logo1);
            //objective1 = new JLabel(icon1);
        }
        else if(view.getPublicObjectives().get(0) >= 100){
            System.out.println(view.getPublicObjectives().get(0));
            obj1 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
            //Icon icon1 = new ImageIcon(logo1);
            //objective1 = new JLabel(icon1);
        }
        if(view.getPublicObjectives().get(1) < 100) {
            System.out.println(view.getPublicObjectives().get(1));
            obj2 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
            //Icon icon2 = new ImageIcon(logo2);
            //objective2 = new JLabel(icon2);
        }
        else if(view.getPublicObjectives().get(1) >= 100){
            System.out.println(view.getPublicObjectives().get(1));
            obj2 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
            //Icon icon2 = new ImageIcon(logo2);
            //objective2 = new JLabel(icon2);
        }

        //token1 = ImageIO.read(new File("C:\\Users\\jamie\\OneDrive\\Desktop\\CODEX_pion_bleu (1).png"));
        //Icon icon3 = new ImageIcon(logo3);
        //token1 = new JLabel(icon3);



        /*GridBagConstraints gbc = new GridBagConstraints();

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

        add(objective2, gbc);*/
        System.out.println("La madre di matteo è na troia");
        System.out.println(view.getPoints());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scoreboard != null) {
            g.drawImage(scoreboard, 15, 15, this);
        }
        if (obj1 != null) {
            g.drawImage(obj1, 3, 640, this);
        }
        if (obj2 != null) {
            g.drawImage(obj2, 170, 640, this);
        }
        if(token1 != null){
            g.drawImage(token1, 200, 65, this);
        }
    }

}