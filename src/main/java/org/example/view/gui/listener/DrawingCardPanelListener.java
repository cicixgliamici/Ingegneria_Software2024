package org.example.view.gui.listener;

import org.example.view.gui.gamearea4.DrawingCardPanel;
import java.io.IOException;

/**
 * The DrawingCardPanelListener class listens for events related to the DrawingCardPanel
 * and updates the panel accordingly.
 */
public class DrawingCardPanelListener implements EvListener {

    private final DrawingCardPanel drawingCardPanel;

    /**
     * Constructs a DrawingCardPanelListener for the specified DrawingCardPanel.
     *
     * @param drawingCardPanel The DrawingCardPanel to be updated on events.
     */
    public DrawingCardPanelListener(DrawingCardPanel drawingCardPanel) {
        this.drawingCardPanel = drawingCardPanel;
    }

    /**
     * Handles the received event and updates the DrawingCardPanel.
     *
     * @param ev The event received.
     * @throws IOException If an I/O error occurs during event handling.
     */
    @Override
    public void eventListener(Event ev) throws IOException {
        if ("visibleArea".equals(ev.getEvent())) {
            System.out.println("Evento visibleArea preso");
            // Update the DrawingCardPanel with new cards
            drawingCardPanel.updateCards();
        }
    }
}
