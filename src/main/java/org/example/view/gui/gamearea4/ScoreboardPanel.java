package org.example.view.gui.gamearea4;

import org.example.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A panel that displays the scoreboard for the game, including public objectives and player tokens.
 */
public class ScoreboardPanel extends JPanel {
    private BufferedImage scoreboard; // The image of the scoreboard
    private BufferedImage obj1; // The image of the first public objective
    private BufferedImage obj2; // The image of the second public objective
    private List<BufferedImage> tokens = new ArrayList<>(); // List of player tokens

    private View view; // Reference to the game view
    private HashMap<Integer, List<Integer>> coordinates = new HashMap<>(); // Map of coordinates for token positions

    /**
     * Constructs a ScoreboardPanel with the specified view.
     *
     * @param view The game view.
     * @throws IOException If an I/O error occurs while loading images.
     */
    public ScoreboardPanel(View view) throws IOException {
        this.view = view;

        // Initialize the coordinates map with positions for tokens on the scoreboard
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

        // Load the scoreboard image
        scoreboard = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/plateau.png"));

        // Load the public objective images
        for (int i = 0; i < 2; i++) {
            int objective = view.getPublicObjectives().get(i);
            String path = String.format("images/mid/front/%s.png", objective < 100 ? "0" + objective : String.valueOf(objective));
            BufferedImage objImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
            if (i == 0) {
                obj1 = objImage;
            } else {
                obj2 = objImage;
            }
        }
        List<String> players = new ArrayList<>(view.getColorPlayer().keySet());
        // Load the player tokens
        for (String player : players) {
            String color = view.getColorPlayer().get(player);
            if (color != null) {
                String tokenPath = String.format("images/%sSmall.png", color);
                BufferedImage token = ImageIO.read(getClass().getClassLoader().getResourceAsStream(tokenPath));
                tokens.add(token);
            }
        }
    }

    /**
     * Paints the component, including the scoreboard, public objectives, and player tokens.
     *
     * @param g The Graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the scoreboard
        if (scoreboard != null) {
            g.drawImage(scoreboard, 15, 15, this);
        }
        // Draw the public objectives
        if (obj1 != null) {
            g.drawImage(obj1, 3, 640, this);
        }
        if (obj2 != null) {
            g.drawImage(obj2, 170, 640, this);
        }
        List<String> players = new ArrayList<>(view.getColorPlayer().keySet());
        // Draw the player tokens based on their points
        for (int i = 0; i < tokens.size(); i++) {
            BufferedImage token = tokens.get(i);
            String username = players.get(i);
            Integer points = view.getPoints().get(username);
            List<Integer> values = coordinates.get(points);
            if (values != null) {
                int xOffset = (i % 2) * 35; // Adjust xOffset for overlapping tokens
                int yOffset = (i / 2) * 35; // Adjust yOffset for overlapping tokens
                g.drawImage(token, values.get(0) + xOffset, values.get(1) - yOffset, this);
                repaint();
            }
        }
    }
}