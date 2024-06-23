package org.example.controller;

import org.example.server.Server;
import org.example.model.Model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The GameFlow class manages the sequence and logic of player turns in a game.
 * It relies on interaction with a model and a server to handle game state and network communication respectively.
 */
public class GameFlow {
    List<Player> players; // List of players participating in the game.
    Model model; // The game's model, handling the state and logic.
    Server server; // The server interface for sending updates to clients.

    private AtomicInteger turn = new AtomicInteger(1); // Current turn number, using thread-safe atomic operations.
    private AtomicInteger maxTurn = new AtomicInteger(0); // Maximum number of turns in the game.
    private int LastRound = 0; // Indicator for the last round of the game.
    private int EndGame = 0; // Indicator that the game has ended.
    private boolean lastRoundStarted = false; // Flag to check if the last round has started.
    private boolean lastRoundAnnounced = false; // Flag to check if the last round has been announced.

    /**
     * Constructor initializing the GameFlow with given players, model, and server.
     *
     * @param players the list of players in the game
     * @param model the game model
     * @param server the server for communication
     */
    public GameFlow(List<Player> players, Model model, Server server) {
        this.players = players;
        this.model = model;
        this.server = server;
    }

    /**
     * Increments the turn counter and handles the transition from the last turn to the first.
     * If it's the last turn and the last round has started, the game end sequence is triggered.
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

        // End game if it's the last turn of the last round
        if (lastRoundStarted && currentTurn == maxTurnValue) {
            try {
                endGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Determines if it is the specified player's turn to execute a command.
     * Commands can be either 'play' or 'draw', and are only valid at certain turns for each player.
     *
     * @param username the username of the player
     * @param command the command the player is attempting to execute
     * @return true if it is the player's turn and the command is valid, false otherwise
     * @throws RemoteException if network issues prevent command execution
     */
    public boolean isYourTurn(String username, String command) throws RemoteException {
        if (command.equals("setObjStarter")) {
            System.out.println("Your turn is set to starter");
            return true;
        }
        if (LastRound != 1 || !players.get(0).getUsername().equals(username)) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(username)) {
                    switch (i) {
                        case 0:
                            // Player 1's turn logic
                            break;
                        // Similar logic for other players
                    }
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
     * Ends the game by calculating scores, determining winners or ties, and notifying players.
     * This method is called when the game reaches its conclusion.
     * @throws RemoteException if network issues occur during execution
     */
    public void endGame() throws RemoteException {
        System.out.println("Entered in end Game");
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
            server.onModelGeneric("Tie:" + "tie, players " + tiedPlayers.toString() + " have scored " + highestScore + " points");
        }
    }

    // Getter and setter methods follow, providing access to private variables.

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

    public void setLastRound(int lastRound) {
        LastRound = lastRound;
    }

    public int getEndGame() {
        return EndGame;
    }

    public int getLastRound() {
        return LastRound;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void startLastRound() {
        lastRoundStarted = true;
    }

    public boolean isLastRoundAnnounced() {
        return lastRoundAnnounced;
    }

    public void setLastRoundAnnounced(boolean announced) {
        lastRoundAnnounced = announced;
    }
}


