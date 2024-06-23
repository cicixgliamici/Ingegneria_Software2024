package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.About;
import org.example.view.gui.gamerules.GameRulesFrame;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

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
        view.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) throws IOException {
                String event = ev.getEvent();
                if(event.equals("winner")) {
                    disableInteractiveComponents();
                }
            }
        });
        setSize(1900, 860);

        // Set the window icon
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("images/icon/iconamini.png")) {
            if (iconStream != null) {
                Image icon = ImageIO.read(iconStream);
                setIconImage(icon);
            } else {
                throw new IOException("Icon image file not found!");
            }
        }

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
        JMenuItem menuItemExit = createMenuItem("Exit", "images/icon/logout.png", KeyEvent.VK_E, "E", e -> {
            int action = JOptionPane.showConfirmDialog(GameAreaFrame.this, "Vuoi uscire dall'applicazione?", "Chiusura Applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (action == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        // Minimize menu item
        JMenuItem minimizedIconItem = createMenuItem("Minimized", "images/icon/minimize.png", -1, null, e -> setExtendedState(JFrame.ICONIFIED));

        // Add items to the options menu
        menuOption.add(minimizedIconItem);
        menuOption.addSeparator();
        menuOption.add(menuItemExit);

        // Create the about menu
        JMenu menuAbout = new JMenu("About");
        menuAbout.setMnemonic(KeyEvent.VK_A);

        // About menu item
        JMenuItem menuItemAbout = createMenuItem("?", "images/icon/about_icon.png", KeyEvent.VK_I, null, e -> new About());

        // Rule book menu item
        JMenuItem menuItemRuleBook = createMenuItem("Rule Book", "images/icon/rulesbook-icon.png", KeyEvent.VK_R, null, e -> {
            try {
                new GameRulesFrame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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

    /**
     * Creates a menu item with the specified properties.
     *
     * @param text The text of the menu item.
     * @param iconPath The path to the icon image.
     * @param mnemonic The mnemonic key for the menu item.
     * @param accelerator The accelerator key for the menu item.
     * @param actionListener The action listener for the menu item.
     * @return The created JMenuItem.
     */
    private JMenuItem createMenuItem(String text, String iconPath, int mnemonic, String accelerator, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(text, loadIcon(iconPath));
        if (mnemonic != -1) {
            menuItem.setMnemonic(mnemonic);
        }
        if (accelerator != null) {
            menuItem.setAccelerator(KeyStroke.getKeyStroke(accelerator));
        }
        menuItem.addActionListener(actionListener);
        return menuItem;
    }

    /**
     * Loads an icon from the specified path.
     *
     * @param path The path to the icon image.
     * @return The loaded ImageIcon.
     */
    private ImageIcon loadIcon(String path) {
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (iconStream != null) {
                return new ImageIcon(loadImage(path));
            } else {
                throw new IOException("Icon image file not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Disables all interactive components in the game area frame.
     */
    private void disableInteractiveComponents() {
        disableComponents(this);
    }

    /**
     * Recursively disables all components within the given container.
     *
     * @param container The container whose components are to be disabled.
     */
    private void disableComponents(Container container) {
        for (Component component : container.getComponents()) {
            component.setEnabled(false);
            if (component instanceof Container) {
                disableComponents((Container) component);
            }
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + path);
        }
        return ImageIO.read(inputStream);
    }
}
