package org.example.view.gui.selectobjstarter3;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button class used for selecting cards in the game.
 * This button has a custom appearance with transparency and specific size.
 */
public class ChooseCardButton extends JButton {

    /**
     * Constructs a ChooseCardButton with no text, transparent background,
     * and specific preferred size.
     */
    public ChooseCardButton() {
        super("");
        setOpaque(false); // Make the button background transparent
        setContentAreaFilled(false); // Do not fill the content area
        setBorderPainted(false); // Do not paint the button border
        setPreferredSize(new Dimension(160, 107)); // Set the preferred size of the button
    }
}
