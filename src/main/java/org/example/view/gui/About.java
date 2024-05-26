package org.example.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class About extends JFrame {
    public About(){
        super("About");
        JPanel aboutPanel = new JPanel();

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon/about_icon.png");
        setIconImage(icon);

        aboutPanel.setPreferredSize(new Dimension(300,200));
        Border insideBorder = BorderFactory.createTitledBorder("Informazioni");
        Border outsideBorder = BorderFactory.createEmptyBorder(10,10,10,10);
        Border finalBorder = BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
        aboutPanel.setBorder(finalBorder);
        JTextArea creditsTextPane = new JTextArea();

        creditsTextPane.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                List<String> strings = new ArrayList<>();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        GridBagConstraints gbcCreditsLabel = new GridBagConstraints();

        gbcCreditsLabel.gridx = 0;
        gbcCreditsLabel.gridy = 0;

        gbcCreditsLabel.weightx = 0.0;
        gbcCreditsLabel.weighty = 0.9;
        aboutPanel.setLayout(new GridBagLayout());
        aboutPanel.add(creditsTextPane, gbcCreditsLabel);

        setLayout(new BorderLayout());
        add(aboutPanel);

        pack();
        setSize(300,200);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
