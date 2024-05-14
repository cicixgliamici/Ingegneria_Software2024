package org.example.view.GUI.gamerules;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRulesPanel extends JPanel{

    public GameRulesPanel() throws IOException {
        setLayout(new BorderLayout());
        Image myPicture = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/02.png");
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel, BorderLayout.CENTER);

        picLabel.setDisplayedMnemonic(KeyEvent.VK_KP_RIGHT);

    }
}
