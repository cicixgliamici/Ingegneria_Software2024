package org.example.view.gui.utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CornerButton extends JButton {
    private int nx;
    private int ny;
    private final static int DIMX = 35;
    private final static int DIMY = 43;

    public CornerButton(int x, int y, int nx, int ny) {
        super("");
        this.nx = nx;
        this.ny = ny;
        setBounds(x, y, DIMX, DIMY);
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
}