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
    private int LastRound =0;
    private int EndGame=0;

    /**
     * Constructor for the GameFlow class.
     *
     * @param players The list of players participating in the game.
     * @param model The model instance to interact with.
     * @param server The server instance to interact with.
     */
    public GameFlow(List<Player> players, Model model, Server server) {
        this.players = players;
        this.model = model;
        this.server = server;
        //Player Winnner = EndGame();
        //System.out.println("the winner is : " + Winnner);
    }

    /**
     * Increments the turn counter. Resets to 1 if it reaches maxTurn.
     */
    public void incrementTurn() {
        int currentTurn = turn.get();
        int maxTurnValue = maxTurn.get();
        if (currentTurn == maxTurnValue) {
            turn.set(1);
        } else {
            turn.incrementAndGet();
        }
        System.out.println("Turn in GF increment: "+ turn);
    }

    /**
     * Checks if it is the player's turn based on the username and command.
     *
     * @param username The username of the player.
     * @param command The command issued by the player.
     * @return True if it is the player's turn to execute the command, false otherwise.
     */
    public boolean isYourTurn(String username, String command) throws RemoteException {
        if (command.equals("setObjStarter")) {
            System.out.println("Your turn is set to starter");
            return true;
        }
        if(LastRound !=1 || !players.get(0).getUsername().equals(username)) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getUsername().equals(username)) {
                    switch (i) {
                        case 0:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 1) {
                                    System.out.println("Turn in GF: "+ turn);
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 2) {
                                    System.out.println("Turn in GF: "+ turn);
                                    return true;
                                }
                            }
                            break;
                        case 1:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 3) {
                                    System.out.println("Turn in GF: "+ turn);
                                    if(LastRound==1 && players.get(players.size() - 1).getUsername().equals(username)) {
                                        endGame();
                                    }
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 4) {
                                    System.out.println("Turn in GF: "+ turn);
                                    return true;
                                }
                            }
                            break;
                        case 2:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 5) {
                                    System.out.println("Turn in GF: "+ turn);
                                    if(LastRound==1 && players.get(players.size() - 1).getUsername().equals(username)) {
                                        endGame();
                                    }
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 6) {
                                    System.out.println("Turn in GF: "+ turn);
                                    return true;
                                }
                            }
                            break;
                        case 3:
                            if (Objects.equals(command, "play")) {
                                if (turn.get() == 7) {
                                    System.out.println("Turn in GF: "+ turn);
                                    if(LastRound==1 && players.get(players.size() - 1).getUsername().equals(username)) {
                                        endGame();
                                    }
                                    return true;
                                }
                            } else if (command.equals("draw")) {
                                if (turn.get() == 8) {
                                    System.out.println("Turn in GF: "+ turn);
                                    return true;
                                }
                            }
                            break;
                    }
                }
            }
        }
        if(LastRound ==1){
            System.out.println("Game Over");
            EndGame=1;

        }
        else {
            System.out.println("Not your turn");
        }
        return false;
    }

    public void endGame() throws RemoteException {
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

        // Aggiornamento della scoreboard nella view con i punteggi di tutti i giocatori (questo dipenderà dall'implementazione della tua view)
        // updateScoreboard();

        if (winners.size() == 1) {
            // Caso di un solo vincitore
            server.onModelGeneric("Winner:"+ "the winner is " + winners.get(0) + " with " + highestScore + " points");
        } else {
            // Caso di pareggio
            StringBuilder tiedPlayers = new StringBuilder();
            for (int i = 0; i < winners.size(); i++) {
                tiedPlayers.append(winners.get(i));
                if (i < winners.size() - 1) {
                    tiedPlayers.append(", ");
                }
            }
            server.onModelGeneric("Tie:" +"pareggio, i giocatori " + tiedPlayers.toString() + " hanno realizzato " + highestScore + " punti");
        }
    }

    /**
     * Gets the current turn counter.
     *
     * @return The current turn as an AtomicInteger.
     */
    public AtomicInteger getTurn() {
        return turn;
    }

    /**
     * Gets the maximum turn value.
     *
     * @return The maximum turn value as an AtomicInteger.
     */
    public AtomicInteger getMaxTurn() {
        return maxTurn;
    }

    /**
     * Sets the current turn counter.
     *
     * @param turn The turn counter to set.
     */
    public void setTurn(AtomicInteger turn) {
        this.turn = turn;
    }

    /**
     * Sets the maximum turn value.
     *
     * @param maxTurn The maximum turn value to set.
     */
    public void setMaxTurn(AtomicInteger maxTurn) {
        this.maxTurn = maxTurn;
    }

    public void setLastRound(int lastRound) {
        LastRound = lastRound;
    }

    public int getEndGame() {
        return EndGame;
    }
}
