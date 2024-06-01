package org.example.view.gui.mainmenu1;

import org.example.view.View;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.gamerules.GameRulesFrame;
import org.example.view.gui.setgame2.SetInitialGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame {

    BoxMenu boxMenu;
    SetInitialGame setInitialGame;
    private int connectionType;
    private String username;
    private View view;

    public MainMenu(int connectionType, View view) throws IOException {
        super("Codex Naturalis");
        this.connectionType = connectionType;
        this.view = view;

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/icon/iconamini.png");
        setIconImage(icon);

        boxMenu = new BoxMenu(connectionType, view) {
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
            public void eventListener(Event ev) throws IOException {
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
                    setInitialGame = new SetInitialGame(ev.getTcpClient(), ev.getData(), ev.getView()) {
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

                    setInitialGame.setEvListener(new EvListener() {
                        @Override
                        public void eventListener(Event ev) {
                            String event = ev.getEvent();
                            if (event.equals("close")) {
                                dispose();
                            }
                        }
                    });
                    setContentPane(setInitialGame);
                    validate();
                    repaint();
                }
            }
        });

        // Aggiungi un KeyBinding per il tasto Invio
        addKeyBinding(this.getRootPane(), KeyEvent.VK_ENTER, "CONFIRM", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Trova e attiva il pulsante di conferma
                Component[] components = boxMenu.getComponents();
                for (Component component : components) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        if (button.getText().equals("Conferma")) {
                            button.doClick();
                        }
                    }
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

    private void addKeyBinding(JComponent component, int keyCode, String name, Action action) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), name);
        actionMap.put(name, action);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOption = new JMenu("Option");
        menuOption.setMnemonic(KeyEvent.VK_O);

        JMenuItem menuItemExit = new JMenuItem("Exit", new ImageIcon("src/main/resources/images/icon/logout.png"));
        menuItemExit.setMnemonic(KeyEvent.VK_E);
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        JMenuItem minimizedIconItem = new JMenuItem("Minimized", new ImageIcon("src/main/resources/images/icon/minimize.png"));
        minimizedIconItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });

        menuOption.add(minimizedIconItem);
        menuOption.addSeparator();
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

        JMenuItem menuItemAbout = new JMenuItem("?", new ImageIcon("src/main/resources/images/icon/about_icon.png"));
        menuItemAbout.setMnemonic(KeyEvent.VK_I);

        JMenuItem menuItemRuleBook = new JMenuItem("Rule Book", new ImageIcon("src/main/resources/images/icon/rulesbook-icon.png"));
        menuItemRuleBook.setMnemonic(KeyEvent.VK_R);

        menuAbout.add(menuItemRuleBook);
        menuAbout.addSeparator();
        menuAbout.add(menuItemAbout);

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
