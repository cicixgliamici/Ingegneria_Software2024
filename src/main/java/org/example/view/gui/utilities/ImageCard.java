package org.example.view.gui.utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCard {
    private long id;
    private BufferedImage image;
    private int x;
    private int y;

    public ImageCard(String imagePath, int x, int y) {
        this.image = loadImage(imagePath);
        this.x = x;
        this.y = y;
    }

    // il parametro "path" è il path dell'immagine da caricare
    private BufferedImage loadImage(String path) {
        BufferedImage bimg = null;
        BufferedImage ret = null;
        try {
            bimg = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret = new BufferedImage(bimg.getWidth(), bimg.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        g.drawImage(bimg, 0, 0, null);
        g.dispose();

        return ret;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}