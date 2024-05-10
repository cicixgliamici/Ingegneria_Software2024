package org.example.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TextField extends JTextField implements MouseListener {
    public TextField(String text, int columns) {
        super(text, columns);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        this.setText("");
        this.setForeground(Color.black);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
