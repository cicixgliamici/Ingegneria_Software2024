package org.example.view.gui;



import org.example.view.gui.gamerules.GameRulesFrame;
import org.example.view.gui.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameAreaFrame extends JFrame {
    GameAreaPanel gameAreaPanel;
    ScoreboardPanel scoreboardPanel;
    Chat chat;
    DrawingCardPanel drawingCardPanel;
    public GameAreaFrame(String username, String color, String num) throws IOException {
        super("Codex Naturalis");
        //setSize(1600, 860);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);
        setLayout(new GridBagLayout());

        setJMenuBar(createMenuBar());

        GridBagConstraints gbc = new GridBagConstraints();

        scoreboardPanel = new ScoreboardPanel()/*{
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/plateau.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        }*/;

        gbc.gridx=0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx=0.03;
        gbc.fill = GridBagConstraints.BOTH;
        add(scoreboardPanel, gbc);

        gameAreaPanel = new GameAreaPanel(color, num){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/gamearea.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };
        gbc.gridx=1;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx=0.1;
        gbc.fill = GridBagConstraints.BOTH;

        add(gameAreaPanel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.05;
        drawingCardPanel = new DrawingCardPanel();
        add(drawingCardPanel, gbc);

        gbc.gridx=3;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx = 0.17;
        gbc.fill = GridBagConstraints.BOTH;
        chat = new Chat(username);
        add(chat, gbc);




        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);


    }

    private JMenuBar createMenuBar(){
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
                int action = JOptionPane.showConfirmDialog(GameAreaFrame.this, "Vuoi uscire dall'applicazione?", "Chiusura Applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(action == JOptionPane.OK_OPTION){
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

        //AboutFrame
        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame("About");
                JPanel aboutPanel = new JPanel();

                aboutPanel.setPreferredSize(new Dimension(300,200));
                Border insideBorder = BorderFactory.createTitledBorder("Informazioni");
                Border outsideBorder = BorderFactory.createEmptyBorder(10,10,10,10);
                Border finalBorder = BorderFactory.createCompoundBorder(insideBorder, outsideBorder);
                aboutPanel.setBorder(finalBorder);
                JTextArea creditsTextPane = new JTextArea();

                creditsTextPane.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        List<String> strings = new ArrayList<>();
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
                aboutFrame.setSize(300,200);
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

    public void setFullScreen() {
        // Ottiene l'oggetto GraphicsDevice per il dispositivo di visualizzazione corrente
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        // Imposta la finestra in modalità a schermo intero
        if (device.isFullScreenSupported()) {
            device.setFullScreenWindow(this);
        } else {
            setSize(800, 600); // Imposta una dimensione di fallback
            setVisible(true);
        }
    }
}