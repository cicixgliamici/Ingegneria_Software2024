
package org.example.view.gui.mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TextField extends JTextField implements MouseListener, KeyListener {
    private boolean isFirstTouch = true;
    public TextField(String text, int columns) {
        super(text, columns);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(isFirstTouch){
            this.setText("");
            this.setForeground(Color.black);
            isFirstTouch = false;
        }
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


    @Override
    public void keyTyped(KeyEvent e) {
        if (isFirstTouch){
            this.setText("");
            this.setForeground(Color.black);
            isFirstTouch = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
