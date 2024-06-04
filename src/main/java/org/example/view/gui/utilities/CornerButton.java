package org.example.view.gui.utilities;

import javax.swing.*;

public class CornerButton extends JButton {
    private int nx;
    private int ny;
    private final static int DIMX = 35;
    private final static int DIMY = 43;

    public CornerButton(int x, int y, int nx, int ny) {
        super("");
//        setOpaque(false);
//        setContentAreaFilled(false);
//        setBorderPainted(false);
        this.nx = nx;
        this.ny = ny;
        setBounds(x, y, DIMX, DIMY);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }
    public boolean matchesCoordinates(int nx, int ny) {
        return this.nx == nx && this.ny == ny;
    }
}