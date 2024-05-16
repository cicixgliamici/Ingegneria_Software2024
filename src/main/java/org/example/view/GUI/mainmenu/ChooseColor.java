package org.example.view.GUI.mainmenu;

import javax.swing.*;
import java.awt.*;

public class ChooseColor extends JFrame {
    public ChooseColor(){
        super ("ChooseColor");

        setLayout(new GridBagLayout());

        //Components
        JRadioButton redRadioButton = new JRadioButton("Red");
        JRadioButton greenRadioButton = new JRadioButton("Green");
        JRadioButton yellowRadioButton = new JRadioButton("Yellow");
        JRadioButton blueRadioButton = new JRadioButton("Blue");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(redRadioButton);
        buttonGroup.add(greenRadioButton);
        buttonGroup.add(yellowRadioButton);
        buttonGroup.add(blueRadioButton);

        JButton confirmButton = new JButton("Confirm!");

        //Layout
        GridBagConstraints gbcRed = new GridBagConstraints();

        gbcRed.gridy = 0;
        gbcRed.gridx = 0;

        gbcRed.weighty = 0.2;
        gbcRed.weightx = 0.0;

        add(redRadioButton, gbcRed);

        GridBagConstraints gbcGreen = new GridBagConstraints();

        gbcGreen.gridy = 1;
        gbcGreen.gridx = 0;

        gbcGreen.weighty = 0.2;
        gbcGreen.weightx = 0.0;

        add(greenRadioButton, gbcGreen);

        GridBagConstraints gbcYellow = new GridBagConstraints();

        gbcYellow.gridy = 0;
        gbcYellow.gridx = 1;

        gbcYellow.weighty = 0.2;
        gbcYellow.weightx = 0.0;

        add(yellowRadioButton, gbcYellow);

        GridBagConstraints gbcBlue = new GridBagConstraints();

        gbcBlue.gridy = 1;
        gbcBlue.gridx = 1;

        gbcBlue.weighty = 0.2;
        gbcBlue.weightx = 0.0;

        add(blueRadioButton, gbcBlue);

        GridBagConstraints gbcConfirmButton = new GridBagConstraints();

        gbcConfirmButton.gridy = 2;
        gbcConfirmButton.gridx = 0;

        gbcConfirmButton.weighty = 0.8;
        gbcConfirmButton.weightx = 0.0;

        gbcConfirmButton.gridheight = 1;
        gbcConfirmButton.gridwidth = 2;

        add(confirmButton, gbcConfirmButton);

        //Frame Engine
        setVisible(true);
        setSize(new Dimension(300, 250));
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
