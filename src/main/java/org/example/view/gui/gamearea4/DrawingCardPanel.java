package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.listener.DrawingCardPanelListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingCardPanel extends JPanel {
    public View view;
    public int drawChoose;
    public TCPClient tcpClient;
    private List<String> paths;
    private Border selectedBorder = new LineBorder(Color.BLUE, 2);
    private Border defaultBorder = new LineBorder(Color.WHITE, 2);
    private JLabel[] cardLabels = new JLabel[6];

    public DrawingCardPanel(TCPClient tcpClient, View view) throws IOException {
        this.view = view;
        this.paths = new ArrayList<>();
        this.tcpClient = tcpClient;
        setLayout(new GridBagLayout());
        view.addListener(new DrawingCardPanelListener(this));
        updateCards();
    }

    private JLabel createCardLabel(String path, int cardIndex) throws IOException {
        System.out.println("create" + path);
        BufferedImage image = ImageIO.read(new File(path));
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBorder(defaultBorder);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    drawChoose = cardIndex;
                    updateCardSelection(cardIndex);
                } else if (e.getClickCount() == 2) {
                    drawChoose = cardIndex;
                    updateCardSelection(cardIndex);
                    tcpClient.sendDraw(cardIndex);
                    updateCards();
                }
            }
        });
        return label;
    }

    private void updateCardSelection(int selectedCardIndex) {
        for (int i = 0; i < cardLabels.length; i++) {
            if (i == selectedCardIndex) {
                cardLabels[i].setBorder(selectedBorder);
            } else {
                cardLabels[i].setBorder(defaultBorder);
            }
        }
    }

    public void updateCards() {
        paths.clear();
        int i =0;
        try {
            List<Integer> drawableCards = view.getDrawableCards();
            for (int drawableCard : drawableCards) {
                if(i<2){
                    String path;
                    if (drawableCard <= 10) {
                        path = "src/main/resources/images/big/back/001.png";
                    } else if (drawableCard <= 20) {
                        path = "src/main/resources/images/big/back/011.png";
                    } else if (drawableCard <= 30) {
                        path = "src/main/resources/images/big/back/021.png";
                    } else if (drawableCard <= 40) {
                        path = "src/main/resources/images/big/back/031.png";
                    } else if (drawableCard <= 50) {
                        path = "src/main/resources/images/big/back/041.png";
                    } else if (drawableCard <= 60) {
                        path = "src/main/resources/images/big/back/051.png";
                    } else if (drawableCard <= 70) {
                        path = "src/main/resources/images/big/back/061.png";
                    } else if (drawableCard <= 80) {
                        path = "src/main/resources/images/big/back/071.png";
                    } else {
                        path = "src/main/resources/images/big/back/081.png";
                    }
                    i++;
                    paths.add(path);

                } else {
                    if(drawableCard<10){
                        String path= "src/main/resources/images/big/front/00"+drawableCard+".png";
                        paths.add(path);
                        i++;
                    } else {
                        String path= "src/main/resources/images/big/front/0"+drawableCard+".png";
                        paths.add(path);
                        i++;
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("update " + paths);
        refreshCardLabels();
    }

    private void refreshCardLabels() {
        removeAll();
        GridBagConstraints gbFactory = new GridBagConstraints();
        gbFactory.fill = GridBagConstraints.BOTH;
        gbFactory.insets = new Insets(5, 0, 5, 0);
        gbFactory.gridx = 0;

        for (int i = 0; i < paths.size(); i++) {
            try {
                JLabel cardLabel = createCardLabel(paths.get(i), i);
                gbFactory.gridy = i;
                add(cardLabel, gbFactory);
                cardLabels[i] = cardLabel;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        revalidate();
        repaint();
    }

    public List<String> getPaths() {
        return paths;
    }
}
