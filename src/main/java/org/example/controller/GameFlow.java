package org.example.controller;
import org.example.server.Server;
import org.example.model.Model;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/** The Gameflow is a controller of the status of the match, it depends on the Controller and allows the players only to Draw and Play
 * then calculates at the end of each turn the points of the player.
 */

public class GameFlow {
    List<Player> players;
    Model model;
    Server server;

    int counter=1;
    private AtomicInteger turn = new AtomicInteger(1);
    private AtomicInteger maxTurn=new AtomicInteger(0);

    public GameFlow(List<Player> players, Model model, Server server) {
        this.players=players;
        this.model= model;
        this.server=server;
        //Player Winnner = EndGame();
        //System.out.println("the winner is : " + Winnner);
    }



    /*
    public Player EndGame(){
        for (Player p : players) {
            model.getPlayerArea(p).privateObject();
            model.getPlayerArea(p).publicObjects(model);
            p.UpdateScoreboardPoints(model);
        }
        return model.getScoreBoard().Winner();
    }
     */


    public void incrementTurn() {
        int currentTurn = turn.get();
        int maxTurnValue = maxTurn.get();
        if (currentTurn == maxTurnValue) {
            turn.set(1);

        } else {
            turn.incrementAndGet();
        }
    }

    public boolean isYourTurn(String username, String command) {
        if (command.equals("setObjStarter")){
            return true;
        }

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(username)) {

                switch (i){
                    case 0:
                       if(Objects.equals(command, "play")) {
                           if (turn.get() == 1) {
                               return true;
                           }
                       }
                       else if(command.equals("draw")) {
                        if (turn.get() == 2) {
                            return true;
                            }
                        }
                       break;
                    case 1:
                        if(Objects.equals(command, "play")) {
                            if (turn.get() == 3) {
                                return true;
                            }
                        }
                        else if(command.equals("draw")) {
                            if (turn.get() == 4) {
                                return true;
                            }
                        }
                    case 2:
                        if(Objects.equals(command, "play")) {
                            if (turn.get() == 5) {
                                return true;
                            }
                        }
                        else if(command.equals("draw")){
                            if (turn.get() == 6) {
                                return true;
                            }
                        }
                    case 3:
                        if(Objects.equals(command, "play")) {
                            if (turn.get() == 7) {
                                return true;
                            }
                        }
                        else if(command.equals("draw")) {
                            if (turn.get() == 8) {
                                return true;
                            }
                        }
                }
            }
        }
      return false;
    }

    public AtomicInteger getTurn() {
        return turn;
    }

    public AtomicInteger getMaxTurn() {
        return maxTurn;
    }

    public void setTurn(AtomicInteger turn) {
        this.turn = turn;
    }

    public void setMaxTurn(AtomicInteger maxTurn) {
        this.maxTurn = maxTurn;
    }
}

