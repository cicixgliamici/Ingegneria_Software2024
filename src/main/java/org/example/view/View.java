package org.example.view;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import org.example.view.gui.gamearea4.DrawingCardPanel;
import org.example.view.gui.gamearea4.GameAreaPanel;
import org.example.view.gui.listener.DrawingCardPanelListener;
import org.example.view.gui.listener.GameAreaPanelListener;
import org.example.view.gui.listener.EvListener;
import org.example.view.gui.listener.Event;
import org.example.view.gui.utilities.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class View {

    protected int flag;
    protected int numConnection;
    protected int validPlay;
    protected int turn;
    final int N = 9;
    final int M = (N - 1) / 2;
    protected Integer SecretObjective;
    protected boolean isFirst;
    protected volatile boolean matchStarted;
    protected List<Integer> cardsId;
    protected List<Integer> drawableCards;
    protected List<Integer> Hand = new ArrayList<>();
    protected List<Integer> PlayerCardArea = new ArrayList<>();
    protected List<Integer> PublicObjectives = new ArrayList<>();
    protected List<String> cardsPath;
    protected List<String> colors;
    protected List<String> players;
    protected List<EvListener> listeners = new ArrayList<>();
    protected Map<String, String> colorPlayer = new HashMap<>();
    protected Map<Integer, Coordinates> map = new HashMap<>();
    protected Map<String, Integer> points;
    protected DrawingCardPanel drawingCardPanel;
    protected GameAreaPanel gameAreaPanel;

    public View() {
    }


    public void removeVisibleArea(int id) {}
    public abstract void setFirst();
    public abstract void printGrid();
    public abstract void printPlayerCardArea();
    public abstract void printHand();
    public abstract void numCon(int num);
    public abstract void drawnCard(int id);
    public abstract void message(int x);
    public abstract void setHand(int side, int choice);
    public abstract void pubObj(int id1, int id2);
    public abstract void playedCard(int id, int side, int x, int y);
    public abstract void unplayable(int id, int x, int y);
    public abstract void placeholder(int id, int x, int y);
    public abstract void hasDrawn(String username, int id);
    public abstract void hasPlayed(String username, int id);
    public abstract void points(String username, int points);
    public abstract void newConnection(String player, String color) ;
    public abstract void updateSetupUI(String[] colors, boolean isFirst);
    public abstract void firstHand(int id1, int id2, int id3, int id4, int id5, int id6);
    public abstract void visibleArea(int id1, int id2, int id3, int id4, int id5, int id6);
    public abstract void order(String us1, String us2, String us3, String us4);
    public abstract void color(String color1, String color2, String color3, String color4);


    public Map<String, String> getColorPlayer(){
        return colorPlayer;
    }

    public void Interpreter(String message) {
        String[] parts = message.split(":");
        if (parts.length < 1) {
            System.out.println("Invalid command received.");
            return;
        }
        String command = parts[0];
        String[] parameters = parts.length > 1 ? parts[1].split(",") : new String[0];
        try {
            Method[] methods = this.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(command) && method.getParameterCount() == parameters.length) {
                    Class<?>[] paramTypes = method.getParameterTypes();
                    Object[] paramValues = new Object[parameters.length];
                    for (int i = 0; parameters != null && i < parameters.length; i++) {
                        if (paramTypes[i] == int.class) {
                            paramValues[i] = Integer.parseInt(parameters[i]);
                        } else if (paramTypes[i] == String.class) {
                            paramValues[i] = parameters[i];
                        }
                    }
                    method.invoke(this, paramValues);
                    return;
                }
            }
            System.out.println("No such method exists: " + command);
        } catch (Exception e) {
            System.out.println("Error executing command " + command + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public JSONObject getCardById(int id) {
        JSONParser parser = new JSONParser();
        String[] filePaths = {
                "src/main/resources/Card.json",
                "src/main/resources/GoldCard.json",
                "src/main/resources/ObjectiveCard.json",
                "src/main/resources/StarterCard.json"
        };
        for (String filePath : filePaths) {
            try {
                JSONArray cards = (JSONArray) parser.parse(new FileReader(filePath));
                for (Object cardObj : cards) {
                    JSONObject card = (JSONObject) cardObj;
                    if (((Long) card.get("id")).intValue() == id) {
                        return card;
                    }
                }
            } catch (IOException | ParseException e) {
                System.err.println("Error reading or parsing the JSON file: " + e.getMessage());
            }
        }
        return null;
    }

    public void updateHand(int id) {
        Hand.add(id);
        notifyListeners(new Event(this, "handUpdated"));
    }


    public void updatePlayerCardArea(int id) {
        PlayerCardArea.add(id);
    }

    public void removeHand(int id) {
        Hand.removeIf(id1 -> id1.equals(id));
    }

    public void removePlayerCardArea(int id) {
        PlayerCardArea.remove(id);
    }

    public void chatC(String username, String message){
        System.out.println(message);
    };

    public boolean isValidPosition(int gridX, int gridY) {
        return gridX >= 0 && gridX < N && gridY >= 0 && gridY < N;
    }

    public int getFlag() {
        return flag;
    }

    public Coordinates getPosition(JSONObject jsonObject) {
        return map.get(jsonObject);
    }

    public List<Integer> getHand() {
        return Hand;
    }

    public List<Integer> getPlayerCardArea() {
        return PlayerCardArea;
    }

    public Integer getSecretObjective() {
        return SecretObjective;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public boolean isFirst() {
        return isFirst;
    }
    public boolean isMatchStarted() {
        return matchStarted;
    }

    public List<String> getColors() {
        return colors;
    }

    public int getNumConnection() {
        return numConnection;
    }

    public List<String> getCardsPath() {
        return cardsPath;
    }

    public List<Integer> getCardsId(){
        return cardsId;
    }

    public List<Integer> getDrawableCards() {
        return drawableCards;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<Integer> getPublicObjectives() {
        return PublicObjectives;
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public int getValidPlay() {
        return validPlay;
    }

    public int isTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setMatchStarted(boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

    public void setGameAreaPanel(GameAreaPanel gameAreaPanel) {
        this.gameAreaPanel = gameAreaPanel;

    }

    public void setDrawingCardPanel(DrawingCardPanel drawingCardPanel) {
        this.drawingCardPanel = drawingCardPanel;

    }

    public void addListener(EvListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(Event event) {
        for (EvListener listener : listeners) {
            try {
                System.out.println("Chiamato da " + listener);
                listener.eventListener(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMapping(Integer integer, int x, int y) {
        map.put(integer, new Coordinates(x, y));
    }

}