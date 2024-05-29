package org.example.view.gui.gamearea4;

import org.example.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingCardPanel extends JPanel {
    public int draw;
    private JLabel coveredCard1;
    private JLabel coveredCard2;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel card4;

    public View view;
    public DrawingCardPanel(View view) throws IOException {
        this.view = view;
        setLayout(new GridBagLayout());

        /*BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon1 = new ImageIcon(logo1);
        coveredCard1 = new JLabel(icon1);*/

        BufferedImage image1 = null;
        try {
            if(view.getDrawableCards().get(0) < 10){
                image1 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(0)).toString() + ".png"));
            } else if (view.getDrawableCards().get(0) > 99) {
                image1 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(0)).toString() + ".png"));
            }
            else {
                image1 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(0)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon1 = new ImageIcon(image1);
        coveredCard1 = new JLabel(icon1);

        /*BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon2 = new ImageIcon(logo2);
        covredCard2 = new JLabel(icon2);*/

        BufferedImage image2 = null;
        try {
            if(view.getDrawableCards().get(1) < 10){
                image2 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(1)).toString() + ".png"));
            } else if (view.getDrawableCards().get(1) > 99) {
                image2 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(1)).toString() + ".png"));
            }
            else {
                image2 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(1)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon2 = new ImageIcon(image2);
        coveredCard2 = new JLabel(icon2);

        /*BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon3 = new ImageIcon(logo3);
        card1 = new JLabel(icon3);*/

        BufferedImage image3 = null;
        try {
            if(view.getDrawableCards().get(2) < 10){
                image3 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png"));
            } else if (view.getDrawableCards().get(2) > 99) {
                image3 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png"));
            }
            else {
                image3 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon3 = new ImageIcon(image3);
        card1 = new JLabel(icon3);

        /*BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon4 = new ImageIcon(logo4);
        card2 = new JLabel(icon4);*/

        BufferedImage image4 = null;
        try {
            if(view.getDrawableCards().get(3) < 10){
                image4 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png"));
            } else if (view.getDrawableCards().get(3) > 99) {
                image4 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png"));
            }
            else {
                image4 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon4 = new ImageIcon(image4);
        card2 = new JLabel(icon4);

        /*BufferedImage logo5 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon5 = new ImageIcon(logo5);
        card3 = new JLabel(icon5);*/

        BufferedImage image5 = null;
        try {
            if(view.getDrawableCards().get(4) < 10){
                image5 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(4)).toString() + ".png"));
            } else if (view.getDrawableCards().get(4) > 99) {
                image5 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(4)).toString() + ".png"));
            }
            else {
                image5 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(4)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon5 = new ImageIcon(image5);
        card3 = new JLabel(icon5);

        /*BufferedImage logo6 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon6 = new ImageIcon(logo6);
        card4 = new JLabel(icon6);*/

        BufferedImage image6 = null;
        try {
            if(view.getDrawableCards().get(5) < 10){
                image6 = ImageIO.read(new File("src/main/resources/images/big/big/back/00" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png"));
            } else if (view.getDrawableCards().get(5) > 99) {
                image6 = ImageIO.read(new File("src/main/resources/images/big/big/back/" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png"));
            }
            else {
                image6 = ImageIO.read(new File("src/main/resources/images/big/big/back/0" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png"));

            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon6 = new ImageIcon(image6);
        card4 = new JLabel(icon6);


        GridBagConstraints gbFactory = new GridBagConstraints();

        gbFactory.gridy = 0;
        gbFactory.fill = GridBagConstraints.BOTH;
        gbFactory.insets = new Insets(5, 0, 5, 0);

        add(coveredCard1, gbFactory);

        gbFactory.gridy = 1;

        add(coveredCard2, gbFactory);

        gbFactory.gridy = 2;

        add(card1, gbFactory);

        gbFactory.gridy = 3;

        add(card2, gbFactory);

        gbFactory.gridy = 4;

        add(card3, gbFactory);

        gbFactory.gridy = 5;

        add(card4, gbFactory);

    }
}