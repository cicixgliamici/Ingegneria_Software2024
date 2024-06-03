package org.example.view.gui.utilities;

import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCard {

    private int x;
    private int y;
    private final static int SCALEX = 125;
    private final static int SCALEY = 64;
    private int id;
    private BufferedImage image;
    private CornerButton cornerButtonHighDx;
    private CornerButton cornerButtonBottomDx;
    private CornerButton cornerButtonHighSx;
    private CornerButton cornerButtonBottomSx;
    private EvListener evListener;

    public ImageCard(String imagePath, int x, int y, int nx, int ny) {
        System.out.println(imagePath);
        this.image = loadImage(imagePath);
        this.x = x;
        this.y = y;
        cornerButtonHighDx = new CornerButton(x + SCALEX, y, nx + 1, ny + 1);
        cornerButtonHighDx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromHighDx");
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonBottomDx = new CornerButton(x + SCALEX, y + SCALEY, nx + 1, ny - 1);
        cornerButtonBottomDx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromBottomDx");
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonHighSx = new CornerButton(x, y, nx - 1, ny + 1);
        cornerButtonHighSx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromHighSx");
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonBottomSx = new CornerButton(x, y + SCALEY, nx - 1, ny - 1);
        cornerButtonBottomSx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromBottomSx");
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    private BufferedImage loadImage(String path) {
        System.out.println(path);
        BufferedImage bimg = null;
        BufferedImage ret = null;
        try {
            bimg = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ret.createGraphics();
        g.drawImage(bimg, 0, 0, null);
        g.dispose();
        return ret;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() {
        return image;
    }

    public CornerButton getCornerButtonHighDx() {
        return cornerButtonHighDx;
    }

    public CornerButton getCornerButtonBottomDx() {
        return cornerButtonBottomDx;
    }

    public CornerButton getCornerButtonBottomSx() {
        return cornerButtonBottomSx;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    public void setCornerButtonHighDx(CornerButton cornerButtonHighDx) {
        this.cornerButtonHighDx = cornerButtonHighDx;
    }

    public void setCornerButtonBottomDx(CornerButton cornerButtonBottomDx) {
        this.cornerButtonBottomDx = cornerButtonBottomDx;
    }

    public CornerButton getCornerButtonHighSx() {
        return cornerButtonHighSx;
    }

    public void setCornerButtonHighSx(CornerButton cornerButtonHighSx) {
        this.cornerButtonHighSx = cornerButtonHighSx;
    }

    public void setCornerButtonBottomSx(CornerButton cornerButtonBottomSx) {
        this.cornerButtonBottomSx = cornerButtonBottomSx;
    }
}