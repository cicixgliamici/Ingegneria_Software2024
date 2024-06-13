package org.example.view.gui.listener;

import org.example.view.gui.gamearea4.GameAreaPanel;

import javax.swing.*;
import java.io.IOException;

/**
 * Listener for events related to the GameAreaPanel.
 * This class handles specific events and updates the GameAreaPanel accordingly.
 */
public class GameAreaPanelListener implements EvListener {

    private final GameAreaPanel gameAreaPanel;

    /**
     * Constructor for GameAreaPanelListener.
     *
     * @param gameAreaPanel The GameAreaPanel that this listener will handle events for.
     */
    public GameAreaPanelListener(GameAreaPanel gameAreaPanel) {
        this.gameAreaPanel = gameAreaPanel;
    }

    /**
     * Handles the event triggered by interacting with the GameAreaPanel.
     * This method processes events and updates the GameAreaPanel accordingly.
     *
     * @param ev The event triggered.
     * @throws IOException If an I/O error occurs during event handling.
     */
    @Override
    public void eventListener(Event ev) throws IOException {
        System.out.println("Prima dell'if " + ev.getEvent());

        // Handle "handUpdated" event
        if ("handUpdated".equals(ev.getEvent())) {
            System.out.println("Dentro handUpdated");
            gameAreaPanel.updateHandDisplay();
        }
        // Handle "playUpdated" event
        else if ("playUpdated".equals(ev.getEvent())) {
            System.out.println("Dentro playUpdated con id: " + ev.getId());
            gameAreaPanel.handlePlayUpdate(ev.getId(), ev.getSide(), ev.getX(), ev.getY());
        }
    }
}
