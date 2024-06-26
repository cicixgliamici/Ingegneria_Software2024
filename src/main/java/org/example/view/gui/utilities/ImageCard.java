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
import java.io.InputStream;

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
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) {
            System.err.println("Resource not found at path: " + path);
            return null;
        }
        try {
            return ImageIO.read(in);
        } catch (IOException e) {
            System.err.println("Failed to load image from path: " + path);
            e.printStackTrace();
            return null;
        }
    }

    // Getters and Setters

 /**
 * Returns the ID.
 * 
 * @return the ID
 */
public int getId() {
    return id;
}

/**
 * Returns the x-coordinate.
 * 
 * @return the x-coordinate
 */
public int getX() {
    return x;
}

/**
 * Returns the y-coordinate.
 * 
 * @return the y-coordinate
 */
public int getY() {
    return y;
}

/**
 * Returns the image.
 * 
 * @return the image
 */
public BufferedImage getImage() {
    return image;
}

/**
 * Returns the high right corner button.
 * 
 * @return the high right corner button
 */
public CornerButton getCornerButtonHighDx() {
    return cornerButtonHighDx;
}

/**
 * Returns the bottom right corner button.
 * 
 * @return the bottom right corner button
 */
public CornerButton getCornerButtonBottomDx() {
    return cornerButtonBottomDx;
}

/**
 * Returns the bottom left corner button.
 * 
 * @return the bottom left corner button
 */
public CornerButton getCornerButtonBottomSx() {
    return cornerButtonBottomSx;
}

/**
 * Sets the ID.
 * 
 * @param id the ID to set
 */
public void setId(int id) {
    this.id = id;
}

/**
 * Sets the x-coordinate.
 * 
 * @param x the x-coordinate to set
 */
public void setX(int x) {
    this.x = x;
}

/**
 * Sets the y-coordinate.
 * 
 * @param y the y-coordinate to set
 */
public void setY(int y) {
    this.y = y;
}

/**
 * Sets the image.
 * 
 * @param image the image to set
 */
public void setImage(BufferedImage image) {
    this.image = image;
}

/**
 * Sets the event listener.
 * 
 * @param evListener the event listener to set
 */
public void setEvListener(EvListener evListener) {
    this.evListener = evListener;
}

/**
 * Sets the high right corner button.
 * 
 * @param cornerButtonHighDx the high right corner button to set
 */
public void setCornerButtonHighDx(CornerButton cornerButtonHighDx) {
    this.cornerButtonHighDx = cornerButtonHighDx;
}

/**
 * Sets the bottom right corner button.
 * 
 * @param cornerButtonBottomDx the bottom right corner button to set
 */
public void setCornerButtonBottomDx(CornerButton cornerButtonBottomDx) {
    this.cornerButtonBottomDx = cornerButtonBottomDx;
}

/**
 * Returns the high left corner button.
 * 
 * @return the high left corner button
 */
public CornerButton getCornerButtonHighSx() {
    return cornerButtonHighSx;
}

/**
 * Sets the high left corner button.
 * 
 * @param cornerButtonHighSx the high left corner button to set
 */
public void setCornerButtonHighSx(CornerButton cornerButtonHighSx) {
    this.cornerButtonHighSx = cornerButtonHighSx;
}

/**
 * Sets the bottom left corner button.
 * 
 * @param cornerButtonBottomSx the bottom left corner button to set
 */
public void setCornerButtonBottomSx(CornerButton cornerButtonBottomSx) {
    this.cornerButtonBottomSx = cornerButtonBottomSx;
}
}
