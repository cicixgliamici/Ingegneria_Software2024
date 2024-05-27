package org.example.view.gui.setgame2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WaitingScreen extends JFrame {

    public WaitingScreen() {
        setTitle("Waiting for Players");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Etichetta di attesa
        JLabel waitingLabel = new JLabel("Waiting for other players...", SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Serif", Font.BOLD, 18));
        waitingLabel.setForeground(Color.BLUE);
        waitingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Barra di caricamento
//        JProgressBar progressBar = new JProgressBar();
//        progressBar.setIndeterminate(true);
//        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
//        progressBar.setPreferredSize(new Dimension(300, 30));
//        progressBar.setBackground(Color.WHITE);

//        ProgressSpinner progress = new ProgressSpinner();
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("src/main/resources/images/loading1.gif"));
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(image, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);

        JLabel progress = new JLabel(icon);
        // Aggiunta degli elementi al pannello principale
        mainPanel.add(waitingLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio tra gli elementi
        mainPanel.add(progress);

        // Aggiunta del pannello principale al frame
        add(progress, BorderLayout.CENTER);
        setVisible(true);
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
