package org.example.view.GUI.mainmenu;

import org.example.view.GUI.listener.EvListener;
import org.example.view.GUI.listener.Event;
import org.example.view.GUI.gamerules.GameRulesFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JFrame {

    BoxMenu boxMenu;
    SetInitialGame setInitialGame;
    private int connectionType;
    private String username = "culo";

    public MainMenu(int connectionType) throws IOException {
        super("Codex Naturalis");
        this.connectionType = connectionType;

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);

        boxMenu = new BoxMenu(connectionType) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/background.png")));
            Image img = icon.getImage();
            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        setInitialGame = new SetInitialGame(username) {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/background.png")));
            Image img = icon.getImage();
            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        add(boxMenu, BorderLayout.CENTER);
        boxMenu.setEvListener(new EvListener() {
            @Override
            public void eventListener(Event ev) {
                String event = ev.getEvent();
                if (event.equals("closeApp")) {
                    dispose();
                } else if (event.equals("notValidUsr")) {
                    JOptionPane.showMessageDialog(null, "Error! Please enter a valid Username.", "Error!", JOptionPane.ERROR_MESSAGE);
                } else if (event.equals("notValidIp")) {
                    JOptionPane.showMessageDialog(null, "Error! Please enter a valid IP address.", "Error!", JOptionPane.ERROR_MESSAGE);
                } else if (event.equals("notValidPort")) {
                    JOptionPane.showMessageDialog(null, "Error! Please enter a valid port number.", "Error!", JOptionPane.ERROR_MESSAGE);
                } else if (event.equals("setInitialGame")) {
                    remove(boxMenu);
                    add(setInitialGame, BorderLayout.CENTER);
                }
            }
        });

        pack();
        setSize(810, 660);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOption = new JMenu("Option");
        menuOption.setMnemonic(KeyEvent.VK_O);

        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.setMnemonic(KeyEvent.VK_E);
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        menuOption.add(menuItemExit);

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainMenu.this, "Vuoi uscire dall'applicazione?", "Chiusura Applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (action == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        JMenu menuAbout = new JMenu("About");
        menuAbout.setMnemonic(KeyEvent.VK_A);

        JMenuItem menuItemAbout = new JMenuItem("?", new ImageIcon("src/main/resources/images/about_icon.png"));
        menuItemAbout.setMnemonic(KeyEvent.VK_I);

        JMenuItem menuItemRuleBook = new JMenuItem("Rule Book");
        menuItemRuleBook.setMnemonic(KeyEvent.VK_R);

        menuAbout.add(menuItemRuleBook);
        menuAbout.addSeparator();
        menuAbout.add(menuItemAbout);

        // AboutFrame
        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame("About");
                JPanel aboutPanel = new JPanel();

                aboutPanel.setPreferredSize(new Dimension(300, 200));
                Border insideBorder = BorderFactory.createTitledBorder("Informazioni");
                Border outsideBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
                Border finalBorder = BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
                aboutPanel.setBorder(finalBorder);
                JTextArea creditsTextPane = new JTextArea();

                creditsTextPane.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        List<String> strings = new ArrayList<>();
                        strings.add("c");
                        strings.add("u");
                        strings.add("l");
                        strings.add("o");
                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                    }
                });

                GridBagConstraints gbcCreditsLabel = new GridBagConstraints();

                gbcCreditsLabel.gridx = 0;
                gbcCreditsLabel.gridy = 0;

                gbcCreditsLabel.weightx = 0.0;
                gbcCreditsLabel.weighty = 0.9;
                aboutPanel.setLayout(new GridBagLayout());
                aboutPanel.add(creditsTextPane, gbcCreditsLabel);

                aboutFrame.setLayout(new BorderLayout());
                aboutFrame.add(aboutPanel);

                aboutFrame.pack();
                aboutFrame.setSize(300, 200);
                aboutFrame.setResizable(false);
                aboutFrame.setLocationRelativeTo(null);
                aboutFrame.setVisible(true);
            }
        });

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

        menuBar.add(menuOption);
        menuBar.add(menuAbout);

        return menuBar;
    }
}
