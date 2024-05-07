package org.example.view;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    protected List<String> Hand= new ArrayList<>();
    protected List<String> PlayerCardArea =new ArrayList<>();
    // Interpreter of the Server's messages
    public void Interpreter(String message){};

    // Interpreted messages from server
    public void drawnCard(int id){};
    public void playedCard(int id, int x, int y){};
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6){};
    public void setHand(int side, int choice){};


    public void UpdateHand(String message) {
        Hand.add(message);
    };
    public void UpdatePlayerCardArea(String message){
        PlayerCardArea.add(message);
    }
    public void RemoveHand(String message) {
        Hand.remove(message);
    };
    public void RemovePlayerCardArea(String message){
        PlayerCardArea.remove(message);
    }
}
