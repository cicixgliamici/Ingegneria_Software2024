package org.example.view.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Chat extends JPanel {
    private JButton button;
    private JTextArea textArea;
    private JTextField textField;

    public Chat(){
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        button = new JButton("Invio");
        textField = new JTextField();

        textArea.setEditable(false);

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });


        /*GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.weighty = 0.9;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(textArea, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.05;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(textField, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.05;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(button, gbc);*/


    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            textArea.append(message + "\n");
            textField.setText("");
        }
    }

}


