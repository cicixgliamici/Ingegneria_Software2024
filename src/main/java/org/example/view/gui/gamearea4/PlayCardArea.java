package org.example.view.gui.gamearea4;

import org.example.view.gui.utilities.ImageCard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;

import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
public class PlayCardArea extends JPanel {

    private List<ImageCard> cardPlaced = new ArrayList<>();

    private double scale = 1.0;

    public PlayCardArea() {
        setLayout(null);
        setPreferredSize(new Dimension(800,800));

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.isControlDown()) {
                    int notches = e.getWheelRotation();
                    if (notches < 0) {
                        scale += 0.1;
                    } else {
                        scale -= 0.1;
                        if (scale < 0.1) {
                            scale = 0.1;
                        }
                    }
                    revalidate();
                    repaint();
                }
            }
        });
    }

    public void InsertCard(int x, int y, String pathImage){
        ImageCard imageCard = new ImageCard(pathImage, x, y);
        cardPlaced.add(imageCard);
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(scale, scale);
        for (ImageCard imageCard : cardPlaced) {
            g2d.drawImage(imageCard.getImage(), imageCard.getX(), imageCard.getY(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (int) (800 * scale);
        int height = (int) (800 * scale);
        return new Dimension(width, height);
    }
}