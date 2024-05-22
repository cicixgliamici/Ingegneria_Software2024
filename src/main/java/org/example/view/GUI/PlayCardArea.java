package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PlayCardArea extends JPanel {

    private ImageCard imageCard;

    public PlayCardArea() throws IOException {
        setLayout(null);
        setPreferredSize(new Dimension(800,800));
    }

    public void InsertCard(int x, int y, String pathImage){
        imageCard = new ImageCard(pathImage, x, y);
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageCard.getImage(), imageCard.getX(), imageCard.getY(), this);
        repaint();
    }
}
