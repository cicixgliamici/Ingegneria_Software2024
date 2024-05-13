package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JFrame{

    BoxMenu boxMenu;

    public MainMenu() throws IOException {
        super("Codex Naturalis");

        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/iconamini.png");
        setIconImage(icon);

        boxMenu = new BoxMenu(){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/background.png")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);
            }
        };

        setLayout(new BorderLayout()); //layout manager che si occuperà di posizionare i componenti

        setJMenuBar(createMenuBar());

        add(boxMenu, BorderLayout.CENTER);

        pack();
        setSize(810, 660);
        setLocationRelativeTo(null); // visualizzare la finestra al centro dello schermo
        setResizable(false); //non permette di ridimensionare la finestra
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menuOption = new JMenu("Option");
        menuOption.setMnemonic(KeyEvent.VK_O);

        JMenuItem menuItemRules = new JMenuItem("Rules");
        menuItemRules.setMnemonic(KeyEvent.VK_R);
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.setMnemonic(KeyEvent.VK_E);

        menuOption.add(menuItemRules);
        menuOption.addSeparator();
        menuOption.add(menuItemExit);

        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainMenu.this, "Vuoi uscire dall'applicazione?", "Chiusura Applicazione", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(action == JOptionPane.OK_OPTION){
                    System.exit(0);
                }
            }
        });

        JMenu menuAbout = new JMenu("About");

        JMenuItem menuItemAbout = new JMenuItem("?");

        menuAbout.add(menuItemAbout);

        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame aboutFrame = new JFrame("About");
                aboutFrame.setSize(300,200);
                aboutFrame.setLocationRelativeTo(null);
                aboutFrame.setVisible(true);
            }
        });

        menuBar.add(menuOption);
        menuBar.add(menuAbout);

        return menuBar;
    }
}
