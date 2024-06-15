package org.example.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

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
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon/about_icon.png");
        setIconImage(icon);

        // Configure the about panel
//        aboutPanel.setPreferredSize(new Dimension(300, 200));
//        Border insideBorder = BorderFactory.createTitledBorder("Informazioni");
//        Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
//        Border finalBorder = BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
//        aboutPanel.setBorder(finalBorder);

        // Create a label with a link
        JLabel link = new JLabel("Visit the page!");
        link.setForeground(Color.BLUE.darker());
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel titleParagraph = new JLabel("Design by IS24-AM19");

        JTextArea textParagraph  =new JTextArea(
                "Leonardo Chiaretti \n" +
                "James Enrico Busato\n" +
                "Matteo Civitillo\n" +
                "Alessandro Paolo Gianni Callegari"
        );

        textParagraph.setEditable(false);
        textParagraph.setOpaque(false);



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
                link.setText("Visit the page!");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                link.setText("Visit the page!");
            }
        });

        // Layout configuration
        GridBagConstraints gbcCreditsLabel = new GridBagConstraints();
        gbcCreditsLabel.gridx = 1;
        gbcCreditsLabel.gridy = 0;
        gbcCreditsLabel.weightx = 0.5;
        gbcCreditsLabel.weighty = 0.0;
        aboutPanel.setLayout(new GridBagLayout());
        aboutPanel.add(link, gbcCreditsLabel);

        GridBagConstraints gbcCreditsTitle = new GridBagConstraints();

        gbcCreditsTitle.gridx = 0;
        gbcCreditsTitle.gridy = 0;

        gbcCreditsTitle.weightx = 0.5;
        gbcCreditsTitle.weighty = 0.2;
        gbcCreditsTitle.anchor = GridBagConstraints.LINE_START;

        aboutPanel.add(titleParagraph, gbcCreditsTitle);

        GridBagConstraints gbcCreditsTextArea = new GridBagConstraints();

        gbcCreditsTextArea.gridx = 0;
        gbcCreditsTextArea.gridy = 1;

        gbcCreditsTextArea.weightx = 0.5;
        gbcCreditsTextArea.weighty = 0.6;

        aboutPanel.add(textParagraph, gbcCreditsTextArea);

        // Add the about panel to the frame
        setLayout(new BorderLayout());
        add(aboutPanel);

        // Configure the frame
        pack();
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        new About();
    }
}
