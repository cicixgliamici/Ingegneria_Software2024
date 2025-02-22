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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameAreaPanel extends JPanel {

    View view;
    TCPClient tcpClient;
    private int ChosenId;
    private int chosenSide;
    private final static int MIDX = 720;
    private final static int MIDY = 747;
    private JLabel token1;
    private JLabel token2;
    private JLabel token3;
    private JLabel token4;
    private JLabel blackToken;
    private JLabel username1;
    private JLabel username2;
    private JLabel username3;
    private JLabel username4;
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
    private String color;

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
        System.out.println("Hand in GameAreaPanel: " + view.getHand());
        backgroundImg = loadImage("images/gamearea.png");
        card1 = createCard(view.getHand().get(0));
        card2 = createCard(view.getHand().get(1));
        card3 = createCard(view.getHand().get(2));
        BufferedImage img4 = loadImage(objCard);
        Icon ic4 = new ImageIcon(img4);
        secretObjective = new JLabel(ic4);
        cardStates.put(secretObjective, true);
        cardIds.put(secretObjective, -1);
        BufferedImage logo = loadImage("images/CODEX_pion_noir.png");
        Icon icon = new ImageIcon(logo);
        JLabel tokenLabel = new JLabel(icon);
        tokenLabel.setVisible(true);
        blackToken = tokenLabel;
        List<String> keys = new ArrayList<>(view.getColorPlayer().keySet());
        keys.removeIf(player -> player.equals(username));
        String firstPlayer = view.getFirstPlayer();

        switch (num) {
            case "2":
                token1 = createToken(color, true);
                token2 = createToken(view.getColorPlayer().get(keys.get(0)), true);
                token3 = createToken("Red", false);
                token4 = createToken("Green", false);
                username1 = new JLabel(username);
                username1.setFont(new Font("Helvetica", Font.BOLD, 15));
                username2 = new JLabel(keys.get(0));
                username2.setFont(new Font("Helvetica", Font.BOLD, 15));
                break;
            case "3":
                token1 = createToken(color, true);
                token2 = createToken(view.getColorPlayer().get(keys.get(0)), true);
                token3 = createToken(view.getColorPlayer().get(keys.get(1)), true);
                token4 = createToken("Green", false);
                username1 = new JLabel(username);
                username1.setFont(new Font("Helvetica", Font.BOLD, 15));
                username2 = new JLabel(keys.get(0));
                username2.setFont(new Font("Helvetica", Font.BOLD, 15));
                username3 = new JLabel(keys.get(1));
                username3.setFont(new Font("Helvetica", Font.BOLD, 15));
                break;
            case "4":
                token1 = createToken(color, true);
                token2 = createToken(view.getColorPlayer().get(keys.get(0)), true);
                token3 = createToken(view.getColorPlayer().get(keys.get(1)), true);
                token4 = createToken(view.getColorPlayer().get(keys.get(2)), true);
                username1 = new JLabel(username);
                username1.setFont(new Font("Helvetica", Font.BOLD, 15));
                username2 = new JLabel(keys.get(0));
                username2.setFont(new Font("Helvetica", Font.BOLD, 15));
                username3 = new JLabel(keys.get(1));
                username3.setFont(new Font("Helvetica", Font.BOLD, 15));
                username4 = new JLabel(keys.get(2));
                username4.setFont(new Font("Helvetica", Font.BOLD, 15));
                break;
        }

        playCardArea = new PlayCardArea(tcpClient, this) {


            ImageIcon icon = new ImageIcon(loadImage("images/pannotavolo.jpg"));
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
        jScrollPane.getViewport().setViewPosition(new Point(500,500));

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
        add(username1, gbc);

        gbc.gridx = 5;
        gbc.gridy = 0;
        add(username2, gbc);

        if(username3!=null){
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(username3, gbc);
        }

        if(username4 != null){
            gbc.gridx = 5;
            gbc.gridy = 2;
            add(username4, gbc);
        }

        if(firstPlayer.equals(username)){
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.PAGE_END;
            add(blackToken, gbc);
        } else if (firstPlayer.equals(keys.get(0))) {
            gbc.gridx = 5;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.PAGE_START;
            add(blackToken, gbc);
        } else if (keys.get(1) != null){
            if(firstPlayer.equals(keys.get(1))){
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.PAGE_START;
                add(blackToken, gbc);
            }
        } else if (keys.get(2) != null) {
            if(firstPlayer.equals(keys.get(2))){
                gbc.gridx = 5;
                gbc.gridy = 1;
                gbc.anchor = GridBagConstraints.PAGE_END;
                add(blackToken, gbc);
            }
        }


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

    private BufferedImage loadImage(String path) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + path);
        }
        return ImageIO.read(inputStream);
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
                newImg = loadImage("images/small/back/001.png");
            } else if (cardId <= 20) {
                newImg = loadImage("images/small/back/011.png");
            } else if (cardId <= 30) {
                newImg = loadImage("images/small/back/021.png");
            } else if (cardId <= 40) {
                newImg = loadImage("images/small/back/031.png");
            } else if (cardId <= 50) {
                newImg = loadImage("images/small/back/041.png");
            } else if (cardId <= 60) {
                newImg = loadImage("images/small/back/051.png");
            } else if (cardId <= 70) {
                newImg = loadImage("images/small/back/061.png");
            } else if (cardId <= 80) {
                newImg = loadImage("images/small/back/071.png");
            }
        } else {
            if (cardId < 10) {
                newImg = loadImage("images/small/front/00" + cardId + ".png");
            } else {
                newImg = loadImage("images/small/front/0" + cardId + ".png");
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
                    if(chosenSide==0){
                        chosenSide++;
                        playCardArea.setPathImageInsert(getCardImagePath(ChosenId));
                    }
                    else if(chosenSide==1){
                        chosenSide--;
                        playCardArea.setPathImageInsert(getCardImagePath(ChosenId));
                    }
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
        chosenSide = cardStates.get(card) ? 0 : 1;
        playCardArea.setPathImageInsert(getCardImagePath(ChosenId));
    }

    private String getCardImagePath(int cardId) {
        if(getChosenSide()==0) {
            if (cardId < 10) {
                return "images/mid/front/00" + cardId + ".png";
            } else {
                return "images/mid/front/0" + cardId + ".png";
            }
        }
        else if(getChosenSide()==1) {
            if (cardId <= 10) {
                return "images/mid/back/001.png";
            } else if (cardId <= 20) {
                return "images/mid/back/011.png";
            } else if (cardId <= 30) {
                return "images/mid/back/021.png";
            } else if (cardId <= 40) {
                return "images/mid/back/031.png";
            } else if (cardId <= 50) {
                return "images/mid/back/041.png";
            } else if (cardId <= 60) {
                return "images/mid/back/051.png";
            } else if (cardId <= 70) {
                return "images/mid/back/061.png";
            } else if (cardId <= 80) {
                return "images/mid/back/071.png";
            }
        }
        return null;
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
            img = loadImage("images/small/front/00" + cardId + ".png");
        } else {
            img = loadImage("images/small/front/0" + cardId + ".png");
        }
        Icon icon = new ImageIcon(img);
        JLabel cardLabel = new JLabel(icon);
        cardStates.put(cardLabel, true);
        cardIds.put(cardLabel, cardId);
        cardLabel.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        return cardLabel;
    }
    private JLabel createToken(String color, boolean visible) throws IOException {
        String imagePath = "images/CODEX_pion_" + color.toLowerCase() + ".png";
        BufferedImage logo = loadImage(imagePath);
        if (logo == null) {
            System.err.println("Failed to load token image for color: " + color);
            return null; // Return null or handle this situation appropriately.
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
        return chosenSide;
    }

    public int getChosenId() {
        return ChosenId;
    }
}