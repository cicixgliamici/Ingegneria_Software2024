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
import java.io.File;
import java.io.IOException;

public class SelectObjStarter extends JFrame {

    private View view;
    private int side = 0;
    private int choice = 0;
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

    public SelectObjStarter(TCPClient tcpClient, String username, View view, String color, String num) throws IOException {
        super("Select StarterCard and ObjectedCard " + "[" + username + "]");
        this.tcpClient = tcpClient;
        this.username = username;
        this.view = view;
        this.color = color;
        this.num = num;

        setLayout(new BorderLayout());

        JPanel container = new JPanel(new GridBagLayout()){
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/backgroundSelecObjStarter.jpg")));
            Image img = icon.getImage();

            {
                setOpaque(false);
            }

            public void paintComponent(Graphics graphics) {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };

        BufferedImage starterFront = null;
        System.out.println(String.valueOf(view.getCardsId().get(0)).toString());
        try {
            starterFront = ImageIO.read(new File(view.getCardsPath().get(0)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon1 = new ImageIcon(starterFront);

        BufferedImage starterBack = null;
        try {
            starterBack = ImageIO.read(new File(view.getCardsPath().get(1)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon2 = new ImageIcon(starterBack);

        BufferedImage obj1 = null;
        try {
            obj1 = ImageIO.read(new File(view.getCardsPath().get(2)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon3 = new ImageIcon(obj1);

        BufferedImage obj2 = null;
        try {
            obj2 = ImageIO.read(new File(view.getCardsPath().get(3)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon4 = new ImageIcon(obj2);

        backSideStarter = new JLabel(icon1);
        frontSideStarter = new JLabel(icon2);
        firstObjectCard = new JLabel(icon3);
        secondObjectCard = new JLabel(icon4);

        JButton button = new JButton("Confirm!");

        ChooseCardButton chooseOne = new ChooseCardButton();
        chooseOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                side = 1;
                updateStarterBorders();
                choiceSideStarter = view.getCardsPath().get(0);
            }
        });

        ChooseCardButton chooseTwo = new ChooseCardButton();
        chooseTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                side = 2;
                updateStarterBorders();
                choiceSideStarter = view.getCardsPath().get(1);
            }
        });

        ChooseCardButton chooseThree = new ChooseCardButton();
        chooseThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                updateChoiceBorders();
                choiceObjCard = view.getCardsPath().get(2);
            }
        });

        ChooseCardButton chooseFour = new ChooseCardButton();
        chooseFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                updateChoiceBorders();
                choiceObjCard = view.getCardsPath().get(3);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (side != 0 && choice != 0) {
                    try {
                        tcpClient.sendSetObjStrater(side, choice);
                        dispose();
                        // Open GameAreaFrame
                        new GameAreaFrame(tcpClient, view, color, num, choiceSideStarter, choiceObjCard, username); // Use actual color and num
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(button, "Please select the side of the starter card and the objective card", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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

        add(container, BorderLayout.CENTER);
        pack();
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void updateStarterBorders() {
        if (side == 1) {
            backSideStarter.setBorder(blueBorder);
            frontSideStarter.setBorder(emptyBorder);
        } else if (side == 2) {
            backSideStarter.setBorder(emptyBorder);
            frontSideStarter.setBorder(blueBorder);
        }
    }

    private void updateChoiceBorders() {
        if (choice == 1) {
            firstObjectCard.setBorder(blueBorder);
            secondObjectCard.setBorder(emptyBorder);
        } else if (choice == 2) {
            firstObjectCard.setBorder(emptyBorder);
            secondObjectCard.setBorder(blueBorder);
        }
    }
}
