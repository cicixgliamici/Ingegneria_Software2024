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
import java.util.ArrayList;
import java.util.List;

/**
 * A chat panel for the game, allowing users to send and receive messages.
 */
public class Chat extends JPanel {
    private TCPClient tcpClient;  // Client for handling TCP communication
    private JButton button;  // Button to send messages
    private static JTextPane textPane;  // Text pane to display messages
    private JTextField textField;  // Text field to input messages

    /**
     * Constructor to initialize the chat panel.
     *
     * @param tcpClient The TCP client for sending messages.
     * @param view The view of the game, used to retrieve players.
     * @param username The username of the current player.
     */
    public Chat(TCPClient tcpClient, View view, String username) {
        this.tcpClient = tcpClient;
        setLayout(new BorderLayout());

        // Initialize the text pane for displaying messages
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);

        button = new JButton("Send");  // Initialize the send button
        textField = new JTextField();  // Initialize the input text field

        // Add the text pane to a scroll pane
        JScrollPane sp = new JScrollPane(textPane);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(sp, BorderLayout.CENTER);

        // Create a panel to hold the text field and send button
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(textField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        // Action listener for the send button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username);
            }
        });

        // Action listener for pressing Enter in the text field
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username);
            }
        });

        List<String> names = new ArrayList<>(view.getColorPlayer().keySet());

        // Welcome message for the chat
        String mess1 = "Welcome everyone, this is the list of players:" + names;
        displayMessage("Server", mess1);
        String mess2 = "If you want to send a private message, type [username]";
        displayMessage("Server", mess2);
    }

    /**
     * Sends the message inputted by the user.
     *
     * @param username The username of the sender.
     */
    private void sendMessage(String username) {
        String message = textField.getText();
        if (!message.isEmpty()) {
            tcpClient.sendChat(username + "," + message);
            textField.setText("");  // Clear the input field after sending
        }
    }

    /**
     * Displays a message in the chat panel.
     *
     * @param username The username of the sender.
     * @param message The message to be displayed.
     */
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
