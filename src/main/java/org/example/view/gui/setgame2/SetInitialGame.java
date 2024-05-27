package org.example.view.gui.setgame2;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.selectobjstarter3.SelectObjStarter;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;



public class SetInitialGame extends JPanel {
    private EvListener evListener;
    private TCPClient tcpClient;  // Aggiungi il riferimento al TCPClient
    private String username;
    private JLabel labelTitle;
    private View view;

    public SetInitialGame(TCPClient tcpClient, String username, View view) {
        this.tcpClient = tcpClient;
        this.username = username;
        this.view = view;

        setLayout(new GridBagLayout());
        //Components
        JPanel chooseColorPanel = new JPanel(new GridBagLayout());
        JPanel setNumberPlayerPanel = new JPanel(new GridBagLayout());

        JButton confirmButton = new JButton("Confirm!");

        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("/images/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Icon icon = new ImageIcon(logo);

        labelTitle = new JLabel(icon);
        //Setting chooseColorPanel
        JRadioButton redRadioButton = new JRadioButton("Red");
        redRadioButton.setActionCommand("Red");
        JRadioButton greenRadioButton = new JRadioButton("Green");
        greenRadioButton.setActionCommand("Green");
        JRadioButton yellowRadioButton = new JRadioButton("Yellow");
        yellowRadioButton.setActionCommand("Yellow");
        JRadioButton blueRadioButton = new JRadioButton("Blue");
        blueRadioButton.setActionCommand("Blue");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(redRadioButton);
        buttonGroup.add(greenRadioButton);
        buttonGroup.add(yellowRadioButton);
        buttonGroup.add(blueRadioButton);



        Border insideChooseColorBorder = BorderFactory.createTitledBorder("Choose a Color");
        Border outsideBorder = BorderFactory.createEmptyBorder(20,20,20,20);
        Border finalChooseColorBorder = BorderFactory.createCompoundBorder(insideChooseColorBorder, outsideBorder);
        chooseColorPanel.setBorder(finalChooseColorBorder);

        //chooseColorPanel.setOpaque(false);

        //Layout of chooseColorPanel
        GridBagConstraints gbcRed = new GridBagConstraints();

        gbcRed.gridy = 0;
        gbcRed.gridx = 0;

        gbcRed.weighty = 0.2;
        gbcRed.weightx = 0.0;

        gbcRed.anchor = GridBagConstraints.LINE_START;

        chooseColorPanel.add(redRadioButton, gbcRed);

        GridBagConstraints gbcGreen = new GridBagConstraints();

        gbcGreen.gridy = 1;
        gbcGreen.gridx = 0;

        gbcGreen.weighty = 0.2;
        gbcGreen.weightx = 0.0;

        gbcGreen.anchor = GridBagConstraints.LINE_START;

        chooseColorPanel.add(greenRadioButton, gbcGreen);

        GridBagConstraints gbcYellow = new GridBagConstraints();

        gbcYellow.gridy = 0;
        gbcYellow.gridx = 1;

        gbcYellow.weighty = 0.2;
        gbcYellow.weightx = 0.0;

        gbcYellow.anchor = GridBagConstraints.LINE_START;

        chooseColorPanel.add(yellowRadioButton, gbcYellow);

        GridBagConstraints gbcBlue = new GridBagConstraints();

        gbcBlue.gridy = 1;
        gbcBlue.gridx = 1;

        gbcBlue.weighty = 0.2;
        gbcBlue.weightx = 0.0;

        gbcBlue.anchor = GridBagConstraints.LINE_START;

        chooseColorPanel.add(blueRadioButton, gbcBlue);

        //Setting setNumberPlayerPanel
        JLabel labelNumPlayer = new JLabel("Number of players: ");
        labelNumPlayer.setForeground(Color.darkGray);
        String[] optionsNumPlayer = {"1", "2", "3", "4"};
        JComboBox menuNumPlayer = new JComboBox(optionsNumPlayer);
        //Border insideNumPlayerBorder = BorderFactory.createLineBorder(Color.BLACK);
        //insideNumPlayerBorder = BorderFactory.createTitledBorder(insideNumPlayerBorder, "Choose the number of players");
        Border insideNumPlayerBorder = BorderFactory.createTitledBorder("Choose number of players");
        Border outsideNumPlayerBorder = BorderFactory.createEmptyBorder(20,20,20,20);
        Border finalNumPlayerBorder = BorderFactory.createCompoundBorder(insideNumPlayerBorder, outsideBorder);
        setNumberPlayerPanel.setBorder(finalNumPlayerBorder);
        setNumberPlayerPanel.setPreferredSize(new Dimension(180,112));
        //setNumberPlayerPanel.setOpaque(false);

        //Layout SetNumberPlayer
        GridBagConstraints gbcNumPlayerLabel = new GridBagConstraints();

        gbcNumPlayerLabel.gridy = 0;
        gbcNumPlayerLabel.gridx = 0;
        gbcNumPlayerLabel.insets = new Insets(0,10,0,10);

        setNumberPlayerPanel.add(labelNumPlayer, gbcNumPlayerLabel);

        GridBagConstraints gbcMenuNumPlayer = new GridBagConstraints();

        gbcMenuNumPlayer.gridy = 0;
        gbcMenuNumPlayer.gridx = 1;

        setNumberPlayerPanel.add(menuNumPlayer, gbcMenuNumPlayer);


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (buttonGroup.getSelection() != null) {
                        String num = menuNumPlayer.getSelectedItem().toString();
                        String color = buttonGroup.getSelection().getActionCommand();
                        // Invio dei dati al server tramite TCPClient
                        tcpClient.sendColorAndNumPlayers(color, num);
                        new SelectObjStarter(tcpClient, username, view, color, num);
                        Event event = new Event(this, "close");
                        if (evListener != null) {
                            try {
                                evListener.eventListener(event);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error! Please pick a color.", "Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        GridBagConstraints gbcTitle = new GridBagConstraints();

        gbcTitle.gridy = 0;
        gbcTitle.gridx = 0;

        gbcTitle.weightx = 0.0;
        gbcTitle.weighty = 0.4;

        gbcTitle.gridheight = 1;
        gbcTitle.gridwidth = 2;

        add(labelTitle, gbcTitle);

        GridBagConstraints gbcChooseColor = new GridBagConstraints();

        gbcChooseColor.gridy = 1;
        gbcChooseColor.gridx = 0;

        gbcChooseColor.weighty = 0.1;
        gbcChooseColor.weightx = 0.0;

        gbcChooseColor.insets = new Insets(0,0,0,10);

        add(chooseColorPanel, gbcChooseColor);

        GridBagConstraints gbcSetNumberPlayer = new GridBagConstraints();

        gbcSetNumberPlayer.gridy = 1;
        gbcSetNumberPlayer.gridx = 1;

        gbcSetNumberPlayer.weighty = 0.1;
        gbcSetNumberPlayer.weightx = 0.0;

        gbcSetNumberPlayer.insets = new Insets(0,10,0,0);

        add(setNumberPlayerPanel, gbcSetNumberPlayer);

        GridBagConstraints gbcConfirmButton = new GridBagConstraints();

        gbcConfirmButton.gridy = 2;
        gbcConfirmButton.gridx = 0;

        gbcConfirmButton.weighty = 0.7;
        gbcConfirmButton.weightx = 0.0;

        gbcConfirmButton.gridheight = 1;
        gbcConfirmButton.gridwidth = 2;

        add(confirmButton, gbcConfirmButton);
    }

    /**
     * Sets the event listener for this menu.
     *
     * @param evListener The event listener to be set.
     */
    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    public TCPClient getTcpClient() {
        return tcpClient;
    }

    public void setTcpClient(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
