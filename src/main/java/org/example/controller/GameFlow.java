package org.example.controller;
import org.example.server.Server;
import org.example.model.Model;
import java.util.List;

/** The Gameflow is a controller of the status of the match, it depends on the Controller and allows the players only to Draw and Play
 * then calculates at the end of each turn the points of the player.
 */

public class GameFlow {
    List<Player> players;
    Model model;
    Server server;

    public GameFlow(List<Player> players, Model model, Server server) {
        this.players=players;
        this.model= model;
        this.server=server;
        Rounds();
        Player Winnner = EndGame();
        //System.out.println("the winner is : " + Winnner);
    }

    public void Rounds () {
        boolean IsEnd = false;
        while (!IsEnd) {
            for (Player p : players) {
                //System.out.println("Player: " + p + " Play phase");
                p.Play(model, server);
                //System.out.println("Player: "+ p+ " Update Scoreboard Points");
                p.UpdateScoreboardPoints(model, server);
                //System.out.println("Player: " + p + " Draw phase");
                p.Draw(model, server);
            }
            IsEnd = model.Checkpoints();
        }
    }

    public Player EndGame(){
        for (Player p : players) {
            model.getPlayerArea(p).privateObject();
            model.getPlayerArea(p).publicObjects(model);
            p.UpdateScoreboardPoints(model);
        }
        return model.getScoreBoard().Winner();
    }

}
