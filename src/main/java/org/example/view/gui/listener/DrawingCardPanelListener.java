package org.example.view.gui.listener;

import org.example.view.gui.gamearea4.DrawingCardPanel;
import org.example.view.gui.gamearea4.GameAreaPanel;

import java.io.IOException;

public class DrawingCardPanelListener implements EvListener {

    private final DrawingCardPanel drawingCardPanel;
    private final GameAreaPanel gameAreaPanel; // Add reference to GameAreaPanel

    public DrawingCardPanelListener(DrawingCardPanel drawingCardPanel, GameAreaPanel gameAreaPanel) {
        this.drawingCardPanel = drawingCardPanel;
        this.gameAreaPanel = gameAreaPanel; // Initialize GameAreaPanel
    }

    @Override
    public void eventListener(Event ev) throws IOException {
        if ("visibleArea".equals(ev.getEvent())) {
            drawingCardPanel.updateCards();
            gameAreaPanel.updateHandDisplay(); // Update GameAreaPanel
        } else if ("handUpdated".equals(ev.getEvent())) {
            gameAreaPanel.updateHandDisplay(); // Update GameAreaPanel
        }
    }
}
