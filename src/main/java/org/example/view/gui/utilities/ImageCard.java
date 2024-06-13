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

/**
 * The ImageCard class represents a card with an image and interactive corner buttons.
 * This class handles the display of the card and interactions with its corners.
 */
public class ImageCard {

    private int x; // The x-coordinate of the card
    private int y; // The y-coordinate of the card
    private final static int SCALEX = 125; // The x scaling factor for the card
    private final static int SCALEY = 64; // The y scaling factor for the card
    private int id; // The id of the card
    private BufferedImage image; // The image of the card
    private CornerButton cornerButtonHighDx; // Button at the high-right corner
    private CornerButton cornerButtonBottomDx; // Button at the bottom-right corner
    private CornerButton cornerButtonHighSx; // Button at the high-left corner
    private CornerButton cornerButtonBottomSx; // Button at the bottom-left corner
    private EvListener evListener; // Event listener for handling events

    /**
     * Constructs an ImageCard with the specified image path and coordinates.
     *
     * @param imagePath The path to the card image.
     * @param x The x-coordinate of the card.
     * @param y The y-coordinate of the card.
     * @param nx The grid x-coordinate of the card.
     * @param ny The grid y-coordinate of the card.
     */
    public ImageCard(String imagePath, int x, int y, int nx, int ny) {
        this.image = loadImage(imagePath);
        this.x = x;
        this.y = y;

        // Initialize corner buttons and add action listeners
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

    /**
     * Loads an image from the specified path.
     *
     * @param path The path to the image file.
     * @return The loaded BufferedImage.
     */
    private BufferedImage loadImage(String path) {
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

    // Getters and Setters

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
