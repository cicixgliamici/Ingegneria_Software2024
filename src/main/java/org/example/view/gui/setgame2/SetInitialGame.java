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
import java.io.InputStream;

/**
 * The SetInitialGame class represents a panel where the user sets up the initial game parameters.
 * The user can choose a color and the number of players.
 */
public class SetInitialGame extends JPanel {
    private EvListener evListener;
    private TCPClient tcpClient;  // Reference to TCPClient for communication
    private String username;
    private JLabel labelTitle;
    private View view;

    /**
     * Constructs a SetInitialGame panel.
     *
     * @param tcpClient The TCP client for communication.
     * @param username  The username of the player.
     * @param view      The view instance.
     */
    public SetInitialGame(TCPClient tcpClient, String username, View view) {
        this.tcpClient = tcpClient;
        this.username = username;
        this.view = view;

        setLayout(new GridBagLayout());

        // Components
        JPanel chooseColorPanel = new JPanel(new GridBagLayout());
        JPanel setNumberPlayerPanel = new JPanel(new GridBagLayout());
        JButton confirmButton = new JButton("Confirm!");

        // Load the logo image
        BufferedImage logo = null;
        try (InputStream logoStream = getClass().getClassLoader().getResourceAsStream("images/logo.png")) {
            if (logoStream != null) {
                logo = ImageIO.read(logoStream);
            } else {
                throw new IOException("Logo image file not found!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Icon icon = new ImageIcon(logo);
        labelTitle = new JLabel(icon);

        // Setting up chooseColorPanel
        JRadioButton redRadioButton = createRadioButton("Red", view.getColors().contains("Red"));
        JRadioButton greenRadioButton = createRadioButton("Green", view.getColors().contains("Green"));
        JRadioButton yellowRadioButton = createRadioButton("Yellow", view.getColors().contains("Yellow"));
        JRadioButton blueRadioButton = createRadioButton("Blue", view.getColors().contains("Blue"));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(redRadioButton);
        buttonGroup.add(greenRadioButton);
        buttonGroup.add(yellowRadioButton);
        buttonGroup.add(blueRadioButton);

        Border insideChooseColorBorder = BorderFactory.createTitledBorder("Choose a Color");
        Border outsideBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border finalChooseColorBorder = BorderFactory.createCompoundBorder(insideChooseColorBorder, outsideBorder);
        chooseColorPanel.setBorder(finalChooseColorBorder);

        // Layout of chooseColorPanel
        layoutChooseColorPanel(chooseColorPanel, redRadioButton, greenRadioButton, yellowRadioButton, blueRadioButton);

        // Setting up setNumberPlayerPanel
        JLabel labelNumPlayer = new JLabel("Number of players: ");
        labelNumPlayer.setForeground(Color.darkGray);
        String[] optionsNumPlayer = {"2", "3", "4"};
        JComboBox<String> menuNumPlayer = new JComboBox<>(optionsNumPlayer);
        Border insideNumPlayerBorder = BorderFactory.createTitledBorder("Choose number of players");
        Border finalNumPlayerBorder = BorderFactory.createCompoundBorder(insideNumPlayerBorder, outsideBorder);
        setNumberPlayerPanel.setBorder(finalNumPlayerBorder);
        setNumberPlayerPanel.setPreferredSize(new Dimension(180, 112));

        // Enable or disable based on whether the user is the first player
        setNumberPlayerPanel.setEnabled(view.isFirst());
        menuNumPlayer.setEnabled(view.isFirst());
        labelNumPlayer.setEnabled(view.isFirst());

        if (!menuNumPlayer.isEnabled()) {
            menuNumPlayer.setSelectedIndex(view.getNumConnection() - 2);
        }

        // Layout setNumberPlayerPanel
        layoutSetNumberPlayerPanel(setNumberPlayerPanel, labelNumPlayer, menuNumPlayer);

        // Add action listener to the confirm button
        confirmButton.addActionListener(new ConfirmButtonActionListener(buttonGroup, menuNumPlayer));

        // Layout main panel
        layoutMainPanel(chooseColorPanel, setNumberPlayerPanel, confirmButton);
    }

    /**
     * Creates a JRadioButton with the specified label and enabled state.
     *
     * @param label  The label for the radio button.
     * @param enabled Whether the radio button is enabled.
     * @return The created JRadioButton.
     */
    private JRadioButton createRadioButton(String label, boolean enabled) {
        JRadioButton radioButton = new JRadioButton(label);
        radioButton.setActionCommand(label);
        radioButton.setEnabled(enabled);
        return radioButton;
    }

    /**
     * Layouts the choose color panel with the radio buttons.
     *
     * @param panel The panel to layout.
     * @param redRadioButton The red color radio button.
     * @param greenRadioButton The green color radio button.
     * @param yellowRadioButton The yellow color radio button.
     * @param blueRadioButton The blue color radio button.
     */
    private void layoutChooseColorPanel(JPanel panel, JRadioButton redRadioButton, JRadioButton greenRadioButton, JRadioButton yellowRadioButton, JRadioButton blueRadioButton) {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(redRadioButton, gbc);

        gbc.gridy = 1;
        panel.add(greenRadioButton, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        panel.add(yellowRadioButton, gbc);

        gbc.gridy = 1;
        panel.add(blueRadioButton, gbc);
    }

    /**
     * Layouts the set number player panel with the label and combo box.
     *
     * @param panel The panel to layout.
     * @param label The label for the number of players.
     * @param comboBox The combo box for selecting the number of players.
     */
    private void layoutSetNumberPlayerPanel(JPanel panel, JLabel label, JComboBox<String> comboBox) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridy = 0;
        gbcLabel.gridx = 0;
        gbcLabel.insets = new Insets(0, 10, 0, 10);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcComboBox = new GridBagConstraints();
        gbcComboBox.gridy = 0;
        gbcComboBox.gridx = 1;
        panel.add(comboBox, gbcComboBox);
    }

    /**
     * Layouts the main panel with the title, choose color panel, and set number player panel.
     *
     * @param chooseColorPanel The choose color panel.
     * @param setNumberPlayerPanel The set number player panel.
     * @param confirmButton The confirm button.
     */
    private void layoutMainPanel(JPanel chooseColorPanel, JPanel setNumberPlayerPanel, JButton confirmButton) {
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
        gbcChooseColor.insets = new Insets(0, 0, 0, 10);
        add(chooseColorPanel, gbcChooseColor);

        GridBagConstraints gbcSetNumberPlayer = new GridBagConstraints();
        gbcSetNumberPlayer.gridy = 1;
        gbcSetNumberPlayer.gridx = 1;
        gbcSetNumberPlayer.weighty = 0.1;
        gbcSetNumberPlayer.insets = new Insets(0, 10, 0, 0);
        add(setNumberPlayerPanel, gbcSetNumberPlayer);

        GridBagConstraints gbcConfirmButton = new GridBagConstraints();
        gbcConfirmButton.gridy = 2;
        gbcConfirmButton.gridx = 0;
        gbcConfirmButton.weighty = 0.7;
        gbcConfirmButton.gridheight = 1;
        gbcConfirmButton.gridwidth = 2;
        add(confirmButton, gbcConfirmButton);
    }

    /**
     * Action listener for the confirm button.
     */
    private class ConfirmButtonActionListener implements ActionListener {
        private final ButtonGroup buttonGroup;
        private final JComboBox<String> menuNumPlayer;

        ConfirmButtonActionListener(ButtonGroup buttonGroup, JComboBox<String> menuNumPlayer) {
            this.buttonGroup = buttonGroup;
            this.menuNumPlayer = menuNumPlayer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (buttonGroup.getSelection() != null) {
                    String num = menuNumPlayer.getSelectedItem().toString();
                    String color = buttonGroup.getSelection().getActionCommand();
                    // Send data to the server via TCPClient
                    tcpClient.sendColorAndNumPlayers(color, num);
                    if (view.isMatchStarted()) {
                        new SelectObjStarter(tcpClient, username, view, color, num);
                    } else {
                        WaitingScreen waitingScreen = new WaitingScreen();
                        new Thread(() -> {
                            while (!view.isMatchStarted()) {
                                try {
                                    Thread.sleep(1000); // Check every second
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            System.out.println("Match started!"); // Debug
                            SwingUtilities.invokeLater(() -> {
                                waitingScreen.dispose();
                                try {
                                    new SelectObjStarter(tcpClient, username, view, color, num);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            });
                        }).start();
                    }
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
    }

    /**
     * Sets the event listener for this menu.
     *
     * @param evListener The event listener to be set.
     */
    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    // Getter and setter methods for TCPClient, username, and view

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
