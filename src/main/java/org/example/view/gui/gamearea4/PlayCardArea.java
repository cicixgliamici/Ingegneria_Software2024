package org.example.view.gui.gamearea4;

import org.example.view.gui.utilities.ImageCard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;

import java.awt.dnd.*;
import java.util.ArrayList;
import java.util.List;
public class PlayCardArea extends JPanel {

    private List<ImageCard> cardPlaced = new ArrayList<>();

    public PlayCardArea() {
        setLayout(null);
        setPreferredSize(new Dimension(800,800));
    }

    public void InsertCard(int x, int y, String pathImage){
        ImageCard imageCard = new ImageCard(pathImage, x, y);
        cardPlaced.add(imageCard);
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(ImageCard imageCard : cardPlaced) {
            g.drawImage(imageCard.getImage(), imageCard.getX(), imageCard.getY(), this);
            repaint();
        }
    }
}