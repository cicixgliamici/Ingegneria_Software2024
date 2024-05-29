package org.example.view.gui.gamearea4;


import org.example.client.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JPanel {
    private TCPClient tcpClient;
    private JButton button;
    private JTextArea textArea;
    private JTextField textField;

    public Chat(TCPClient tcpClient , String username){
        this.tcpClient = tcpClient;
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        button = new JButton("Send");
        textField = new JTextField();

        textArea.setEditable(false);

        JScrollPane sp = new JScrollPane(textArea);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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

    private void sendMessage(String username) {
        String message = textField.getText();
        System.out.println("Send " + message);
        if (!message.isEmpty()) {
            tcpClient.sendChat(username + ";" + message);
            textField.setText("");  // Clear the input field after sending
        }
    }
    public void displayMessage(String message) {
        System.out.println("Display " + message);
        textArea.append(message + "\n");  // Append the new message with a newline
    }

}