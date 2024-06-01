package org.example.view.gui.listener;

import org.example.view.gui.gamearea4.GameAreaPanel;
import java.io.IOException;

public class GameAreaPanelListener implements EvListener {

    private final GameAreaPanel gameAreaPanel;

    public GameAreaPanelListener(GameAreaPanel gameAreaPanel) {
        this.gameAreaPanel = gameAreaPanel;
    }

    @Override
    public void eventListener(Event ev) throws IOException {
        System.out.println("Prima dell'if "+ ev.getEvent());
        if ("handUpdated".equals(ev.getEvent())) {
            System.out.println("Dentro handUpdated");
            gameAreaPanel.updateHandDisplay();
        } else if ("playUpdated".equals(ev.getEvent())) {
            System.out.println("Dentro playUpdated");
            gameAreaPanel.handlePlayUpdate();
        }
    }
}
