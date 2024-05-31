package org.example.view.gui.gamearea4;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.selectobjstarter3.ChooseCardButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingCardPanel extends JPanel {

    public View view;
    public int draw;
    public int drawChoose ;
    public TCPClient tcpClient;
    private JLabel coveredCard1;
    private JLabel coveredCard2;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel card4;
    private String path1;
    private String path2;
    private String path3;
    private String path4;
    private String path5;
    private String path6;
    private List<String> paths;
    // Class Variables
    private Border selectedBorder = new LineBorder(Color.BLUE, 2);
    private Border defaultBorder = new LineBorder(Color.WHITE, 2);
    private JLabel[] cardLabels = new JLabel[6];

    public DrawingCardPanel(TCPClient tcpClient, View view) throws IOException {
        this.view = view;
        this.paths=new ArrayList<>();
        this.tcpClient=tcpClient;
        setLayout(new GridBagLayout());

        updateCards();

        /*BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon1 = new ImageIcon(logo1);
        coveredCard1 = new JLabel(icon1);*/



        GridBagConstraints gbFactory = new GridBagConstraints();

        gbFactory.fill = GridBagConstraints.BOTH;
        gbFactory.insets = new Insets(5, 0, 5, 0);

        for(int i = 0; i< paths.size(); i++){
            JLabel cardlabel =createCardLabel(paths.get(i), i);
            gbFactory.gridy = i;
            add(cardlabel, gbFactory);
            cardLabels[i]=cardlabel;
        }

    }
    private JLabel createCardLabel(String path, int cardIndex) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBorder(defaultBorder);  // Imposta il bordo predefinito

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
                    updateCards();
                }
            }
        });
        return label;
    }


    private void updateCardSelection(int selectedCardIndex) {
        for (int i = 0; i < cardLabels.length; i++) {
            if (i == selectedCardIndex) {
                cardLabels[i].setBorder(selectedBorder);  // Seleziona il bordo per la carta cliccata
            } else {
                cardLabels[i].setBorder(defaultBorder);  // Ripristina il bordo predefinito per le altre carte
            }
        }
    }

    public void updateCards(){
        BufferedImage image1 = null;
        Icon icon1 = null;
        try {
            if(view.getDrawableCards().get(0) <= 10){
                path1 = "src/main/resources/images/big/back/001.png";

                image1 = ImageIO.read(new File(path1));
                icon1 = new ImageIcon(image1);
            } else if (view.getDrawableCards().get(0) <=20 ) {
                path1 = "src/main/resources/images/big/back/011.png";

                image1 = ImageIO.read(new File(path1));
                icon1 = new ImageIcon(image1);
            }
            else if(view.getDrawableCards().get(0) <=30){
                path1 = "src/main/resources/images/big/back/021.png";
                image1 = ImageIO.read(new File(path1));
                icon1 = new ImageIcon(image1);
            }
            else if(view.getDrawableCards().get(0) <=40){
                path1 = "src/main/resources/images/big/back/031.png";
                image1 = ImageIO.read(new File(path1));
                icon1 = new ImageIcon(image1);
            }
            paths.add(path1);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        coveredCard1 = new JLabel(icon1);

        /*BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon2 = new ImageIcon(logo2);
        covredCard2 = new JLabel(icon2);*/

        BufferedImage image2 = null;
        Icon icon2 = null;
        try {
            if(view.getDrawableCards().get(1) <= 50){
                path2 = "src/main/resources/images/big/back/041.png";
                image2 = ImageIO.read(new File(path2));
                icon2 = new ImageIcon(image2);
            } else if (view.getDrawableCards().get(1) <=60 ) {
                path2 = "src/main/resources/images/big/back/051.png";
                image2 = ImageIO.read(new File(path2));
                icon2 = new ImageIcon(image2);
            }
            else if(view.getDrawableCards().get(1) <=70){
                path2 = "src/main/resources/images/big/back/061.png";
                image2 = ImageIO.read(new File(path2));
                icon2 = new ImageIcon(image2);
            }
            else if(view.getDrawableCards().get(1) <=80){
                path2 = "src/main/resources/images/big/back/071.png";
                image2 = ImageIO.read(new File(path2));
                icon2 = new ImageIcon(image2);
            }
            paths.add(path2);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        coveredCard2 = new JLabel(icon2);

        /*BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon3 = new ImageIcon(logo3);
        card1 = new JLabel(icon3);*/

        BufferedImage image3 = null;
        try {
            if(view.getDrawableCards().get(2) < 10){
                path3 ="src/main/resources/images/big/front/00" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png";
                image3 = ImageIO.read(new File(path3));
            } else if (view.getDrawableCards().get(2) > 99) {
                path3= "src/main/resources/images/big/front/" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png";
                image3 = ImageIO.read(new File(path3));
            }
            else {
                path3 = "src/main/resources/images/big/front/0" + String.valueOf(view.getDrawableCards().get(2)).toString() + ".png";
                image3 = ImageIO.read(new File(path3));

            }
            paths.add(path3);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon3 = new ImageIcon(image3);
        card1 = new JLabel(icon3);

        /*BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon4 = new ImageIcon(logo4);
        card2 = new JLabel(icon4);*/

        BufferedImage image4 = null;
        try {
            String path= null;
            if(view.getDrawableCards().get(3) < 10){
                path4= "src/main/resources/images/big/front/00" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png";
                image4 = ImageIO.read(new File(path4));
            } else if (view.getDrawableCards().get(3) > 99) {
                path4= "src/main/resources/images/big/front/" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png";
                image4 = ImageIO.read(new File(path4));
            }
            else {
                path4= "src/main/resources/images/big/front/0" + String.valueOf(view.getDrawableCards().get(3)).toString() + ".png";
                image4 = ImageIO.read(new File(path4));

            }
            paths.add(path4);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon4 = new ImageIcon(image4);
        card2 = new JLabel(icon4);

        /*BufferedImage logo5 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon5 = new ImageIcon(logo5);
        card3 = new JLabel(icon5);*/

        BufferedImage image5 = null;
        try {
            path5= "src/main/resources/images/big/front/0" + String.valueOf(view.getDrawableCards().get(4)).toString() + ".png";
            image5 = ImageIO.read(new File(path5));
            paths.add(path5);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon5 = new ImageIcon(image5);
        card3 = new JLabel(icon5);

        /*BufferedImage logo6 = ImageIO.read(new File("src/main/resources/images/big/big/front/001.png"));
        Icon icon6 = new ImageIcon(logo6);
        card4 = new JLabel(icon6);*/

        BufferedImage image6 = null;
        try {
            if(view.getDrawableCards().get(5) < 10){
                path6 = "src/main/resources/images/big/front/00" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png";
                image6 = ImageIO.read(new File(path6));
            } else if (view.getDrawableCards().get(5) > 99) {
                path6 = "src/main/resources/images/big/front/" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png";
                image6 = ImageIO.read(new File(path6));
            }
            else {
                path6 = "src/main/resources/images/big/front/0" + String.valueOf(view.getDrawableCards().get(5)).toString() + ".png";
                image6 = ImageIO.read(new File(path6));

            }
            paths.add(path6);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Icon icon6 = new ImageIcon(image6);
        card4 = new JLabel(icon6);
    }


}
