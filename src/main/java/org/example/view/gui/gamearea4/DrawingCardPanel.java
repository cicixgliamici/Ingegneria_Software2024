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
    public View view;
    private JLabel coveredCard1;
    private JLabel covredCard2;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel card4;
    public DrawingCardPanel(View view) throws IOException {
        this.view = view;
        setLayout(new GridBagLayout());
        BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon1 = new ImageIcon(logo1);
        coveredCard1 = new JLabel(icon1);

        BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon2 = new ImageIcon(logo2);
        covredCard2 = new JLabel(icon2);

        BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon3 = new ImageIcon(logo3);
        card1 = new JLabel(icon3);

        BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon4 = new ImageIcon(logo4);
        card2 = new JLabel(icon4);

        BufferedImage logo5 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon5 = new ImageIcon(logo5);
        card3 = new JLabel(icon5);

        BufferedImage logo6 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon6 = new ImageIcon(logo6);
        card4 = new JLabel(icon6);

        GridBagConstraints gbFactory = new GridBagConstraints();

        gbFactory.gridy = 0;
        gbFactory.fill = GridBagConstraints.BOTH;
        gbFactory.insets = new Insets(5, 0, 5, 0);

        add(coveredCard1, gbFactory);

        gbFactory.gridy = 1;

        add(covredCard2, gbFactory);

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