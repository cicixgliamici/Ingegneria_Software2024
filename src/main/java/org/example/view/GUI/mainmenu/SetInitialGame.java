package org.example.view.GUI.mainmenu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SetInitialGame extends JFrame {
    public SetInitialGame(){
        super ("SetInitialGame");

        setLayout(new GridBagLayout());

        //Components
        JPanel chooseColorPanel = new JPanel(new GridBagLayout());
        JPanel setNumberPlayerPanel = new JPanel(new GridBagLayout());

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

        JButton confirmButton = new JButton("Confirm!");

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

        GridBagConstraints gbcConfirmButton = new GridBagConstraints();

        gbcConfirmButton.gridy = 2;
        gbcConfirmButton.gridx = 0;

        gbcConfirmButton.weighty = 0.8;
        gbcConfirmButton.weightx = 0.0;

        gbcConfirmButton.gridheight = 1;
        gbcConfirmButton.gridwidth = 2;

        chooseColorPanel.add(confirmButton, gbcConfirmButton);

        //Setting setNumberPlayerPanel
        JLabel labelNumPlayer = new JLabel("Numero Giocatori: ");
        String[] optionsNumPlayer = {"1", "2", "3", "4"};
        JComboBox menuNumPlayer = new JComboBox(optionsNumPlayer);
        Border insideNumPlayerBorder = BorderFactory.createTitledBorder("Number of player");
        Border outsideNumPlayerBorder = BorderFactory.createEmptyBorder(20,20,20,20);
        Border finalNumPlayerBorder = BorderFactory.createCompoundBorder(insideChooseColorBorder, outsideBorder);
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

        add(chooseColorPanel, gbcChooseColor);

        GridBagConstraints gbcSetNumberPlayer = new GridBagConstraints();

        gbcSetNumberPlayer.gridy = 1;
        gbcSetNumberPlayer.gridx = 0;

        add(setNumberPlayerPanel, gbcSetNumberPlayer);

        //Frame Engine
        setVisible(true);
        setSize(new Dimension(300, 500));
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
