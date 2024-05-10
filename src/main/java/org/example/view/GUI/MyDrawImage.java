package org.example.view.GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

//not working
public class MyDrawImage {
    private Graphics graphics;
    private int x;
    private int y;
    private String fileName;

    public MyDrawImage(Graphics graphics, int x, int y, String fileName){
        this.graphics = graphics;
        this.x = x;
        this.y = y;
        this.fileName = fileName;
    }

    public void draw(){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(fileName);
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        graphics.drawImage(img, x,y,null);
    }
}
