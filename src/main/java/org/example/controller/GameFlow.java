package org.example.controller;

import org.example.server.Server;
import org.example.model.Model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The GameFlow class is a controller for the status of the match.
 * It depends on the Controller and allows the players only to draw and play.
 * It calculates the points of the player at the end of each turn.
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

    public GameFlow(List<Player> players, Model model, Server server) {
        this.players = players;
        this.model = model;
        this.server = server;
    }

    public void incrementTurn() {
        int currentTurn = turn.get();
        int maxTurnValue = maxTurn.get();
        if (currentTurn == maxTurnValue) {
            turn.set(1);
        } else {
            turn.incrementAndGet();
        }
        System.out.println("Turn in GF increment: " + turn);

        // Check if all players have completed their turn in the last round
        if (lastRoundStarted && currentTurn == maxTurnValue) {
            try {
                endGame();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

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
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 1) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 2) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            }
                            break;
                        case 1:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 3) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 4) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            }
                            break;
                        case 2:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 5) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 6) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            }
                            break;
                        case 3:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 7) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 8) {
                                    System.out.println("Turn in GF: " + turn);
                                    return true;
                                }
                            }
                            break;
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


