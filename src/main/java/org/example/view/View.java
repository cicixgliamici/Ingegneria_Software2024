package org.example.view;

import org.example.model.deck.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public abstract class View {
    protected List<String> Hand= new ArrayList<>();
    protected List<String> PlayerCardArea =new ArrayList<>();
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

    /** Messages of success and fail from the server
     */
    public void message(int x){};
}
