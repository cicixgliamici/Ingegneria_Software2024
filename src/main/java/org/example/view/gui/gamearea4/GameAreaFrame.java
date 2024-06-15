package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.About;
import org.example.view.gui.gamerules.GameRulesFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Main frame for the Codex Naturalis game. This frame includes components for the game area, scoreboard, chat, and drawing card panel.
 */
public class GameAreaFrame extends JFrame {
    private GameAreaPanel gameAreaPanel; // Panel for the main game area
    private ScoreboardPanel scoreboardPanel; // Panel for displaying the scoreboard
    private Chat chat; // Panel for the chat
    private View view; // Reference to the view for accessing game state
    private DrawingCardPanel drawingCardPanel; // Panel for drawing cards
    private String starterCard; // Starter card for the player
    private String objCard; // Objective card for the player

    /**
     * Constructor to initialize the main game area frame.
     *
     * @param tcpClient The TCP client for server communication.
     * @param view The view for accessing game state.
     * @param username The player's username.
     * @param color The player's color.
     * @param num The player's number.
     * @param starterCard The player's starter card.
     * @param objCard The player's objective card.
     * @throws IOException If an error occurs during image loading.
     */
    public GameAreaFrame(TCPClient tcpClient, View view, String username, String color, String num, String starterCard, String objCard) throws IOException {
        super("Codex Naturalis" + "[" + username + "]");
        this.starterCard = starterCard;
        this.objCard = objCard;
        this.view = view;
        setSize(1900, 1000);

        // Set the window icon
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon/iconamini.png");
        setIconImage(icon);

        // Set the layout manager for the frame
        setLayout(new GridBagLayout());

        // Create and set the menu bar
        setJMenuBar(createMenuBar());

        GridBagConstraints gbc = new GridBagConstraints();

        // Add the scoreboard panel
        scoreboardPanel = new ScoreboardPanel(view);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx = 0.55;
        gbc.fill = GridBagConstraints.BOTH;
        add(scoreboardPanel, gbc);

        // Add the game area panel
        gameAreaPanel = new GameAreaPanel(tcpClient, view, color, num, starterCard, objCard, username);
        view.setGameAreaPanel(gameAreaPanel);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        add(gameAreaPanel, gbc);

        // Add the drawing card panel
        gbc.gridx = 2;
        gbc.weightx = 0.05;
        drawingCardPanel = new DrawingCardPanel(tcpClient, view);
        view.setDrawingCardPanel(drawingCardPanel);
        add(drawingCardPanel, gbc);

        // Add the chat panel
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx = 0.17;
        gbc.fill = GridBagConstraints.BOTH;
        chat = new Chat(tcpClient, view, username);
        add(chat, gbc);

        // Set frame properties
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Creates the menu bar with options and about sections.
     *
     * @return The constructed JMenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Create the options menu
        JMenu menuOption = new JMenu("Option");
        menuOption.setMnemonic(KeyEvent.VK_O);

        // Exit menu item
        JMenuItem menuItemExit = new JMenuItem("Exit", new ImageIcon("src/main/resources/images/icon/logout.png"));
        menuItemExit.setMnemonic(KeyEvent.VK_E);
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(GameAreaFrame.this, "Vuoi uscire dall'applicazione?", "Chiusura Applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Minimize menu item
        JMenuItem minimizedIconItem = new JMenuItem("Minimized", new ImageIcon("src/main/resources/images/icon/minimize.png"));
        minimizedIconItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });

        // Add items to the options menu
        menuOption.add(minimizedIconItem);
        menuOption.addSeparator();
        menuOption.add(menuItemExit);

        // Create the about menu
        JMenu menuAbout = new JMenu("About");
        menuAbout.setMnemonic(KeyEvent.VK_A);

        // About menu item
        JMenuItem menuItemAbout = new JMenuItem("?", new ImageIcon("src/main/resources/images/icon/about_icon.png"));
        menuItemAbout.setMnemonic(KeyEvent.VK_I);
        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new About();
            }
        });

        // Rule book menu item
        JMenuItem menuItemRuleBook = new JMenuItem("Rule Book", new ImageIcon("src/main/resources/images/icon/rulesbook-icon.png"));
        menuItemRuleBook.setMnemonic(KeyEvent.VK_R);
        menuItemRuleBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameRulesFrame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Add items to the about menu
        menuAbout.add(menuItemRuleBook);
        menuAbout.addSeparator();
        menuAbout.add(menuItemAbout);

        // Add menus to the menu bar
        menuBar.add(menuOption);
        menuBar.add(menuAbout);

        return menuBar;
    }
}
