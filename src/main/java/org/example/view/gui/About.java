package org.example.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;



public class About extends JFrame {
    public About(){
        super("About");
        JPanel aboutPanel = new JPanel();

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon/about_icon.png");
        setIconImage(icon);

        aboutPanel.setPreferredSize(new Dimension(300, 200));
        Border insideBorder = BorderFactory.createTitledBorder("Informazioni");
        Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border finalBorder = BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
        aboutPanel.setBorder(finalBorder);
        //JTextArea creditsTextPane = new JTextArea();
        JLabel link = new JLabel("Visit the page!");
        link.setForeground(Color.BLUE.darker());
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                try
                {
                    Desktop.getDesktop().browse(new URI("https://www.craniocreations.it/prodotto/codex-naturalis"));
                }
                catch (IOException | URISyntaxException e1)
                {
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
        GridBagConstraints gbcCreditsLabel = new GridBagConstraints();
        gbcCreditsLabel.gridx = 0;
        gbcCreditsLabel.gridy = 0;
        gbcCreditsLabel.weightx = 0.0;
        gbcCreditsLabel.weighty = 0.9;
        aboutPanel.setLayout(new GridBagLayout());
        aboutPanel.add(link, gbcCreditsLabel);
        setLayout(new BorderLayout());
        add(aboutPanel);
        pack();
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
