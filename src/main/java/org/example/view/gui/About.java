package org.example.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

/**
 * About class represents an information window that displays details about the application.
 * It includes a link that redirects users to the Codex Naturalis product page.
 */
public class About extends JFrame {

    /**
     * Constructor for About class. Sets up the window with information and a clickable link.
     */
    public About() {
        super("About");
        JPanel aboutPanel = new JPanel();

        // Set the window icon
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("images/icon/about_icon.png")) {
            if (iconStream != null) {
                Image icon = ImageIO.read(iconStream);
                setIconImage(icon);
            } else {
                throw new IOException("Icon image file not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a label with a link
        BufferedImage logo = null;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("images/icon/iconamini.png")) {
            if (inputStream != null) {
                logo = ImageIO.read(inputStream);
            } else {
                throw new IOException("Image file not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon1 = new ImageIcon(logo);
        JLabel labelTitle = new JLabel(icon1);
        JLabel link = new JLabel("Codex Naturalis - © 2024 - Cranio Creations");
        link.setForeground(Color.BLUE.darker());
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titleParagraph = new JLabel("Design by IS24-AM19");

        JTextArea textParagraph = new JTextArea(
//                "Design by IS24-AM19: \n" +
                "Leonardo Chiaretti \n" +
                        "James Enrico Busato\n" +
                        "Matteo Civitillo\n" +
                        "Alessandro Paolo Gianni Callegari"
        );

        textParagraph.setEditable(false);
        textParagraph.setOpaque(false);

        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add a mouse listener to handle click events on the link
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    // Open the URL in the default web browser
                    Desktop.getDesktop().browse(new URI("https://www.craniocreations.it/prodotto/codex-naturalis"));
                    dispose();
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                link.setText("Codex Naturalis - © 2024 - Cranio Creations");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                link.setText("Visit the page!");
            }
        });

        aboutPanel.setLayout(new GridBagLayout());

        // Layout configuration
        GridBagConstraints gbcLogoLabel = new GridBagConstraints();
        gbcLogoLabel.gridx = 0;
        gbcLogoLabel.gridy = 0;
        gbcLogoLabel.weightx = 0.0;
        gbcLogoLabel.weighty = 0.01;
        aboutPanel.add(labelTitle, gbcLogoLabel);

        GridBagConstraints gbcCreditsLabel = new GridBagConstraints();
        gbcCreditsLabel.gridx = 0;
        gbcCreditsLabel.gridy = 2;
        gbcCreditsLabel.weightx = 0.0;
        gbcCreditsLabel.weighty = 0.01;
        aboutPanel.add(link, gbcCreditsLabel);

        GridBagConstraints gbcCreditsTitle = new GridBagConstraints();
        gbcCreditsTitle.gridx = 0;
        gbcCreditsTitle.gridy = 3;
        gbcCreditsTitle.weightx = 0.0;
        gbcCreditsTitle.weighty = 0.01;
        //gbcCreditsTitle.anchor = GridBagConstraints.LINE_START;
        aboutPanel.add(titleParagraph, gbcCreditsTitle);

        GridBagConstraints gbcCreditsTextArea = new GridBagConstraints();
        gbcCreditsTextArea.gridx = 0;
        gbcCreditsTextArea.gridy = 4;
        gbcCreditsTextArea.weightx = 0.0;
        gbcCreditsTextArea.weighty = 0.02;
        aboutPanel.add(textParagraph, gbcCreditsTextArea);

        GridBagConstraints gbcCloseButton = new GridBagConstraints();
        gbcCloseButton.gridx = 0;
        gbcCloseButton.gridy = 5;
        gbcCloseButton.weightx = 0.0;
        gbcCloseButton.weighty = 0.1;
        aboutPanel.add(closeButton, gbcCloseButton);

        // Add the about panel to the frame
        setLayout(new BorderLayout());
        add(aboutPanel);

        // Configure the frame
        pack();
        setSize(300, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}