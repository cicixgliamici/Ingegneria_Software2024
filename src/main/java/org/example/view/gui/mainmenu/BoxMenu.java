package org.example.view.gui.mainmenu;

import org.example.client.TCPClient;
import org.example.view.ViewGUI;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * The BoxMenu class represents the main menu panel where users can enter their username, IP address, and port number to connect to the server.
 */
public class BoxMenu extends JPanel {
    private JButton button; // Button to initiate the connection
    private JLabel labelTitle; // Label for the title image
    private JLabel labelUsr; // Label for the username field
    private TextField textFieldUsr; // Text field for entering the username
    private JLabel labelIp; // Label for the IP address field
    private TextField textFieldIp; // Text field for entering the IP address
    private JLabel labelPort; // Label for the port number field
    private TextField textFieldPort; // Text field for entering the port number
    private EvListener evListener; // Event listener for handling custom events
    private int connectionType; // Connection type (0 for TCP, 1 for RMI)
    private TCPClient tcpClient;

    /**
     * Constructor for the BoxMenu class.
     *
     * @param connectionType The type of connection (0 for TCP, 1 for RMI).
     * @throws IOException if the logo image file cannot be found.
     */
    public BoxMenu(int connectionType) throws IOException {
        this.connectionType = connectionType;
        setLayout(new GridBagLayout());

        // Load the logo image
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

        // Initialize and style the username label and text field
        labelUsr = new JLabel("Username:");
        labelUsr.setForeground(Color.darkGray);
        labelUsr.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldUsr = new TextField("Enter a username...", 15);
        textFieldUsr.setForeground(Color.gray);
        textFieldUsr.addMouseListener(textFieldUsr);
        textFieldUsr.addKeyListener(textFieldUsr);

        // Initialize and style the IP address label and text field
        labelIp = new JLabel("Ip:");
        labelIp.setForeground(Color.darkGray);
        labelIp.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldIp = new TextField("Enter an IP address...", 15);
        textFieldIp.setForeground(Color.gray);
        textFieldIp.addKeyListener(textFieldIp);
        textFieldIp.addMouseListener(textFieldIp);

        // Initialize and style the port number label and text field
        labelPort = new JLabel("Port:");
        labelPort.setForeground(Color.darkGray);
        labelPort.setFont(new Font("Helvetica", Font.BOLD, 15));
        textFieldPort = new TextField("Enter a port number...", 15);
        textFieldPort.setForeground(Color.gray);
        textFieldPort.addMouseListener(textFieldPort);
        textFieldPort.addKeyListener(textFieldPort);
        textFieldPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Allow letters in the port field to trigger default port logic
            }
        });

        setEvListener(new EvListener() {
            @Override
            public void eventListener(Event event) {
                if (event.getEvent().equals("setInitialGame")) {
                    switchToPlayerSetupPanel();
                }
            }
        });

        // Initialize the connect button and add an action listener
        button = new JButton("Connect!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate the input fields
                if ((Objects.equals(textFieldUsr.getText(), "Enter a username...")) || textFieldUsr.getText().isEmpty()) {
                    Event event = new Event(this, "notValidUsr");
                    if (evListener != null) {
                        evListener.eventListener(event);
                    }
                } else if ((Objects.equals(textFieldIp.getText(), "Enter an IP address...")) || textFieldIp.getText().isEmpty()) {
                    Event event = new Event(this, "notValidIp");
                    if (evListener != null) {
                        evListener.eventListener(event);
                    }
                } else if ((Objects.equals(textFieldPort.getText(), "Enter a port number...")) || textFieldPort.getText().isEmpty()) {
                    Event event = new Event(this, "notValidPort");
                    if (evListener != null) {
                        evListener.eventListener(event);
                    }
                } else {
                    // Get the IP address and port number from the input fields
                    String ip = textFieldIp.getText();
                    String username = textFieldUsr.getText();
                    int port;
                    try {
                        port = Integer.parseInt(textFieldPort.getText());
                    } catch (NumberFormatException ex) {
                        ip = "127.0.0.1";  // Default IP
                        port = 50000;  // Default port
                    }
                    // Attempt to connect to the server
                    connectToServer(ip, port, username, connectionType);
                }
            }
        });

        // Layout configuration using GridBagConstraints
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.weightx = 0.0;
        gbcTitle.weighty = 0.3;
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

    /**
     * Sets the event listener for this menu.
     *
     * @param evListener The event listener to be set.
     */
    public void setEvListener(EvListener evListener) {
        this.evListener = evListener;
    }

    /**
     * Connects to the server using the specified IP address, port, username, and connection type.
     *
     * @param ip             The IP address of the server.
     * @param port           The port number of the server.
     * @param username       The username to be used.
     * @param connectionType The type of connection (0 for TCP, 1 for RMI).
     */
    private void connectToServer(String ip, int port, String username, int connectionType) {
        if (tcpClient != null && tcpClient.isConnected()) {
            JOptionPane.showMessageDialog(this, "You are already connected.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean connected = false;
        ViewGUI view = new ViewGUI();
        tcpClient = new TCPClient(ip, port, view);
        try {
            tcpClient.connect();
            connected = true;
            tcpClient.sendUsername(username);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect via TCP", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mostra il messaggio di successo e poi passa alla prossima schermata
        JOptionPane.showMessageDialog(this, "Connected successfully via TCP", "Success", JOptionPane.INFORMATION_MESSAGE);
        switchToPlayerSetupPanel(); // Cambio di schermata solo dopo il messaggio di successo
    }


    /**
     * Switches to the player setup panel within the application.
     * It triggers a custom event using the event listener if it's set, signaling other components to update accordingly.
     */
    private void switchToPlayerSetupPanel() {
        // Assumendo che ci sia un JFrame o un Container principale che ospiti i pannelli
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new PlayerSetupPanel());
        frame.validate();
        frame.repaint();
    }
}
