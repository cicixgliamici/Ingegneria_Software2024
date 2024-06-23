package org.example.view.gui.selectobjstarter3;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.gamearea4.GameAreaFrame;
import org.example.view.gui.listener.EvListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The SelectObjStarter class represents a JFrame where the user selects a starter card and an objective card.
 */
public class SelectObjStarter extends JFrame {

    private View view;
    private int side = 0; // Side selection for the starter card
    private int choice = 0; // Choice selection for the objective card
    private TCPClient tcpClient;
    private String username;
    private String color;
    private String num;
    private EvListener evListener;
    private String choiceSideStarter;
    private String choiceObjCard;
    private JLabel backSideStarter;
    private JLabel frontSideStarter;
    private JLabel firstObjectCard;
    private JLabel secondObjectCard;

    private Border blueBorder = new LineBorder(Color.BLUE, 3);
    private Border emptyBorder = new LineBorder(Color.WHITE, 3); // white or null

    /**
     * Constructs a SelectObjStarter frame.
     *
     * @param tcpClient The TCP client for communication.
     * @param username  The username of the player.
     * @param view      The view instance.
     * @param color     The color chosen by the player.
     * @param num       The player number.
     * @throws IOException If an I/O error occurs during image loading.
     */
    public SelectObjStarter(TCPClient tcpClient, String username, View view, String color, String num) throws IOException {
        super("Select StarterCard and ObjectedCard " + "[" + username + "]");
        this.tcpClient = tcpClient;
        this.username = username;
        this.view = view;
        this.color = color;
        this.num = num;

        setLayout(new BorderLayout());

        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("images/icon/iconamini.png")) {
            if (iconStream != null) {
                Image icon = ImageIO.read(iconStream);
                setIconImage(icon);
            } else {
                throw new IOException("Icon image file not found!");
            }
        }

        // Panel with background image
        JPanel container = new JPanel(new GridBagLayout()) {
            ImageIcon icon;
            Image img;

            {
                try (InputStream bgStream = getClass().getClassLoader().getResourceAsStream("images/backgroundSelecObjStarter.jpg")) {
                    if (bgStream != null) {
                        icon = new ImageIcon(ImageIO.read(bgStream));
                        img = icon.getImage();
                    } else {
                        throw new IOException("Background image file not found!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        // Load and set images for starter and objective cards
        BufferedImage starterFront = loadImage(view.getCardsPath().get(0));
        Icon icon1 = new ImageIcon(starterFront);

        BufferedImage starterBack = loadImage(view.getCardsPath().get(1));
        Icon icon2 = new ImageIcon(starterBack);

        BufferedImage obj1 = loadImage(view.getCardsPath().get(2));
        Icon icon3 = new ImageIcon(obj1);

        BufferedImage obj2 = loadImage(view.getCardsPath().get(3));
        Icon icon4 = new ImageIcon(obj2);

        // Initialize JLabels with images
        backSideStarter = new JLabel(icon1);
        frontSideStarter = new JLabel(icon2);
        firstObjectCard = new JLabel(icon3);
        secondObjectCard = new JLabel(icon4);

        JButton button = new JButton("Confirm!");

        // Create and add action listeners to buttons
        ChooseCardButton chooseOne = createChooseCardButton(0, 1, view.getCardsPath().get(0));
        ChooseCardButton chooseTwo = createChooseCardButton(1, 2, view.getCardsPath().get(1));
        ChooseCardButton chooseThree = createChooseCardButton(2, 1, view.getCardsPath().get(2));
        ChooseCardButton chooseFour = createChooseCardButton(3, 2, view.getCardsPath().get(3));

        // Confirm button action listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (side != 0 && choice != 0) {
                    try {
                        tcpClient.sendSetObjStrater(side, choice);
                        dispose();
                        // Open GameAreaFrame
                        new GameAreaFrame(tcpClient, view, username, color, num, choiceSideStarter, choiceObjCard);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(button, "Please select the side of the starter card and the objective card", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Layout configuration
        layoutComponents(container, chooseOne, chooseTwo, chooseThree, chooseFour, button);

        add(container, BorderLayout.CENTER);
        pack();
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        firstUpdate();
    }

    /**
     * Loads an image from the specified path.
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
     * Creates a ChooseCardButton and adds an action listener to it.
     *
     * @param index        The index of the card in the view's card paths.
     * @param sideOrChoice The side or choice value to set.
     * @param cardPath     The path to the card image.
     * @return The created ChooseCardButton.
     */
    private ChooseCardButton createChooseCardButton(int index, int sideOrChoice, String cardPath) {
        ChooseCardButton button = new ChooseCardButton();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < 2) {
                    side = sideOrChoice;
                    updateStarterBorders();
                    choiceSideStarter = cardPath;
                } else {
                    choice = sideOrChoice;
                    updateChoiceBorders();
                    choiceObjCard = cardPath;
                }
            }
        });
        return button;
    }

    /**
     * Updates the borders of the starter card labels.
     */
    private void updateStarterBorders() {
        if (side == 1) {
            backSideStarter.setBorder(blueBorder);
            frontSideStarter.setBorder(emptyBorder);
        } else if (side == 2) {
            backSideStarter.setBorder(emptyBorder);
            frontSideStarter.setBorder(blueBorder);
        }
    }

    /**
     * Updates the borders of the choice card labels.
     */
    private void updateChoiceBorders() {
        if (choice == 1) {
            firstObjectCard.setBorder(blueBorder);
            secondObjectCard.setBorder(emptyBorder);
        } else if (choice == 2) {
            firstObjectCard.setBorder(emptyBorder);
            secondObjectCard.setBorder(blueBorder);
        }
    }

    /**
     * Sets the initial borders of all labels to empty.
     */
    private void firstUpdate() {
        frontSideStarter.setBorder(emptyBorder);
        backSideStarter.setBorder(emptyBorder);
        firstObjectCard.setBorder(emptyBorder);
        secondObjectCard.setBorder(emptyBorder);
    }

    /**
     * Layouts the components in the specified container.
     *
     * @param container   The container to add components to.
     * @param chooseOne   The button for choosing the first card.
     * @param chooseTwo   The button for choosing the second card.
     * @param chooseThree The button for choosing the third card.
     * @param chooseFour  The button for choosing the fourth card.
     * @param button      The confirm button.
     */
    private void layoutComponents(JPanel container, ChooseCardButton chooseOne, ChooseCardButton chooseTwo, ChooseCardButton chooseThree, ChooseCardButton chooseFour, JButton button) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.5;
        gbc.weightx = 0.5;

        gbc.gridy = 0;
        gbc.gridx = 0;
        container.add(backSideStarter, gbc);
        container.add(chooseOne, gbc);

        gbc.gridy = 1;
        container.add(frontSideStarter, gbc);
        container.add(chooseTwo, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        container.add(firstObjectCard, gbc);
        container.add(chooseThree, gbc);

        gbc.gridy = 1;
        container.add(secondObjectCard, gbc);
        container.add(chooseFour, gbc);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 2;
        gbcButton.gridwidth = 2;
        gbcButton.weighty = 0.3;

        container.add(button, gbcButton);
    }
}
