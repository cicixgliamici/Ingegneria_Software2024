package org.example.view.gui.selectobjstarter3;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.gamearea4.GameAreaFrame;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.setgame2.SetInitialGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectObjStarter extends JFrame {

    private View view;
    private int side;
    private int choice;
    private TCPClient tcpClient;
    private String username;
    private String color;
    private String num;
    private EvListener evListener;

    public SelectObjStarter(TCPClient tcpClient, String username, View view, String color, String num) throws IOException {
        super("Select StarterCard and ObjectedCard");
        this.tcpClient = tcpClient;
        this.username = username;
        this.view = view;
        this.color=color;
        this.num=num;

        setLayout(new GridBagLayout());

        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("/images/card/102.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Icon icon = new ImageIcon(logo);
        JLabel backSideStarter = new JLabel(icon);
        JLabel frontSideStarter = new JLabel(icon);
        JLabel firstObjectCard = new JLabel(icon);
        JLabel secondObjectCard = new JLabel(icon);

        JButton button = new JButton("Confirm!");

        ChooseCardButton chooseOne = new ChooseCardButton();
        chooseOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                side = 1;
                System.out.println("Left upper card clicked!");
            }
        });

        ChooseCardButton chooseTwo = new ChooseCardButton();
        chooseTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                side = 2;
                System.out.println("Left lower card clicked!");
            }
        });

        ChooseCardButton chooseThree = new ChooseCardButton();
        chooseThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 1;
                System.out.println("Right upper card clicked!");
            }
        });

        ChooseCardButton chooseFour = new ChooseCardButton();
        chooseFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice = 2;
                System.out.println("Right lower card clicked!");
            }
        });

        button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        tcpClient.sendSetObjStrater(side, choice);
                        dispose();
                        // Open GameAreaFrame
                        new GameAreaFrame(username, color, num); // Use actual color and num
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.5;
        gbc.weightx = 0.5;

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(backSideStarter, gbc);
        add(chooseOne, gbc);

        gbc.gridy = 1;
        add(frontSideStarter, gbc);
        add(chooseTwo, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        add(firstObjectCard, gbc);
        add(chooseThree, gbc);

        gbc.gridy = 1;
        add(secondObjectCard, gbc);
        add(chooseFour, gbc);

        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 2;
        gbcButton.gridwidth = 2;
        gbcButton.weighty = 0.3;

        add(button, gbcButton);

        pack();
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
