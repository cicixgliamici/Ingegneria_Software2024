package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class GameRulesFrame extends JFrame {

    GameRulesPanel gameRulesPanel;
    private JPanel imagePanel;
    private JScrollPane scrollPane;
    private ImageIcon[] images;
    private int currentIndex = 0;
    public GameRulesFrame() throws IOException {

        super("Game Rules");
        setSize(700, 700);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/01.png");
        setIconImage(icon);

        images = new ImageIcon[3];
        images[0] = new ImageIcon("src/main/resources/images/01.png"); // Imposta il percorso dell'immagine
        images[1] = new ImageIcon("src/main/resources/images/02.png");
        images[2] = new ImageIcon("src/main/resources/images/03.png");

        // JPanel per visualizzare le immagini
        imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                images[currentIndex].paintIcon(this, g, 0, 0);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(images[currentIndex].getIconWidth(), images[currentIndex].getIconHeight());
            }
        };

        // JScrollPane per lo scorrimento
        scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Aggiungi JScrollPane al JFrame
        getContentPane().add(scrollPane);

        // Aggiungi ascoltatore delle frecce della tastiera
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    currentIndex = (currentIndex - 1 + images.length) % images.length;
                    imagePanel.repaint();
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    currentIndex = (currentIndex + 1) % images.length;
                    imagePanel.repaint();
                }
            }
        });

        // Assicurati che il frame abbia il focus per catturare gli eventi della tastiera
        setFocusable(true);
        requestFocus();

        // Visualizza il frame
        setVisible(true);
    }


    //setLayout(new BorderLayout());

        //gameRulesPanel = new GameRulesPanel();

        //add(gameRulesPanel, BorderLayout.CENTER);


}


