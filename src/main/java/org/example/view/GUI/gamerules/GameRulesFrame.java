package org.example.view.GUI.gamerules;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameRulesFrame extends JFrame {

    private JPanel imagePanel;
    private JScrollPane scrollPane;
    private ImageIcon[] images;
    private int currentIndex = 0;
    public GameRulesFrame() throws IOException {

        super("Game Rules");
        setSize(714, 740);
        setLayout(new BorderLayout());
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/01.png");
        setIconImage(icon);

        images = new ImageIcon[12];
        images[0] = new ImageIcon("src/main/resources/images/01.png");
        images[1] = new ImageIcon("src/main/resources/images/02.png");
        images[2] = new ImageIcon("src/main/resources/images/03.png");
        images[3] = new ImageIcon("src/main/resources/images/04.png");
        images[4] = new ImageIcon("src/main/resources/images/05.png");
        images[5] = new ImageIcon("src/main/resources/images/06.png");
        images[6] = new ImageIcon("src/main/resources/images/07.png");
        images[7] = new ImageIcon("src/main/resources/images/08.png");
        images[8] = new ImageIcon("src/main/resources/images/09.png");
        images[9] = new ImageIcon("src/main/resources/images/10.png");
        images[10] = new ImageIcon("src/main/resources/images/11.png");
        images[11] = new ImageIcon("src/main/resources/images/12.png");

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


        scrollPane = new JScrollPane(imagePanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    if(currentIndex != 0){
                        currentIndex = (currentIndex - 1 + images.length) % images.length;
                        imagePanel.repaint();
                    }
                }  else if (keyCode == KeyEvent.VK_RIGHT) {
                    if (currentIndex < images.length - 1) {
                        currentIndex++;
                        imagePanel.repaint();
                    }
                }

            }
        });


        setFocusable(true);
        requestFocus();
        setResizable(false);
        setVisible(true);
        String information = "Please use arrows key to navigate"; //TODO jima controlla la forma di questa frase
        JOptionPane.showMessageDialog(null, information, "Information", JOptionPane.INFORMATION_MESSAGE);
    }


}


