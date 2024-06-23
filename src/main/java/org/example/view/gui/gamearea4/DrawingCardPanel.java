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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A panel for displaying drawable cards in a grid layout, allowing users to select and draw cards.
 */
public class DrawingCardPanel extends JPanel {
    public View view;  // Reference to the view, which contains game state information
    public int drawChoose;  // Index of the chosen card
    public TCPClient tcpClient;  // TCP client for sending draw actions to the server
    private List<String> paths;  // Paths to the card images
    private Border selectedBorder = new LineBorder(Color.BLUE, 2);  // Border for selected card
    private Border defaultBorder = new LineBorder(Color.WHITE, 2);  // Default border for cards
    private JLabel[] cardLabels = new JLabel[6];  // Labels for displaying cards

    /**
     * Constructor to initialize the drawing card panel.
     *
     * @param tcpClient The TCP client for sending messages to the server.
     * @param view The view containing game state and information.
     * @throws IOException If an error occurs during image loading.
     */
    public DrawingCardPanel(TCPClient tcpClient, View view) throws IOException {
        this.view = view;
        this.paths = new ArrayList<>();
        this.tcpClient = tcpClient;
        setLayout(new GridBagLayout());
        view.addListener(new DrawingCardPanelListener(this));
        updateCards();  // Initialize the panel with drawable cards
    }

    /**
     * Creates a JLabel for a card with the given image path and index.
     *
     * @param path The path to the card image.
     * @param cardIndex The index of the card.
     * @return The JLabel representing the card.
     * @throws IOException If an error occurs during image loading.
     */
    private JLabel createCardLabel(String path, int cardIndex) throws IOException {
        BufferedImage image = loadImage(path);
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
                    updateCards();  // Update the cards after drawing
                }
            }
        });
        return label;
    }

    /**
     * Updates the border of the selected card and resets others to the default border.
     *
     * @param selectedCardIndex The index of the selected card.
     */
    private void updateCardSelection(int selectedCardIndex) {
        for (int i = 0; i < cardLabels.length; i++) {
            if (i == selectedCardIndex) {
                cardLabels[i].setBorder(selectedBorder);
            } else {
                cardLabels[i].setBorder(defaultBorder);
            }
        }
    }

    /**
     * Updates the panel with the current drawable cards from the view.
     */
    public void updateCards() {
        paths.clear();
        int i = 0;
        try {
            List<Integer> drawableCards = view.getDrawableCards();
            for (int drawableCard : drawableCards) {
                String path;
                if (i < 2) {
                    // Set path for the back of the card based on the card index
                    path = getBackCardImagePath(drawableCard);
                } else {
                    // Set path for the front of the card based on the card index
                    path = getFrontCardImagePath(drawableCard);
                }
                paths.add(path);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        refreshCardLabels();
    }

    /**
     * Gets the path for the back of the card based on the card index.
     *
     * @param drawableCard The index of the drawable card.
     * @return The path to the back card image.
     */
    private String getBackCardImagePath(int drawableCard) {
        if (drawableCard <= 10) {
            return "images/big/back/001.png";
        } else if (drawableCard <= 20) {
            return "images/big/back/011.png";
        } else if (drawableCard <= 30) {
            return "images/big/back/021.png";
        } else if (drawableCard <= 40) {
            return "images/big/back/031.png";
        } else if (drawableCard <= 50) {
            return "images/big/back/041.png";
        } else if (drawableCard <= 60) {
            return "images/big/back/051.png";
        } else if (drawableCard <= 70) {
            return "images/big/back/061.png";
        } else if (drawableCard <= 80) {
            return "images/big/back/071.png";
        } else {
            return "images/big/back/081.png";
        }
    }

    /**
     * Gets the path for the front of the card based on the card index.
     *
     * @param drawableCard The index of the drawable card.
     * @return The path to the front card image.
     */
    private String getFrontCardImagePath(int drawableCard) {
        if (drawableCard < 10) {
            return "images/big/front/00" + drawableCard + ".png";
        } else {
            return "images/big/front/0" + drawableCard + ".png";
        }
    }

    /**
     * Refreshes the labels in the panel to display the current set of card images.
     */
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

    /**
     * Loads an image from the specified path using the class loader.
     *
     * @param path The path to the image.
     * @return The loaded BufferedImage.
     * @throws IOException If an error occurs during image loading.
     */
    private BufferedImage loadImage(String path) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream != null) {
                return ImageIO.read(inputStream);
            } else {
                throw new IOException("Image file not found: " + path);
            }
        }
    }

    /**
     * Gets the list of paths to the card images.
     *
     * @return The list of image paths.
     */
    public List<String> getPaths() {
        return paths;
    }
}
