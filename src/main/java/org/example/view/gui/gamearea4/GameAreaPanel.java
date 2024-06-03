
package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.listener.GameAreaPanelListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAreaPanel extends JPanel {

    View view;
    TCPClient tcpClient;
    private int ChosenId;
    private int ChosenSide;
    private final static int MIDX = 320;
    private final static int MIDY = 347;
    private JLabel token1;
    private JLabel token2;
    private JLabel token3;
    private JLabel token4;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel secretObjective;
    private JLabel selectedCard = null;
    private PlayCardArea playCardArea;
    private static Coordinates mouseDownCompCoords;
    private BufferedImage backgroundImg;
    private Map<JLabel, Boolean> cardStates = new HashMap<>();
    private Map<JLabel, Integer> cardIds = new HashMap<>();
    private Icon transparentIcon;

    public GameAreaPanel(TCPClient tcpClient, View view, String color, String num, String starterCard, String objCard, String username) throws IOException {
        this.tcpClient = tcpClient;
        this.view = view;
        setLayout(new GridBagLayout());
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
        List<String> p = new ArrayList<>();
        for (String player : view.getPlayers()) {
            if (!player.equals(username)) {
                p.add(player);
            }
        }
        switch (num) {
            case "1":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken("Yellow", "Blue", "Yellow", "Red", "Green", false);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", false);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "2":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken(view.getColorPlayer().get(p.get(0)), "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken("Red", "Blue", "Yellow", "Red", "Green", false);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "3":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken(view.getColorPlayer().get(p.get(0)), "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken(view.getColorPlayer().get(p.get(1)), "Blue", "Yellow", "Red", "Green", true);
                token4 = createToken("Green", "Blue", "Yellow", "Red", "Green", false);
                break;

            case "4":
                token1 = createToken(color, "Blue", "Yellow", "Red", "Green", true);
                token2 = createToken(view.getColorPlayer().get(p.get(0)), "Blue", "Yellow", "Red", "Green", true);
                token3 = createToken(view.getColorPlayer().get(p.get(1)), "Blue", "Yellow", "Red", "Green", true);
                token4 = createToken(view.getColorPlayer().get(p.get(2)), "Blue", "Yellow", "Red", "Green", true);
                break;
        }

        playCardArea = new PlayCardArea(tcpClient, this) {


            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/pannotavolo.jpg")));
            //ImageIcon icon = new ImageIcon(ImageIO.read(new File("C:\\Users\\jamie\\OneDrive\\Desktop\\pannotavolo.jpg")));
            Image img = icon.getImage();

            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        JScrollPane jScrollPane = new JScrollPane(playCardArea);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        /*InfinitePanel infinitePanel = new InfinitePanel();
        infinitePanel.setView(playCardArea);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewport(infinitePanel);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
*/

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

        playCardArea.insertCardStarter(MIDX, MIDY, starterCard, 0, 0);

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

        view.addListener(new GameAreaPanelListener(this));
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

    private Icon loadCardIcon(int cardId, boolean isFront) throws IOException {
        BufferedImage newImg = null;
        if (!isFront) {
            if (cardId <= 10) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/001.png"));
            } else if (cardId <= 20) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/011.png"));
            } else if (cardId <= 30) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/021.png"));
            } else if (cardId <= 40) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/031.png"));
            } else if (cardId <= 50) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/041.png"));
            } else if (cardId <= 60) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/051.png"));
            } else if (cardId <= 70) {
                newImg = ImageIO.read(new File("src/main/resources/images/small/back/061.png"));
            } else if (cardId <= 80) {
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
                    System.out.println(view.getValidPlay() + " valid in gamePanel before tcp.sendPlay");
                    System.out.println(view.isTurn() + " turn in gamePanel before tcp.sendPlay");
                    //tcpClient.sendPlay(ChosenId, ChosenSide, 1, 1);
                } else if (e.getClickCount() == 2) {
                    changeCardImage(card);
                }
            }
        };
        card.addMouseListener(ma);
        card.putClientProperty("mouseAdapter", ma);
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
        System.out.println(getCardImagePath(ChosenId));
        playCardArea.setPathImageInsert(getCardImagePath(ChosenId));
    }

    private String getCardImagePath(int cardId) {
        if (cardId < 10) {
            return "src/main/resources/images/small/front/00" + cardId + ".png";
        } else {
            return "src/main/resources/images/small/front/0" + cardId + ".png";
        }
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

    public void updateHandDisplay() {
        try {
            System.out.println("Updating hand display");
            java.util.List<Integer> hand = view.getHand();
            System.out.println("Hand in GameAreaPanel: " + hand);
            System.out.println("Card IDs before update: " + cardIds.values());

            // Rimuovi tutte le carte attuali dal pannello
            for (JLabel card : new JLabel[]{card1, card2, card3}) {
                remove(card);
            }

            // Crea e aggiungi le nuove carte con i nuovi ID
            if (hand.size() >= 3) {
                card1 = createCard(hand.get(0));
                card2 = createCard(hand.get(1));
                card3 = createCard(hand.get(2));

                // Aggiorna cardIds con i nuovi ID
                cardIds.clear();
                cardIds.put(card1, hand.get(0));
                cardIds.put(card2, hand.get(1));
                cardIds.put(card3, hand.get(2));

                addCardListeners(card1);
                addCardListeners(card2);
                addCardListeners(card3);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridy = 2;
                gbc.weightx = 0.0001;

                gbc.gridx = 1;
                add(card1, gbc);

                gbc.gridx = 2;
                add(card2, gbc);

                gbc.gridx = 3;
                add(card3, gbc);
            }

            System.out.println("Card IDs after update: " + cardIds.values());

            revalidate();
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating hand cards", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handlePlayUpdate(int playedCardId, int side, int x, int y) {
        System.out.println("Updating play with playedCardId: " + playedCardId);
        JLabel playedCardLabel = null;
        for (Map.Entry<JLabel, Integer> entry : cardIds.entrySet()) {
            if (entry.getValue() == playedCardId) {
                playedCardLabel = entry.getKey();
                break;
            }
        }
        if (playedCardLabel != null) {
            GridBagConstraints gbc = ((GridBagLayout) getLayout()).getConstraints(playedCardLabel);
            cardIds.remove(playedCardLabel);
            remove(playedCardLabel);
            JLabel transparentCardLabel = new JLabel(transparentIcon);
            transparentCardLabel.setPreferredSize(new Dimension(transparentIcon.getIconWidth(), transparentIcon.getIconHeight()));
            cardIds.put(transparentCardLabel, -1);
            add(transparentCardLabel, gbc);
            transparentCardLabel.setEnabled(false);
            playCardArea.playCard(playedCardId, side, x, y);
        }
        revalidate();
        repaint();
    }

    public int getChosenSide() {
        return ChosenSide;
    }

    public int getChosenId() {
        return ChosenId;
    }
}