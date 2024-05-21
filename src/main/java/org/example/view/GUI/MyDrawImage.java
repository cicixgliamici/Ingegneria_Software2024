package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//not working
public class MyDrawImage extends JPanel {
    private Image image;
    private int x;
    private int y;

    // Costruttore per inizializzare l'immagine e le coordinate
    public MyDrawImage(String imagePath, int x, int y) throws IOException {
        ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));
        this.image = icon.getImage();
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna l'immagine alle coordinate specificate
        g.drawImage(image, x, y, this);
    }

}
