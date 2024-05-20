package org.example.view.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Chat extends JPanel {
    private JButton button;
    private JTextArea textArea;
    private JTextField textField;

    public Chat(String username){
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        button = new JButton("Send");
        textField = new JTextField();

        textArea.setEditable(false);

        JScrollPane sp = new JScrollPane(textArea);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username);
            }
        });

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username);
            }
        });

    }

    private void sendMessage(String username){
        String message = textField.getText();
        if (!message.isEmpty()) {
            textArea.append(username + ": " + message + "\n");
            textField.setText("");
        }
    }

}


