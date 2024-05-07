package org.example.view;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    protected List<String> Hand= new ArrayList<>();
    protected List<String> PlayerCardArea =new ArrayList<>();
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
    public void Interpreter(String message){};
}
