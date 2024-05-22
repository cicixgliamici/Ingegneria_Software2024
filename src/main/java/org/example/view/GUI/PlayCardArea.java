package org.example.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayCardArea extends JPanel {

    private int x;
    private int y;
    private Graphics graphics;

    public PlayCardArea() throws IOException {

        setLayout(new BorderLayout());
        BufferedImage logo = ImageIO.read(new File("src/main/resources/images/pannotavolo.jpg"));
        Icon icon = new ImageIcon(logo);
        JLabel labelprova = new JLabel(icon);
        JScrollPane scrollPane = new JScrollPane(labelprova);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(labelprova,BorderLayout.CENTER);

        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        });
    }

    public void paintComponent(Graphics graphics, int x, int y, Image img){
        graphics.drawImage(img,x,y, this);
        super.paintComponent(graphics);
    }
}
