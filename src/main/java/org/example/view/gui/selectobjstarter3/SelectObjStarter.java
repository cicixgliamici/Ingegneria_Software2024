package org.example.view.gui.selectobjstarter3;

import org.example.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SelectObjStarter extends JFrame {

    private View view;


    public SelectObjStarter(/*View view*/){
        super("Select StarterCard and ObjectedCard");
        //this.view = view;

        setLayout(new GridBagLayout());

        //Components
        BufferedImage logo = null;
        try {
            logo = ImageIO.read(getClass().getResource("/images/102.png"));
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
                System.out.println("Mi hai cliccato!!");
            }
        });
        ChooseCardButton chooseTwo = new ChooseCardButton();
        ChooseCardButton chooseThree = new ChooseCardButton();
        ChooseCardButton chooseFour = new ChooseCardButton();

        //Layout
        GridBagConstraints gbcBSS = new GridBagConstraints();

        gbcBSS.gridy = 0;
        gbcBSS.gridx = 0;
        gbcBSS.weighty = 0.5;
        gbcBSS.weightx = 0.5;

        add(backSideStarter, gbcBSS);
        add(chooseOne, gbcBSS);

        GridBagConstraints gbcFSS = new GridBagConstraints();

        gbcFSS.gridy = 1;
        gbcFSS.gridx = 0;
        gbcFSS.weighty = 0.5;
        gbcFSS.weightx = 0.5;

        add(frontSideStarter, gbcFSS);
        add(chooseTwo, gbcFSS);

        GridBagConstraints gbcFOC = new GridBagConstraints();

        gbcFOC.gridy = 0;
        gbcFOC.gridx = 1;
        gbcFOC.weighty = 0.5;
        gbcFOC.weightx = 0.5;

        add(firstObjectCard, gbcFOC);
        add(chooseThree, gbcFOC);

        GridBagConstraints gbcSOC = new GridBagConstraints();

        gbcSOC.gridy = 1;
        gbcSOC.gridx = 1;
        gbcSOC.weighty = 0.5;
        gbcSOC.weightx = 0.5;

        add(secondObjectCard, gbcSOC);
        add(chooseFour, gbcSOC);

        GridBagConstraints gbcButton = new GridBagConstraints();

        gbcButton.gridx = 0;
        gbcButton.gridy = 2;
        gbcButton.weighty = 0.3;
        gbcButton.weightx = 0.0;

        gbcButton.gridheight = 1;
        gbcButton.gridwidth = 2;

        add(button, gbcButton);

        //Settings of frame
        pack();
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
