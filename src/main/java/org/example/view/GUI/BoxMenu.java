package org.example.view.GUI;

import javax.swing.*;
import java.awt.*;

public class BoxMenu extends JPanel {
    private JButton button;
    private JLabel labelTitle;
    private JLabel labelUsr;
    private JTextField textFieldUsr;
    private JLabel labelIp;
    private JTextField textFieldIp;
    private JLabel labelPort;
    private JTextField textFieldPort;

    public BoxMenu(){
        setLayout(new GridBagLayout());

        // Components
        labelTitle = new JLabel("Codex Naturalis");

        labelUsr = new JLabel("Username:");
        textFieldUsr = new JTextField("Inserisci un username...");

        labelIp = new JLabel("Ip:");
        textFieldIp = new JTextField("Inserici un indirizzo ip...");

        labelPort = new JLabel("Porta:");
        textFieldPort = new JTextField("Inserisci una porta...");

        button = new JButton("Connetti!");

        // Layout
        GridBagConstraints gbcTitle = new GridBagConstraints();

        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;

        gbcTitle.weightx = 0.0;
        gbcTitle.weighty = 0.0;

        add(labelTitle, gbcTitle);

        GridBagConstraints gbcUsrLabel = new GridBagConstraints();

        gbcUsrLabel.gridx = 0;
        gbcUsrLabel.gridy = 1;

        gbcUsrLabel.weightx = 0.0;
        gbcUsrLabel.weighty = 0.0;

        add(labelUsr, gbcUsrLabel);

        GridBagConstraints gbcUsrField = new GridBagConstraints();

        gbcUsrField.gridx = 1;
        gbcUsrField.gridy = 1;

        gbcUsrField.weightx = 0.0;
        gbcUsrField.weighty = 0.0;

        add(textFieldUsr, gbcUsrField);

        GridBagConstraints gbcIpLabel = new GridBagConstraints();

        gbcIpLabel.gridx = 0;
        gbcIpLabel.gridy = 2;

        gbcIpLabel.weightx = 0.0;
        gbcIpLabel.weighty = 0.0;

        add(labelIp, gbcIpLabel);

        GridBagConstraints gbcIpField = new GridBagConstraints();

        gbcIpField.gridx = 1;
        gbcIpField.gridy = 2;

        gbcIpField.weightx = 0.0;
        gbcIpField.weighty = 0.0;

        add(textFieldIp, gbcIpField);

        GridBagConstraints gbcPortLabel = new GridBagConstraints();

        gbcPortLabel.gridx = 0;
        gbcPortLabel.gridy = 3;

        gbcPortLabel.weightx = 0.0;
        gbcPortLabel.weighty = 0.0;

        add(labelPort, gbcPortLabel);

        GridBagConstraints gbcPortField = new GridBagConstraints();

        gbcPortField.gridx = 1;
        gbcPortField.gridy = 3;

        gbcPortField.weightx = 0.0;
        gbcPortField.weighty = 0.0;

        add(textFieldPort, gbcPortField);

        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcButton.gridx = 0;
        gbcButton.gridy = 4;

        gbcButton.weightx = 0.0;
        gbcButton.weighty = 0.0;

        add(button, gbcButton);
    }
}
