package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.listener.InvalidPlacementListener;
import org.example.view.gui.utilities.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameAreaPanel extends JPanel implements InvalidPlacementListener {
    private JLabel token1;
    private JLabel token2;
    private JLabel token3;
    private JLabel token4;
    private PlayCardArea playCardArea;
    private static Coordinates mouseDownCompCoords;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel secretObjective;
    private BufferedImage backgroundImg;
    private int ChosenId;
    private int ChosenSide;
    View view;
    TCPClient tcpClient;
    private Map<JLabel, Boolean> cardStates = new HashMap<>();
    private Map<JLabel, Integer> cardIds = new HashMap<>();
    private JLabel selectedCard = null;
    private Icon transparentIcon;
    private final static int MIDX = 320;
    private final static int MIDY = 347;

    public GameAreaPanel(TCPClient tcpClient, View view, String color, String num, String starterCard, String objCard) throws IOException {
        this.tcpClient = tcpClient;
        this.view = view;
        setLayout(new GridBagLayout());

        // Creazione dell'icona trasparente
        BufferedImage transparentImage = new BufferedImage(100, 150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transparentImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, 100, 150);
        g2d.dispose();
        transparentIcon = new ImageIcon(transparentImage);

        System.out.println(view.getHand());
        backgroundImg = ImageIO.read(new File("src/main/resources/images/gamearea.png"));

        card1 = createCard(view.getHand().get(0));
        card2 = createCard(view.getHand().get(1));
        card3 = createCard(view.getHand().get(2));

        BufferedImage img4 = ImageIO.read(new File(objCard));
        Icon ic4 = new ImageIcon(img4);
        secretObjective = new JLabel(ic4);
        cardStates.put(secretObjective, true);
        cardIds.put(secretObjective, -1);

        switch (num) {
            case "1":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken("Yellow", "Blue", "Yellow", "Red", "Green", false);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", false);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "2":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken("Blue", "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", false);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "3":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken("Yellow", "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", true);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "4":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken("Yellow", "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", true);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", true);
                break;
        }

        playCardArea = new PlayCardArea() {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/pannotavolo.jpg")));
            Image img = icon.getImage();

            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        MouseAdapter ma = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, playCardArea);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        playCardArea.scrollRectToVisible(view);
                    }
                }
            }
        };

        playCardArea.addMouseListener(ma);
        playCardArea.addMouseMotionListener(ma);

        JScrollPane jScrollPane = new JScrollPane(playCardArea);

        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        playCardArea.insertCardStarter(MIDX, MIDY, starterCard, 0,0);

        addCardListeners(card1);
        addCardListeners(card2);
        addCardListeners(card3);
        JLabel[] cards = {card1, card2, card3};
        for (JLabel card : cards) {
            card.setBorder(new LineBorder(Color.WHITE, 1));
        }
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.0001;
        gbc.weighty = 0.000025;
        add(card1, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0001;
        add(card2, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.0001;
        add(card3, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.0001;
        add(secretObjective, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.weighty = 0.000025;
        add(token1, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.00025;
        add(token2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.000025;
        gbc.weighty = 0.000025;
        add(token3, gbc);

        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.weightx = 0.000025;
        gbc.weighty = 0.000025;
        add(token4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 0.99;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 4;
        add(jScrollPane, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImg != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g.drawImage(backgroundImg, 0, 0, panelWidth, panelHeight, this);
        }
    }

    @Override
    public void onInvalidPlacement(int cardId) {
        for (Map.Entry<JLabel, Integer> entry : cardIds.entrySet()) {
            if (entry.getValue() == cardId) {
                try {
                    entry.getKey().setIcon(loadCardIcon(cardId, true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                entry.getKey().setVisible(true);
                break;
            }
        }
    }

    private Icon loadCardIcon(int cardId, boolean isFront) throws IOException {
        BufferedImage newImg = null;
        if (!isFront) {
            if (cardId < 10) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/001.png"));
            } else if (cardId < 20) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/011.png"));
            } else if (cardId < 30) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/021.png"));
            } else if (cardId < 40) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/031.png"));
            } else if (cardId < 50) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/041.png"));
            } else if (cardId < 60) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/051.png"));
            } else if (cardId < 70) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/061.png"));
            } else if (cardId < 80) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/071.png"));
            }
        } else {
            if (cardId < 10) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/front/00" + cardId + ".png"));
            } else {
                newImg = ImageIO.read(new File("src/main/resources/images/small/front/0" + cardId + ".png"));
            }
        }
        return new ImageIcon(newImg);
    }

    private void addCardListeners(JLabel card) {
        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    selectCard(card);
                    try {
                        tcpClient.sendPlay(ChosenId, ChosenSide, 1, 1);
                        view.removeHand(ChosenId);
                        card.setIcon(transparentIcon);
                        card.setBorder(null);
                        removeMouseListeners(card); // Rimuove i listener del mouse
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        restoreCard(card); // Ripristina la carta in caso di errore
                    }
                } else if ( e.getClickCount() == 2) {
                    changeCardImage(card);
                }
            }
        };
        card.addMouseListener(ma);
        card.putClientProperty("mouseAdapter", ma); // Salva il listener nel client property della carta
    }

    private void removeMouseListeners(JLabel card) {
        MouseAdapter ma = (MouseAdapter) card.getClientProperty("mouseAdapter");
        if (ma != null) {
            card.removeMouseListener(ma);
        }
    }

    private void restoreCard(JLabel card) {
        try {
            int cardId = cardIds.get(card);
            card.setIcon(loadCardIcon(cardId, true));
            card.setBorder(new LineBorder(Color.WHITE, 1));
            cardStates.put(card, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectCard(JLabel card) {
        if (selectedCard != null) {
            selectedCard.setBorder(new LineBorder(Color.WHITE, 1));
        }
        selectedCard = card;
        selectedCard.setBorder(new LineBorder(Color.BLUE, 2));
        ChosenId = cardIds.get(card);
        ChosenSide = cardStates.get(card) ? 0 : 1;
    }

    private void changeCardImage(JLabel card) {
        try {
            int cardId = cardIds.get(card);
            boolean isFront = cardStates.get(card);
            card.setIcon(loadCardIcon(cardId, !isFront));
            cardStates.put(card, !isFront);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JLabel createCard(int cardId) throws IOException {
        BufferedImage img;
        if (cardId < 10) {
            img = ImageIO.read(new File("src/main/resources/images/small/front/00" + cardId + ".png"));
        } else {
            img = ImageIO.read(new File("src/main/resources/images/small/front/0" + cardId + ".png"));
        }
        Icon icon = new ImageIcon(img);
        JLabel cardLabel = new JLabel(icon);
        cardStates.put(cardLabel, true);
        cardIds.put(cardLabel, cardId);
        cardLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        return cardLabel;
    }

    private JLabel createToken(String color, String blueColor, String yellowColor, String redColor, String greenColor, boolean visible) throws IOException {
        BufferedImage logo;
        if (color.equals("Blue")) {
            logo = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
        } else if (color.equals("Yellow")) {
            logo = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
        } else if (color.equals("Red")) {
            logo = ImageIO.read(new File("src/main/resources/images/red.png"));
        } else {
            logo = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
        }
        Icon icon = new ImageIcon(logo);
        JLabel tokenLabel = new JLabel(icon);
        tokenLabel.setVisible(visible);
        return tokenLabel;
    }
}