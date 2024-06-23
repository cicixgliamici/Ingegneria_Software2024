package org.example.controller;

import org.example.server.Server;
import org.example.model.Model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controls the flow of the game, managing turns and game ending conditions.
 */
public class GameFlow {
    List<Player> players;
    Model model;
    Server server;

    private AtomicInteger turn = new AtomicInteger(1);
    private AtomicInteger maxTurn = new AtomicInteger(0);
    private int LastRound = 0;
    private int EndGame = 0;
    private boolean lastRoundStarted = false;
    private boolean lastRoundAnnounced = false;

    /**
     * Constructor to initialize the game flow controller.
     *
     * @param players the list of players in the game
     * @param model   the game model
     * @param server  the game server
     */
    public GameFlow(List<Player> players, Model model, Server server) {
        this.players = players;
        this.model = model;
        this.server = server;
    }

    /**
     * Increments the turn count and manages the flow between rounds.
     */
    public void incrementTurn() {
        int currentTurn = turn.get();
        int maxTurnValue = maxTurn.get();
        if (currentTurn == maxTurnValue) {
            turn.set(1);
        } else {
            turn.incrementAndGet();
        }
        System.out.println("Turn in GF increment: " + turn);

        if (lastRoundStarted && currentTurn == maxTurnValue) {
            try {
                endGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines if it's a player's turn to perform a specific command.
     *
     * @param username the username of the player
     * @param command  the command to be checked
     * @return true if it's the player's turn, false otherwise
     * @throws RemoteException if an RMI error occurs
     */
    public boolean isYourTurn(String username, String command) throws RemoteException {
        if (command.equals("setObjStarter")) {
            System.out.println("Your turn is set to starter");
            return true;
        }
        if (LastRound != 1 || !players.get(0).getUsername().equals(username)) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(username)) {
                    return checkTurn(command, i);
                }
            }
        }
        if (LastRound == 1) {
            System.out.println("Game Over");
            EndGame = 1;
        } else {
            System.out.println("Not your turn");
        }
        return false;
    }

    /**
     * Ends the game by determining the winner based on points.
     *
     * @throws RemoteException if an RMI error occurs
     */
    public void endGame() throws RemoteException {
        System.out.println("entrato in end Game");
        int highestScore = Integer.MIN_VALUE;
        List<String> winners = new ArrayList<>();

        for (Player player : players) {
            model.getPlayerCardArea(player).publicObjective(model);
            model.getPlayerCardArea(player).privateObjective();

            int playerScore = model.getPlayerCardArea(player).getCounter().getPointCounter();
            if (playerScore > highestScore) {
                highestScore = playerScore;
                winners.clear();
                winners.add(player.getUsername());
            } else if (playerScore == highestScore) {
                winners.add(player.getUsername());
            }
        }

        if (winners.size() == 1) {
            server.onModelGeneric("Winner:" + "the winner is " + winners.get(0) + " with " + highestScore + " points");
        } else {
            StringBuilder tiedPlayers = new StringBuilder();
            for (int i = 0; i < winners.size(); i++) {
                tiedPlayers.append(winners.get(i));
                if (i < winners.size() - 1) {
                    tiedPlayers.append(", ");
                }
            }
            server.onModelGeneric("Tie:" + "pareggio, i giocatori " + tiedPlayers.toString() + " hanno realizzato " + highestScore + " punti");
        }
    }

    // Helper method to check if it's the right turn for a command
    private boolean checkTurn(String command, int playerIndex) {
        int turnIndex = 1 + playerIndex * 2;
        if (Objects.equals(command, "play") && turn.get() == turnIndex) {
            System.out.println("Turn in GF: " + turn);
            return true;
        } else if (command.equals("draw") && turn.get() == turnIndex + 1) {
            System.out.println("Turn in GF: " + turn);
            return true;
        }
        return false;
    }

    // Getters and setters
    public AtomicInteger getTurn() {
        return turn;
    }

    public void setTurn(AtomicInteger turn) {
        this.turn = turn;
    }

    public AtomicInteger getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(AtomicInteger maxTurn) {
        this.maxTurn = maxTurn;
    }

    public int getEndGame() {
        return EndGame;
    }

    public int getLastRound() {
        return LastRound;
    }

    public void setLastRound(int lastRound) {
        LastRound = lastRound;
    }

    public boolean isLastRoundAnnounced() {
        return lastRoundAnnounced;
    }

    public void setLastRoundAnnounced(boolean announced) {
        lastRoundAnnounced = announced;
    }

    public void startLastRound() {
        lastRoundStarted = true;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
