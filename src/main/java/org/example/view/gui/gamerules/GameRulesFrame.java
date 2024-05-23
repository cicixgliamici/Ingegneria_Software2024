package org.example.view.gui.gamerules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * A JFrame subclass for displaying game rules as a series of images.
 * Users can navigate through images using left and right arrow keys.
 */
public class GameRulesFrame extends JFrame {

    private JPanel imagePanel; // Panel to display images
    private JScrollPane scrollPane; // Scroll pane to contain the image panel
    private ImageIcon[] images; // Array to hold the images
    private int currentIndex = 0; // Current index of the displayed image

    /**
     * Constructs a GameRulesFrame, loading images and setting up the UI components.
     */
    public GameRulesFrame() throws IOException {
        super("Game Rules");
        setSize(714, 740); // Set the size of the frame
        setLayout(new BorderLayout()); // Use BorderLayout for layout management
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/01.png");
        setIconImage(icon); // Set the icon image of the frame

        // Load all images for the rules into an array
        images = new ImageIcon[12];
        for (int i = 0; i < images.length; i++) {
            images[i] = new ImageIcon("src/main/resources/images/" + String.format("%02d", i + 1) + ".png");
        }

        // Initialize the image panel and override its paintComponent to draw the current image
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                images[currentIndex].paintIcon(this, g, 0, 0); // Draw the current image at position (0,0)
            }

            @Override
            public Dimension getPreferredSize() {
                // Dynamically adjust size based on the current image
                return new Dimension(images[currentIndex].getIconWidth(), images[currentIndex].getIconHeight());
            }
        };

        // Setup the scroll pane to house the image panel
        scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPane, BorderLayout.CENTER); // Add the scrollPane to the center of the frame

        // Add a key listener to the frame to handle left and right arrow keys for navigation
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    if (currentIndex != 0) {
                        currentIndex = (currentIndex - 1 + images.length) % images.length;
                        imagePanel.repaint(); // Repaint to show the previous image
                    }
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    if (currentIndex < images.length - 1) {
                        currentIndex++; // Increment the index to show the next image
                        imagePanel.repaint(); // Repaint to update the display
                    }
                }
            }
        });

        setFocusable(true); // Make the frame focusable to receive key events
        requestFocus(); // Request focus to receive key events
        setResizable(false); // Make the frame non-resizable
        setVisible(true); // Make the frame visible

        // Show information dialog instructing the user on how to navigate
        String information = "Please use the arrow keys to navigate.";
        JOptionPane.showMessageDialog(null, information, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
