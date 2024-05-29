package org.example.view.gui.gamearea4;

import org.example.client.*;
import org.example.view.View;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JPanel {
    private TCPClient tcpClient;
    private JButton button;
    private static JTextPane textPane;
    private JTextField textField;

    public Chat(TCPClient tcpClient, View view, String username) {
        this.tcpClient = tcpClient;
        setLayout(new BorderLayout());

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);

        button = new JButton("Send");
        textField = new JTextField();

        JScrollPane sp = new JScrollPane(textPane);
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

        String mess1 = "Welcome everyone, this is the list of players:" + view.getPlayers();
        displayMessage("Server", mess1);
        String mess2 = "If you want to send a private messagge, type [username]";
        displayMessage("Server", mess2);
    }

    private void sendMessage(String username) {
        String message = textField.getText();
        if (!message.isEmpty()) {
            tcpClient.sendChat(username + "," + message);
            textField.setText("");  // Clear the input field after sending
        }
    }

    public static void displayMessage(String username, String message) {
        // Create the formatted message with HTML tags
        String formattedMessage = "<b>" + username + ":</b> " + message + "<br>";
        // Insert the HTML content into the text pane
        HTMLEditorKit kit = (HTMLEditorKit) textPane.getEditorKit();
        HTMLDocument doc = (HTMLDocument) textPane.getDocument();
        try {
            kit.insertHTML(doc, doc.getLength(), formattedMessage, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Scroll to the bottom of the text pane
        textPane.setCaretPosition(doc.getLength());
    }
}
