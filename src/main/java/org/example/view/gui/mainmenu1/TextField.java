package org.example.view.gui.mainmenu1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Custom JTextField that clears its default text on the first interaction (click or key press).
 */
public class TextField extends JTextField implements MouseListener, KeyListener {

    private boolean isFirstTouch = true; // Flag to track if the field has been interacted with

    /**
     * Constructs a TextField with the specified text and column width.
     *
     * @param text The initial text to display in the text field.
     * @param columns The number of columns to use to calculate the preferred width.
     */
    public TextField(String text, int columns) {
        super(text, columns);
        addMouseListener(this);
        addKeyListener(this);
    }

    /**
     * Clears the text field on first click and sets the text color to black.
     *
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (isFirstTouch) {
            this.setText("");
            this.setForeground(Color.black);
            isFirstTouch = false;
        }
    }

    // Unused mouse event methods
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Clears the text field on first key type and sets the text color to black.
     *
     * @param e The key event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (isFirstTouch) {
            this.setText("");
            this.setForeground(Color.black);
            isFirstTouch = false;
        }
    }

    // Unused key event methods
    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
