package org.example.view.GUI.mainmenu;

import org.example.view.GUI.GameAreaFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SetInitialGame extends JFrame {
    public SetInitialGame(){
        super ("SetInitialGame");

        setLayout(new GridBagLayout());

        //Components
        JPanel chooseColorPanel = new JPanel(new GridBagLayout());
        JPanel setNumberPlayerPanel = new JPanel(new GridBagLayout());

        JButton confirmButton = new JButton("Confirm!");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new GameAreaFrame();
                    dispose();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //Setting chooseColorPanel
        JRadioButton redRadioButton = new JRadioButton("Red");
        JRadioButton greenRadioButton = new JRadioButton("Green");
        JRadioButton yellowRadioButton = new JRadioButton("Yellow");
        JRadioButton blueRadioButton = new JRadioButton("Blue");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(redRadioButton);
        buttonGroup.add(greenRadioButton);
        buttonGroup.add(yellowRadioButton);
        buttonGroup.add(blueRadioButton);

        Border insideChooseColorBorder = BorderFactory.createTitledBorder("ChooseColor");
        Border outsideBorder = BorderFactory.createEmptyBorder(20,20,20,20);
        Border finalChooseColorBorder = BorderFactory.createCompoundBorder(insideChooseColorBorder, outsideBorder);
        chooseColorPanel.setBorder(finalChooseColorBorder);

        //Layout of chooseColorPanel
        GridBagConstraints gbcRed = new GridBagConstraints();

        gbcRed.gridy = 0;
        gbcRed.gridx = 0;

        gbcRed.weighty = 0.2;
        gbcRed.weightx = 0.0;

        chooseColorPanel.add(redRadioButton, gbcRed);

        GridBagConstraints gbcGreen = new GridBagConstraints();

        gbcGreen.gridy = 1;
        gbcGreen.gridx = 0;

        gbcGreen.weighty = 0.2;
        gbcGreen.weightx = 0.0;

        chooseColorPanel.add(greenRadioButton, gbcGreen);

        GridBagConstraints gbcYellow = new GridBagConstraints();

        gbcYellow.gridy = 0;
        gbcYellow.gridx = 1;

        gbcYellow.weighty = 0.2;
        gbcYellow.weightx = 0.0;

        chooseColorPanel.add(yellowRadioButton, gbcYellow);

        GridBagConstraints gbcBlue = new GridBagConstraints();

        gbcBlue.gridy = 1;
        gbcBlue.gridx = 1;

        gbcBlue.weighty = 0.2;
        gbcBlue.weightx = 0.0;

        chooseColorPanel.add(blueRadioButton, gbcBlue);

        //Setting setNumberPlayerPanel
        JLabel labelNumPlayer = new JLabel("Numero Giocatori: ");
        String[] optionsNumPlayer = {"1", "2", "3", "4"};
        JComboBox menuNumPlayer = new JComboBox(optionsNumPlayer);
        Border insideNumPlayerBorder = BorderFactory.createTitledBorder("Number of players");
        Border outsideNumPlayerBorder = BorderFactory.createEmptyBorder(20,20,20,20);
        Border finalNumPlayerBorder = BorderFactory.createCompoundBorder(insideNumPlayerBorder, outsideBorder);
        setNumberPlayerPanel.setBorder(finalNumPlayerBorder);

        GridBagConstraints gbcLabelNumPlayer = new GridBagConstraints();

        gbcLabelNumPlayer.gridy = 0;
        gbcLabelNumPlayer.gridx = 0;

        setNumberPlayerPanel.add(labelNumPlayer, gbcLabelNumPlayer);

        GridBagConstraints gbcMenuNumPlayer = new GridBagConstraints();

        gbcMenuNumPlayer.gridy = 0;
        gbcMenuNumPlayer.gridx = 1;

        setNumberPlayerPanel.add(menuNumPlayer, gbcMenuNumPlayer);


        //Layout
        GridBagConstraints gbcChooseColor = new GridBagConstraints();

        gbcChooseColor.gridy = 0;
        gbcChooseColor.gridx = 0;

        gbcChooseColor.weighty = 0.3;
        gbcChooseColor.weightx = 0.0;

        add(chooseColorPanel, gbcChooseColor);

        GridBagConstraints gbcSetNumberPlayer = new GridBagConstraints();

        gbcSetNumberPlayer.gridy = 1;
        gbcSetNumberPlayer.gridx = 0;

        gbcSetNumberPlayer.weighty = 0.3;
        gbcSetNumberPlayer.weightx = 0.0;

        add(setNumberPlayerPanel, gbcSetNumberPlayer);

        GridBagConstraints gbcConfirmButton = new GridBagConstraints();

        gbcConfirmButton.gridy = 2;
        gbcConfirmButton.gridx = 0;

        gbcConfirmButton.weighty = 0.7;
        gbcConfirmButton.weightx = 0.0;

        gbcConfirmButton.gridheight = 1;
        gbcConfirmButton.gridwidth = 2;

        add(confirmButton, gbcConfirmButton);

        //Frame Engine
        setVisible(true);
        setSize(new Dimension(300, 400));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
