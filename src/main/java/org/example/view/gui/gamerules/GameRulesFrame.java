package org.example.view.gui.gamerules;

import org.example.client.TCPClient;
import org.example.view.gui.setgame2.SetInitialGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * A JFrame subclass for displaying game rules as a series of images.
 * Users can navigate through images using left and right arrow keys.
 */
public class GameRulesFrame extends JFrame {

    private JPanel imagePanelIta; // Panel to display images in Italian
    private JPanel imagePanelEng; // Panel to display images in English
    private JScrollPane scrollPaneIta; // Scroll pane to contain the Italian image panel
    private JScrollPane scrollPaneEng; // Scroll pane to contain the English image panel
    private ImageIcon[] imagesIta; // Array to hold the images for Italian rules
    private ImageIcon[] imagesEng; // Array to hold the images for English rules
    private boolean activeIta = true; // Flag to indicate if Italian rules are active
    private boolean activeEng = false; // Flag to indicate if English rules are active
    private int currentIndex = 0; // Current index of the displayed image

    /**
     * Constructs a GameRulesFrame, loading images and setting up the UI components.
     */
    public GameRulesFrame() throws IOException {
        super("Game Rules");
        setSize(714, 740); // Set the size of the frame
        setLayout(new BorderLayout()); // Use BorderLayout for layout management
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/rulebook/ita/01.png");
        setIconImage(icon); // Set the icon image of the frame

        // Load all images for the Italian rules into an array
        imagesIta = new ImageIcon[12];
        for (int i = 0; i < imagesIta.length; i++) {
            imagesIta[i] = new ImageIcon("src/main/resources/images/rulebook/ita/" + String.format("%02d", i + 1) + ".png");
        }

        // Load all images for the English rules into an array
        imagesEng = new ImageIcon[12];
        for (int i = 0; i < imagesEng.length; i++) {
            imagesEng[i] = new ImageIcon("src/main/resources/images/rulebook/eng/" + String.format("%02d", i + 1) + ".jpg");
        }

        // Initialize the Italian image panel and override its paintComponent to draw the current image
        imagePanelIta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                imagesIta[currentIndex].paintIcon(this, g, 0, 0); // Draw the current image at position (0,0)
            }

            @Override
            public Dimension getPreferredSize() {
                // Dynamically adjust size based on the current image
                return new Dimension(imagesIta[currentIndex].getIconWidth(), imagesIta[currentIndex].getIconHeight());
            }
        };

        // Setup the scroll pane to house the Italian image panel
        scrollPaneIta = new JScrollPane(imagePanelIta);
        scrollPaneIta.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneIta.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPaneIta, BorderLayout.CENTER); // Add the scrollPane to the center of the frame

        // Initialize the English image panel and override its paintComponent to draw the current image
        imagePanelEng = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                imagesEng[currentIndex].paintIcon(this, g, 0, 0); // Draw the current image at position (0,0)
            }

            @Override
            public Dimension getPreferredSize() {
                // Dynamically adjust size based on the current image
                return new Dimension(imagesEng[currentIndex].getIconWidth(), imagesEng[currentIndex].getIconHeight());
            }
        };

        // Setup the scroll pane to house the English image panel
        scrollPaneEng = new JScrollPane(imagePanelEng);
        scrollPaneEng.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneEng.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getContentPane().add(scrollPaneEng, BorderLayout.CENTER); // Add the scrollPane to the center of the frame

        // Add a key listener to the frame to handle left and right arrow keys for navigation
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    if (activeIta && !activeEng) {
                        if (currentIndex != 0) {
                            currentIndex = (currentIndex - 1 + imagesIta.length) % imagesIta.length;
                            imagePanelIta.repaint(); // Repaint to show the previous image
                        }
                    } else if (!activeIta && activeEng) {
                        if (currentIndex != 0) {
                            currentIndex = (currentIndex - 1 + imagesEng.length) % imagesEng.length;
                            imagePanelEng.repaint(); // Repaint to show the previous image
                        }
                    }
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    if (activeIta && !activeEng) {
                        if (currentIndex < imagesIta.length - 1) {
                            currentIndex++; // Increment the index to show the next image
                            imagePanelIta.repaint(); // Repaint to update the display
                        }
                    } else if (!activeIta && activeEng) {
                        if (currentIndex < imagesEng.length - 1) {
                            currentIndex++; // Increment the index to show the next image
                            imagePanelEng.repaint(); // Repaint to update the display
                        }
                    }
                }
            }
        });

        setContentPane(scrollPaneIta); // Set the initial content pane to the Italian scroll pane
        setJMenuBar(createMenuBar()); // Set the menu bar
        setFocusable(true); // Make the frame focusable to receive key events
        requestFocus(); // Request focus to receive key events
        setResizable(false); // Make the frame non-resizable
        setVisible(true); // Make the frame visible

        // Show information dialog instructing the user on how to navigate
        String information = "Please use the arrow keys to navigate.";
        JOptionPane.showMessageDialog(null, information, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Switches the content pane to the specified scroll pane and updates active flags.
     *
     * @param scrollPane The scroll pane to switch to.
     * @param eng        Flag indicating if English rules are active.
     * @param ita        Flag indicating if Italian rules are active.
     */
    private void switchPanel(JScrollPane scrollPane, boolean eng, boolean ita) {
        setContentPane(scrollPane);
        validate();
        repaint();
        setActiveEng(eng);
        setActiveIta(ita);
    }

    /**
     * Creates the menu bar with options to switch between Italian and English rules.
     *
     * @return The constructed JMenuBar.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOption = new JMenu("Option");
        menuOption.setMnemonic(KeyEvent.VK_O);

        JRadioButtonMenuItem chooseEng = new JRadioButtonMenuItem("English");
        JRadioButtonMenuItem chooseIta = new JRadioButtonMenuItem("Italian");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(chooseEng);
        buttonGroup.add(chooseIta);

        chooseIta.setSelected(true); // Default selection to Italian

        chooseEng.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(scrollPaneEng, true, false);
            }
        });

        chooseIta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanel(scrollPaneIta, false, true);
            }
        });

        menuOption.add(chooseEng);
        menuOption.add(chooseIta);

        menuBar.add(menuOption);

        return menuBar;
    }

    /**
     * Sets the active state for Italian rules.
     *
     * @param activeIta Flag indicating if Italian rules are active.
     */
    public void setActiveIta(boolean activeIta) {
        this.activeIta = activeIta;
    }

    /**
     * Sets the active state for English rules.
     *
     * @param activeEng Flag indicating if English rules are active.
     */
    public void setActiveEng(boolean activeEng) {
        this.activeEng = activeEng;
    }
}
