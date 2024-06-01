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
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private String path5;
    private String path6;
    private List<String> paths;
    private Border selectedBorder = new LineBorder(Color.BLUE, 2);
    private Border defaultBorder = new LineBorder(Color.WHITE, 2);
    private JLabel[] cardLabels = new JLabel[6];

    public DrawingCardPanel(TCPClient tcpClient, View view, GameAreaPanel gameAreaPanel) throws IOException {
        this.view = view;
        this.paths = new ArrayList<>();
        this.tcpClient = tcpClient;
        setLayout(new GridBagLayout());
        view.addListener(new DrawingCardPanelListener(this, gameAreaPanel));
        updateCards();

        GridBagConstraints gbFactory = new GridBagConstraints();
        gbFactory.fill = GridBagConstraints.BOTH;
        gbFactory.insets = new Insets(5, 0, 5, 0);
        gbFactory.gridx = 0;

        for (int i = 0; i < paths.size(); i++) {
            JLabel cardLabel = createCardLabel(paths.get(i), i);
            gbFactory.gridy = i;
            add(cardLabel, gbFactory);
            cardLabels[i] = cardLabel;
        }
    }

    private JLabel createCardLabel(String path, int cardIndex) throws IOException {
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
                    view.drawnCard(drawChoose); // Notify View of the drawn card
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
        try {
            int drawableCard0 = view.getDrawableCards().get(0);
            if (drawableCard0 <= 10) {
                path1 = "src/main/resources/images/big/back/001.png";
            } else if (drawableCard0 <= 20) {
                path1 = "src/main/resources/images/big/back/011.png";
            } else if (drawableCard0 <= 30) {
                path1 = "src/main/resources/images/big/back/021.png";
            } else if (drawableCard0 <= 40) {
                path1 = "src/main/resources/images/big/back/031.png";
            }
            paths.add(path1);

            int drawableCard1 = view.getDrawableCards().get(1);
            if (drawableCard1 <= 50) {
                path2 = "src/main/resources/images/big/back/041.png";
            } else if (drawableCard1 <= 60) {
                path2 = "src/main/resources/images/big/back/051.png";
            } else if (drawableCard1 <= 70) {
                path2 = "src/main/resources/images/big/back/061.png";
            } else if (drawableCard1 <= 80) {
                path2 = "src/main/resources/images/big/back/071.png";
            }
            paths.add(path2);

            int drawableCard2 = view.getDrawableCards().get(2);
            if (drawableCard2 < 10) {
                path3 = "src/main/resources/images/big/front/00" + drawableCard2 + ".png";
            } else if (drawableCard2 > 99) {
                path3 = "src/main/resources/images/big/front/" + drawableCard2 + ".png";
            } else {
                path3 = "src/main/resources/images/big/front/0" + drawableCard2 + ".png";
            }
            paths.add(path3);

            int drawableCard3 = view.getDrawableCards().get(3);
            if (drawableCard3 < 10) {
                path4 = "src/main/resources/images/big/front/00" + drawableCard3 + ".png";
            } else if (drawableCard3 > 99) {
                path4 = "src/main/resources/images/big/front/" + drawableCard3 + ".png";
            } else {
                path4 = "src/main/resources/images/big/front/0" + drawableCard3 + ".png";
            }
            paths.add(path4);

            path5 = "src/main/resources/images/big/front/0" + view.getDrawableCards().get(4) + ".png";
            paths.add(path5);

            int drawableCard5 = view.getDrawableCards().get(5);
            if (drawableCard5 < 10) {
                path6 = "src/main/resources/images/big/front/00" + drawableCard5 + ".png";
            } else if (drawableCard5 > 99) {
                path6 = "src/main/resources/images/big/front/" + drawableCard5 + ".png";
            } else {
                path6 = "src/main/resources/images/big/front/0" + drawableCard5 + ".png";
            }
            paths.add(path6);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
