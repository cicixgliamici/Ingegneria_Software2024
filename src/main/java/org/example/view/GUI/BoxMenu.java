package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoxMenu extends JPanel{
    private JButton button;
    private JLabel labelTitle;
    private JLabel labelUsr;
    private TextField textFieldUsr;
    private JLabel labelIp;
    private TextField textFieldIp;
    private JLabel labelPort;
    private TextField textFieldPort;

    public BoxMenu() throws IOException{
        setLayout(new GridBagLayout());

        // Components
        BufferedImage logo = ImageIO.read(new File("src/main/resources/logomini.jpg"));
        Icon icon = new ImageIcon(logo);
        labelTitle = new JLabel(icon);

        labelUsr = new JLabel("Username:");
        labelUsr.setForeground(Color.white);
        textFieldUsr = new TextField("Inserisci un username...", 15);
        textFieldUsr.addMouseListener(textFieldUsr);
        textFieldUsr.setForeground(Color.gray);

        labelIp = new JLabel("Ip:");
        labelIp.setForeground(Color.white);
        textFieldIp = new TextField("Inserici un indirizzo ip...", 15);
        textFieldIp.setForeground(Color.gray);
        textFieldIp.addMouseListener(textFieldIp);
        /*try {
            MaskFormatter formatter = new MaskFormatter("###.###.###.###");
            formatter.setPlaceholderCharacter('#');
            textFieldIp = new JFormattedTextField(formatter);
            textFieldIp.setColumns(15);
        } catch(Exception e) {
            e.printStackTrace();
        } */

        labelPort = new JLabel("Porta:");
        labelPort.setForeground(Color.white);
        textFieldPort = new TextField("Inserisci una porta...", 15);
        textFieldPort.addMouseListener(textFieldPort);
        textFieldPort.setForeground(Color.gray);

        button = new JButton("Connetti!");

        // Layout
        GridBagConstraints gbcTitle = new GridBagConstraints();

        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;

        gbcTitle.weightx = 0.0;
        gbcTitle.weighty = 0.4;

        gbcTitle.gridheight = 1;
        gbcTitle.gridwidth = 2;

        add(labelTitle, gbcTitle);

        GridBagConstraints gbcUsrLabel = new GridBagConstraints();

        gbcUsrLabel.gridx = 0;
        gbcUsrLabel.gridy = 1;

        gbcUsrLabel.weightx = 0.0;
        gbcUsrLabel.weighty = 0.01;

        add(labelUsr, gbcUsrLabel);

        GridBagConstraints gbcUsrField = new GridBagConstraints();

        gbcUsrField.gridx = 1;
        gbcUsrField.gridy = 1;

        gbcUsrField.weightx = 0.0;
        gbcUsrField.weighty = 0.01;

        gbcUsrField.insets = new Insets(0, 10, 0, 10);

        add(textFieldUsr, gbcUsrField);

        GridBagConstraints gbcIpLabel = new GridBagConstraints();

        gbcIpLabel.gridx = 0;
        gbcIpLabel.gridy = 2;

        gbcIpLabel.weightx = 0.0;
        gbcIpLabel.weighty = 0.01;

        add(labelIp, gbcIpLabel);

        GridBagConstraints gbcIpField = new GridBagConstraints();

        gbcIpField.gridx = 1;
        gbcIpField.gridy = 2;

        gbcIpField.weightx = 0.0;
        gbcIpField.weighty = 0.01;

        gbcIpField.insets = new Insets(0, 10, 0, 10);

        add(textFieldIp, gbcIpField);

        GridBagConstraints gbcPortLabel = new GridBagConstraints();

        gbcPortLabel.gridx = 0;
        gbcPortLabel.gridy = 3;

        gbcPortLabel.weightx = 0.0;
        gbcPortLabel.weighty = 0.01;

        add(labelPort, gbcPortLabel);

        GridBagConstraints gbcPortField = new GridBagConstraints();

        gbcPortField.gridx = 1;
        gbcPortField.gridy = 3;

        gbcPortField.weightx = 0.0;
        gbcPortField.weighty = 0.01;

        gbcPortField.insets = new Insets(0, 10, 0, 10);

        add(textFieldPort, gbcPortField);

        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcButton.gridx = 0;
        gbcButton.gridy = 4;

        gbcButton.weightx = 0.0;
        gbcButton.weighty = 0.5;

        gbcButton.gridheight = 1;
        gbcButton.gridwidth = 2;

        add(button, gbcButton);
    }
}
