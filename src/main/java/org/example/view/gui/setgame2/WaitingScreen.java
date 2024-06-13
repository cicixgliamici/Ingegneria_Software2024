package org.example.view.gui.setgame2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The WaitingScreen class represents a waiting screen displayed to the user while waiting for other players to join.
 * It shows a logo, a message, and an indeterminate progress bar.
 */
public class WaitingScreen extends JFrame {

    /**
     * Constructs a WaitingScreen frame.
     *
     * @throws IOException if an error occurs while loading images.
     */
    public WaitingScreen() throws IOException {
        super("Waiting for Players");
        setLayout(new BorderLayout());

        // Create a panel with a background image
        JPanel panel = new JPanel(new GridBagLayout()) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/backgroundSelecObjStarter.jpg")));
            Image img = icon.getImage();

            {
                setOpaque(false);
            }

            @Override
            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        // Load the logo image
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("/images/icon/iconamini.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon = new ImageIcon(logo);
        JLabel labelLogo = new JLabel(icon);
        JLabel label = new JLabel("Waiting for other player...");

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        // Layout for components
        GridBagConstraints gbcLogo = new GridBagConstraints();
        gbcLogo.gridx = 0;
        gbcLogo.gridy = 0;
        gbcLogo.weightx = 0.0;
        gbcLogo.weighty = 0.5;
        panel.add(labelLogo, gbcLogo);

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 1;
        gbcLabel.weightx = 0.0;
        gbcLabel.weighty = 0.2;
        panel.add(label, gbcLabel);

        GridBagConstraints gbcProgress = new GridBagConstraints();
        gbcProgress.gridx = 0;
        gbcProgress.gridy = 2;
        gbcProgress.weightx = 0.0;
        gbcProgress.weighty = 0.4;
        panel.add(progressBar, gbcProgress);

        add(panel, BorderLayout.CENTER);

        // Frame settings
        setUndecorated(true);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
