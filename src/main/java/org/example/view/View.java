package org.example.view;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    protected List<Integer> Hand= new ArrayList<>();
    protected List<Integer> PlayerCardArea =new ArrayList<>();
    protected Integer SecretObjective;
    final int N=9;
    final int M=(N-1)/2;
    protected String[][] grid = new String[N][N];
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


    public void updateHand(int id ) {
        Hand.add(id);
    };
    public void updatePlayerCardArea(int id ){
        PlayerCardArea.add(id);
    }
    public void removeHand(int id) {
        Hand.remove(id);
    };
    public void removePlayerCardArea(int id ){
        PlayerCardArea.remove(id);
    }

    public void playCardInGrid(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[x + M][y + M] = "x";  // Offset by 4 to center [0][0] in a 9x9 grid
        } else {
            System.out.println("Invalid position: (" + x + "," + y + ")");
        }
    }

    public void removeCardFromGrid(int x, int y) {
        if (isValidPosition(x, y)) {
            grid[x + M][y + M] = "";  // Offset by 4 to center [0][0] in a 9x9 grid
        } else {
            System.out.println("Invalid position: (" + x + "," + y + ")");
        }
    }

    private boolean isValidPosition(int x, int y) {
        return x >= -4 && x <= 4 && y >= -4 && y <= 4;
    }


    public void printGrid() {
        System.out.println("Current grid state:");
        System.out.println("    -4  -3  -2  -1   0   1   2   3   4  ");

        for (int i = 0; i < grid.length; i++) {
            System.out.printf("%3d", i - 4);
            System.out.print(" ");
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j].isEmpty() ? "   " : " x ");
                if (j < grid[i].length - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < grid.length - 1) {
                System.out.print("    ");
                for (int j = 0; j < grid[i].length; j++) {
                    System.out.print("---");
                    if (j < grid[i].length - 1) {
                        System.out.print("+");
                    }
                }
                System.out.println();
            }
        }
    }

    /** Messages of success and fail from the server
     */
    public void message(int x){};
}
