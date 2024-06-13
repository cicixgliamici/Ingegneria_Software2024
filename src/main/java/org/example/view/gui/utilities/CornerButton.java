package org.example.view.gui.utilities;

import javax.swing.*;

/**
 * The CornerButton class represents a button with specific coordinates in a grid.
 * It is used to handle interactions at the corners of cards in a game.
 */
public class CornerButton extends JButton {
    private int nx; // The grid x-coordinate of the button
    private int ny; // The grid y-coordinate of the button
    private final static int DIMX = 35; // The width of the button
    private final static int DIMY = 43; // The height of the button

    /**
     * Constructs a CornerButton with specified screen and grid coordinates.
     *
     * @param x The screen x-coordinate of the button.
     * @param y The screen y-coordinate of the button.
     * @param nx The grid x-coordinate of the button.
     * @param ny The grid y-coordinate of the button.
     */
    public CornerButton(int x, int y, int nx, int ny) {
        super(""); // Calls the superclass constructor with an empty label
        this.nx = nx;
        this.ny = ny;
        setBounds(x, y, DIMX, DIMY); // Sets the position and size of the button
        setOpaque(false); // Makes the button transparent
        setContentAreaFilled(false); // Prevents the button from filling its content area
        setBorderPainted(false); // Prevents the button from painting its border
    }

    /**
     * Returns the grid x-coordinate of the button.
     *
     * @return The grid x-coordinate.
     */
    public int getNx() {
        return nx;
    }

    /**
     * Sets the grid x-coordinate of the button.
     *
     * @param nx The grid x-coordinate.
     */
    public void setNx(int nx) {
        this.nx = nx;
    }

    /**
     * Returns the grid y-coordinate of the button.
     *
     * @return The grid y-coordinate.
     */
    public int getNy() {
        return ny;
    }

    /**
     * Sets the grid y-coordinate of the button.
     *
     * @param ny The grid y-coordinate.
     */
    public void setNy(int ny) {
        this.ny = ny;
    }

    /**
     * Checks if the button's coordinates match the specified coordinates.
     *
     * @param nx The grid x-coordinate to check.
     * @param ny The grid y-coordinate to check.
     * @return true if the button's coordinates match the specified coordinates, false otherwise.
     */
    public boolean matchesCoordinates(int nx, int ny) {
        return this.nx == nx && this.ny == ny;
    }
}
