package org.example.view.gui.gamearea4;

import org.example.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ScoreboardPanel extends JPanel {
    private BufferedImage scoreboard;
    private BufferedImage obj1;
    private BufferedImage obj2;
    private BufferedImage token1;
    private BufferedImage token2;
    private BufferedImage token3;
    private BufferedImage token4;

    public View view;
    HashMap<Integer, List<Integer>> coordinates = new HashMap<>();

    public ScoreboardPanel(View view) throws IOException {

        this.view = view;

        coordinates.put(0, List.of(60, 575));
        coordinates.put(1, List.of(130, 575));
        coordinates.put(2, List.of(200, 575));
        coordinates.put(3, List.of(240, 510));
        coordinates.put(4, List.of(170, 510));
        coordinates.put(5, List.of(100, 510));
        coordinates.put(6, List.of(30, 510));
        coordinates.put(7, List.of(30, 445));
        coordinates.put(8, List.of(100, 445));
        coordinates.put(9, List.of(170, 445));
        coordinates.put(10, List.of(240, 445));
        coordinates.put(11, List.of(240, 380));
        coordinates.put(12, List.of(170, 380));
        coordinates.put(13, List.of(100, 380));
        coordinates.put(14, List.of(30, 380));
        coordinates.put(15, List.of(30, 315));
        coordinates.put(16, List.of(100, 315));
        coordinates.put(17, List.of(170, 315));
        coordinates.put(18, List.of(240, 315));
        coordinates.put(19, List.of(240, 250));
        coordinates.put(20, List.of(135, 220));
        coordinates.put(21, List.of(30, 250));
        coordinates.put(22, List.of(30, 185));
        coordinates.put(23, List.of(30, 120));
        coordinates.put(24, List.of(70, 65));
        coordinates.put(25, List.of(135, 55));
        coordinates.put(26, List.of(200, 65));
        coordinates.put(27, List.of(240, 120));
        coordinates.put(28, List.of(240, 185));
        coordinates.put(29, List.of(135, 135));

        scoreboard = ImageIO.read(new File("src/main/resources/images/plateau.png"));

        if(view.getPublicObjectives().get(0) < 100) {
            System.out.println(view.getPublicObjectives().get(0));
            obj1 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
        }
        else if(view.getPublicObjectives().get(0) >= 100){
            System.out.println(view.getPublicObjectives().get(0));
            obj1 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(0)).toString() + ".png"));
        }
        if(view.getPublicObjectives().get(1) < 100) {
            System.out.println(view.getPublicObjectives().get(1));
            obj2 = ImageIO.read(new File("src/main/resources/images/mid/front/0" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
        }
        else if(view.getPublicObjectives().get(1) >= 100){
            System.out.println(view.getPublicObjectives().get(1));
            obj2 = ImageIO.read(new File("src/main/resources/images/mid/front/" + String.valueOf(view.getPublicObjectives().get(1)).toString() + ".png"));
        }

        System.out.println(view.getColorPlayer());

        if(view.getColorPlayer().get(view.getPlayers().get(0)).equals("Blue")){
            token1 = ImageIO.read(new File("src/main/resources/images/BlueSmall.png"));
        } else if (view.getColorPlayer().get(view.getPlayers().get(0)).equals("Green")) {
            token1 = ImageIO.read(new File("src/main/resources/images/GreenSmall.png"));
        } else if (view.getColorPlayer().get(view.getPlayers().get(0)).equals("Yellow")) {
            token1 = ImageIO.read(new File("src/main/resources/images/YellowSmall.png"));
        } else if (view.getColorPlayer().get(view.getPlayers().get(0)).equals("Red")) {
            token1 = ImageIO.read(new File("src/main/resources/images/RedSmall.png"));
        }

        if(view.getPlayers().size() > 1){
            if(view.getColorPlayer().get(view.getPlayers().get(1)).equals("Blue")){
                token2 = ImageIO.read(new File("src/main/resources/images/BlueSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(1)).equals("Green")) {
                token2 = ImageIO.read(new File("src/main/resources/images/GreenSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(1)).equals("Yellow")) {
                token2 = ImageIO.read(new File("src/main/resources/images/YellowSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(1)).equals("Red")) {
                token2 = ImageIO.read(new File("src/main/resources/images/RedSmall.png"));
            }
        }

        if(view.getPlayers().size() > 2){
            if(view.getColorPlayer().get(view.getPlayers().get(2)).equals("Blue")){
                token3 = ImageIO.read(new File("src/main/resources/images/BlueSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(2)).equals("Green")) {
                token3 = ImageIO.read(new File("src/main/resources/images/GreenSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(2)).equals("Yellow")) {
                token3 = ImageIO.read(new File("src/main/resources/images/YellowSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(2)).equals("Red")) {
                token3 = ImageIO.read(new File("src/main/resources/images/RedSmall.png"));
            }
        }

        if(view.getPlayers().size() > 3){
            if(view.getColorPlayer().get(view.getPlayers().get(3)).equals("Blue")){
                token4 = ImageIO.read(new File("src/main/resources/images/BlueSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(3)).equals("Green")) {
                token4 = ImageIO.read(new File("src/main/resources/images/GreenSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(3)).equals("Yellow")) {
                token4 = ImageIO.read(new File("src/main/resources/images/YellowSmall.png"));
            } else if (view.getColorPlayer().get(view.getPlayers().get(3)).equals("Red")) {
                token4 = ImageIO.read(new File("src/main/resources/images/RedSmall.png"));
            }
        }

        System.out.println("La madre di matteo è na troia");
        System.out.println(view.getPoints());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scoreboard != null) {
            g.drawImage(scoreboard, 15, 15, this);
        }
        if (obj1 != null) {
            g.drawImage(obj1, 3, 640, this);
        }
        if (obj2 != null) {
            g.drawImage(obj2, 170, 640, this);
        }
        if(token1 != null){
            String username = view.getPlayers().get(0);
            Integer points = view.getPoints().get(username);
            List<Integer> values = coordinates.get(points);
            g.drawImage(token1, values.get(0), values.get(1), this);
        }
        if(token2 != null && view.getPlayers().size() > 1){
            String username = view.getPlayers().get(1);
            Integer points = view.getPoints().get(username);
            List<Integer> values = coordinates.get(points);
            g.drawImage(token2, values.get(0), values.get(1) - 35, this);
        }
        if(token3 != null && view.getPlayers().size() > 2){
            String username = view.getPlayers().get(2);
            Integer points = view.getPoints().get(username);
            List<Integer> values = coordinates.get(points);
            g.drawImage(token3, values.get(0) + 35, values.get(1), this);
        }
        if(token4 != null && view.getPlayers().size() > 3){
            String username = view.getPlayers().get(3);
            Integer points = view.getPoints().get(username);
            List<Integer> values = coordinates.get(points);
            g.drawImage(token4, values.get(0) + 35, values.get(1) - 35, this);
        }
    }
}