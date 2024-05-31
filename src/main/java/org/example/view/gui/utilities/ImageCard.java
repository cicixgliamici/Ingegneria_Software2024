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
    private long id;
    private BufferedImage image;
    private int x;
    private int y;
    private CornerButton cornerButtonHighDx;
    private CornerButton cornerButtonBottomDx;

    private CornerButton cornerButtonHighSx;

    private CornerButton cornerButtonBottomSx;
    private EvListener evListener;
    private final static int SCALEX = 125;
    private final static int SCALEY = 64;



    public ImageCard(String imagePath, int x, int y, int nx, int ny) {
        this.image = loadImage(imagePath);
        this.x = x;
        this.y = y;
        cornerButtonHighDx = new CornerButton(x+SCALEX, y, nx + 1, ny + 1);
        cornerButtonHighDx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO comunicare al server la propria mossa e verificare che sia possibile (questo todo vale per tutti gli altri metodi)
                //TODO sempre utilizzando la logica degli eventi e passando al model nx e ny, attributi appartenenti al bottone
                Event event = new Event(this, "addFromHighDx");
                cornerButtonHighDx.setEnabled(false);
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonBottomDx = new CornerButton(x+SCALEX, y+SCALEY, nx + 1, ny - 1);
        cornerButtonBottomDx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromBottomDx");
                cornerButtonBottomDx.setEnabled(false);
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonHighSx = new CornerButton(x,y,nx - 1, ny + 1);
        cornerButtonHighSx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromHighSx");
                cornerButtonHighSx.setEnabled(false);
                if (evListener != null) {
                    try {
                        evListener.eventListener(event);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        cornerButtonBottomSx = new CornerButton(x, y+SCALEY, nx - 1, ny - 1);
        cornerButtonBottomSx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Event event = new Event(this, "addFromBottomSx");
                cornerButtonBottomSx.setEnabled(false);
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
    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    public CornerButton getCornerButtonHighDx() {
        return cornerButtonHighDx;
    }

    public void setCornerButtonHighDx(CornerButton cornerButtonHighDx) {
        this.cornerButtonHighDx = cornerButtonHighDx;
    }

    public CornerButton getCornerButtonBottomDx() {
        return cornerButtonBottomDx;
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

    public CornerButton getCornerButtonBottomSx() {
        return cornerButtonBottomSx;
    }

    public void setCornerButtonBottomSx(CornerButton cornerButtonBottomSx) {
        this.cornerButtonBottomSx = cornerButtonBottomSx;
    }
}