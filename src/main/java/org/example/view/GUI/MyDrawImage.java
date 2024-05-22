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
        Toolkit tk = Toolkit.getDefaultToolkit();
        image = tk.getImage(imagePath);
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(image, 1);
        try { mt.waitForAll(); }
        catch (InterruptedException e){}
    }

    @Override
   protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, this);
    }

}
