package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoxMenu extends JPanel{
    // monnezza
    private JButton button;
    private JLabel labelTitle;
    private JLabel labelUsr;
    private TextField textFieldUsr;
    private JLabel labelIp;
    private TextField textFieldIp;
    private JLabel labelPort;
    private TextField textFieldPort;
    private EvListener evListener;

    public BoxMenu() throws IOException{
        setLayout(new GridBagLayout());

        // Components
        BufferedImage logo = ImageIO.read(new File("src/main/resources/images/logomini.jpg"));
        Icon icon = new ImageIcon(logo);
        labelTitle = new JLabel(icon);

        labelUsr = new JLabel("Username:");
        labelUsr.setForeground(Color.darkGray);
        labelUsr.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldUsr = new TextField("Inserisci un username...", 15);
        textFieldUsr.setForeground(Color.gray);
        textFieldUsr.addMouseListener(textFieldUsr);
        textFieldUsr.addKeyListener(textFieldUsr);

        labelIp = new JLabel("Ip:");
        labelIp.setForeground(Color.darkGray);
        labelIp.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldIp = new TextField("Inserici un indirizzo ip...", 15);
        textFieldIp.setForeground(Color.gray);
        textFieldIp.addKeyListener(textFieldIp);
        textFieldIp.addMouseListener(textFieldIp);
        /*try {
            MaskFormatter formatter = new MaskFormatter("###.###.###.###");
            formatter.setPlaceholderCharacter('#');
            textFieldIp = new JFormattedTextField(formatter);
            textFieldIp.setColumns(15);
        } catch(Exception e) {
            e.printStackTrace();
        }*/

        labelPort = new JLabel("Porta:");
        labelPort.setForeground(Color.darkGray);
        labelPort.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldPort = new TextField("Inserisci una porta...", 15);
        textFieldPort.setForeground(Color.gray);
        textFieldPort.addMouseListener(textFieldPort);
        textFieldPort.addKeyListener(textFieldPort);
        textFieldPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c  = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        button = new JButton("Connetti!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameAreaFrame gameAreaFrame = new GameAreaFrame();
                Event event = new Event(this, "closeApp");

                if (evListener != null){
                    evListener.eventListener(event);
                }
            }
        });

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
        gbcUsrLabel.weighty = 0.02;

        add(labelUsr, gbcUsrLabel);

        GridBagConstraints gbcUsrField = new GridBagConstraints();

        gbcUsrField.gridx = 1;
        gbcUsrField.gridy = 1;

        gbcUsrField.weightx = 0.0;
        gbcUsrField.weighty = 0.02;

        gbcUsrField.insets = new Insets(0, 10, 0, 10);

        add(textFieldUsr, gbcUsrField);

        GridBagConstraints gbcIpLabel = new GridBagConstraints();

        gbcIpLabel.gridx = 0;
        gbcIpLabel.gridy = 2;

        gbcIpLabel.weightx = 0.0;
        gbcIpLabel.weighty = 0.02;

        add(labelIp, gbcIpLabel);

        GridBagConstraints gbcIpField = new GridBagConstraints();

        gbcIpField.gridx = 1;
        gbcIpField.gridy = 2;

        gbcIpField.weightx = 0.0;
        gbcIpField.weighty = 0.02;

        gbcIpField.insets = new Insets(0, 10, 0, 10);

        add(textFieldIp, gbcIpField);

        GridBagConstraints gbcPortLabel = new GridBagConstraints();

        gbcPortLabel.gridx = 0;
        gbcPortLabel.gridy = 3;

        gbcPortLabel.weightx = 0.0;
        gbcPortLabel.weighty = 0.02;

        add(labelPort, gbcPortLabel);

        GridBagConstraints gbcPortField = new GridBagConstraints();

        gbcPortField.gridx = 1;
        gbcPortField.gridy = 3;

        gbcPortField.weightx = 0.0;
        gbcPortField.weighty = 0.02;

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

    public void setEvListener(EvListener evListener){
        this.evListener = evListener;
    }
}
