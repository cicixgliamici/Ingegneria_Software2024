package org.example.view.gui.listener;

import org.example.view.gui.gamearea4.DrawingCardPanel;
import java.io.IOException;

public class DrawingCardPanelListener implements EvListener {

    private final DrawingCardPanel drawingCardPanel;

    public DrawingCardPanelListener(DrawingCardPanel drawingCardPanel) {
        this.drawingCardPanel = drawingCardPanel;
    }

    @Override
    public void eventListener(Event ev) throws IOException {
        if ("visibleArea".equals(ev.getEvent())) {
            System.out.println("visibleArea");
            drawingCardPanel.updateCards();
        }
    }
}
