package org.example.view.GUI.mainmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerSetupPanel extends JPanel {
    private JLabel labelColor;
    private JComboBox<String> colorComboBox;
    private JLabel labelNumPlayers;
    private JTextField textFieldNumPlayers;
    private JButton submitButton;

    public PlayerSetupPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label and ComboBox for color selection
        labelColor = new JLabel("Color:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelColor, gbc);

        colorComboBox = new JComboBox<>(new String[]{"Red", "Blue", "Green", "Yellow"});
        gbc.gridx = 1;
        add(colorComboBox, gbc);

        // Label and TextField for number of players
        labelNumPlayers = new JLabel("Number of Players:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelNumPlayers, gbc);

        textFieldNumPlayers = new JTextField(10);
        gbc.gridx = 1;
        add(textFieldNumPlayers, gbc);

        // Submit button
        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle submit action here
                String selectedColor = (String) colorComboBox.getSelectedItem();
                String numPlayers = textFieldNumPlayers.getText();
                JOptionPane.showMessageDialog(PlayerSetupPanel.this,
                        "Color: " + selectedColor + "\nNumber of Players: " + numPlayers,
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
