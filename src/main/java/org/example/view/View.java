package org.example.view;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    protected List<String> Hand= new ArrayList<>();
    protected List<String> PlayerCardArea =new ArrayList<>();
    protected String[][] grid =new String[5][5];
    // Interpreter of the Server's messages
    public void Interpreter(String message){};
    public JSONObject getCardById(int id) {
        return  null;
    };

    // Interpreted messages from server
    public void drawnCard(int id){};
    public void hasDrawn(String username, int id){};
    public void playedCard(int id, int x, int y){};
    public void hasPlayed(String username, int id){};
    public void unplayable(int id, int x, int y){};
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6){};
    public void setHand(int side, int choice){};
    public void pubObj(int id1, int id2){};


    public void updateHand(String message) {
        Hand.add(message);
    };
    public void updatePlayerCardArea(String message){
        PlayerCardArea.add(message);
    }
    public void removeHand(String message) {
        Hand.remove(message);
    };
    public void removePlayerCardArea(String message){
        PlayerCardArea.remove(message);
    }

    public void playCardInGrid(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[x][y] = "x";
        } else {
            System.out.println("Invalid position: (" + x + "," + y + ")");
        }
    }

    public void removeCardFromGrid(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[x][y] = "";
        } else {
            System.out.println("Invalid position: (" + x + "," + y + ")");
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
    public void printGrid() {
        System.out.println("Current grid state:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j].isEmpty() ? " " : "x");
                if (j < grid[i].length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < grid.length - 1) {
                System.out.println("---+---+---+---+---");
            }
        }
    }

    /** Messages of success and fail from the server
     */
    public void message(int x){};
}
