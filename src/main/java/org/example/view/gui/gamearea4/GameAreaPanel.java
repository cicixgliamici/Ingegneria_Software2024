package org.example.view.gui.gamearea4;

import org.example.view.gui.utilities.Coordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameAreaPanel extends JPanel{
    private JLabel token1;
    private JLabel token2;
    private JLabel token3;
    private JLabel token4;
    private PlayCardArea playCardArea;
    private static Coordinates mouseDownCompCoords;
    private JLabel card1;
    private JLabel card2;
    private JLabel card3;
    private JLabel secretObjective;
    private BufferedImage backgroundImg;

    public GameAreaPanel(String color, String num) throws IOException {
        setLayout(new GridBagLayout());

        backgroundImg = ImageIO.read(new File("src/main/resources/images/gamearea.png"));

        BufferedImage img1 = ImageIO.read(new File("src/main/resources/images/minicard.png"));
        Icon ic1 = new ImageIcon(img1);
        card1 = new JLabel(ic1);

        /*card1.setTransferHandler(new TransferHandler("text"));
        card1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JLabel label = (JLabel) e.getSource();
                TransferHandler handler = label.getTransferHandler();
                handler.exportAsDrag(label, e, TransferHandler.COPY);
            }
        });*/
        card1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                handler.exportAsDrag(c, e, TransferHandler.COPY);
                //playCardArea.insertCard(e.getX(), e.getY(), "src/main/resources/images/minicard.png");
            }
            public void mouseReleased(MouseEvent e){
                playCardArea.insertCard(e.getX(), e.getY(), "src/main/resources/images/minicard.png");
            }
        });

        card1.setTransferHandler(new TransferHandler("icon") {
            protected Transferable createTransferable(JComponent c) {
                return new Transferable() {
                    @Override
                    public DataFlavor[] getTransferDataFlavors() {
                        return new DataFlavor[] { DataFlavor.imageFlavor };
                    }

                    @Override
                    public boolean isDataFlavorSupported(DataFlavor flavor) {
                        return flavor.equals(DataFlavor.imageFlavor);
                    }

                    @Override
                    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                        return ((ImageIcon) card1.getIcon()).getImage();
                    }
                };
            }
        });



        BufferedImage img2 = ImageIO.read(new File("src/main/resources/images/minicard.png"));
        Icon ic2 = new ImageIcon(img2);
        card2 = new JLabel(ic2);


        BufferedImage img3 = ImageIO.read(new File("src/main/resources/images/minicard.png"));
        Icon ic3 = new ImageIcon(img3);
        card3 = new JLabel(ic3);

        BufferedImage img4 = ImageIO.read(new File("src/main/resources/images/minicard.png"));
        Icon ic4 = new ImageIcon(img4);
        secretObjective = new JLabel(ic4);

        switch (num){
            case "1":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo2 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon2 = new ImageIcon(logo2);
                token2 = new JLabel(icon2);
                token2.setVisible(false);
                BufferedImage logo3 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon3 = new ImageIcon(logo3);
                token3 = new JLabel(icon3);
                token3.setVisible(false);
                BufferedImage logo4 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon4 = new ImageIcon(logo4);
                token4 = new JLabel(icon4);
                token4.setVisible(false);
                break;

            case "2":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo5 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                Icon icon5 = new ImageIcon(logo5);
                token2 = new JLabel(icon5);
                BufferedImage logo6 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon6 = new ImageIcon(logo6);
                token3 = new JLabel(icon6);
                token3.setVisible(false);
                BufferedImage logo7 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon7 = new ImageIcon(logo7);
                token4 = new JLabel(icon7);
                token4.setVisible(false);
                break;

            case "3":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo8 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon8 = new ImageIcon(logo8);
                token2 = new JLabel(icon8);
                BufferedImage logo9 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon9 = new ImageIcon(logo9);
                token3 = new JLabel(icon9);
                BufferedImage logo10 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon10 = new ImageIcon(logo10);
                token4 = new JLabel(icon10);
                token4.setVisible(false);
                break;

            case "4":
                if(color.equals("Blue")){
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_bleu.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Yellow")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else if (color.equals("Red")) {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/red.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                } else {
                    BufferedImage logo1 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                    Icon icon1 = new ImageIcon(logo1);
                    token1 = new JLabel(icon1);
                }
                BufferedImage logo11 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_jaune.png"));
                Icon icon11 = new ImageIcon(logo11);
                token2 = new JLabel(icon11);
                BufferedImage logo12 = ImageIO.read(new File("src/main/resources/images/red.png"));
                Icon icon12 = new ImageIcon(logo12);
                token3 = new JLabel(icon12);
                BufferedImage logo13 = ImageIO.read(new File("src/main/resources/images/CODEX_pion_vert.png"));
                Icon icon13 = new ImageIcon(logo13);
                token4 = new JLabel(icon13);
                break;
        }

        //PlayCardArea

        playCardArea = new PlayCardArea() {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File("src/main/resources/images/pannotavolo.jpg")));
            Image img = icon.getImage();
            {setOpaque(false);}
            public void paintComponent(Graphics graphics){
                graphics.drawImage(img,0,0, this);
                super.paintComponent(graphics);}
        };
        /*playCardArea.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.stringFlavor);
            }

            public boolean importData(TransferHandler.TransferSupport support) {
                try {
                    String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
                    ((JPanel) support.getComponent()).add(new JLabel(data));
                    support.getComponent().revalidate();
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });*/
        playCardArea.setTransferHandler(new TransferHandler() {
            public boolean canImport(TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.imageFlavor);
            }

            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    Transferable t = support.getTransferable();
                    Image image = (Image) t.getTransferData(DataFlavor.imageFlavor);
                    // Handle the dropped image as needed
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        });


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

        JScrollPane jScrollPane = new JScrollPane(playCardArea);

        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        playCardArea.insertCard(500,500,"src/main/resources/images/card/001.png");


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

        gbc.weightx = 0.75;
        gbc.weighty = 0.99;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 4;
        //gbc.anchor = GridBagConstraints.LINE_START;

        add(jScrollPane, gbc);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImg != null) {
            // Ottieni le dimensioni del pannello
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Disegna l'immagine ridimensionata per coprire l'intera area del pannello
            g.drawImage(backgroundImg, 0, 0, panelWidth, panelHeight, this);
        }
    }

}
