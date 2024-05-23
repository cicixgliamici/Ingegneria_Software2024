package org.example.view.gui.listener;

import java.util.EventListener;

/**
 * Interface for handling custom events within the application.
 * Classes implementing this interface can react to specific events signaled by other parts of the application.
 */
public interface EvListener extends EventListener {
    void eventListener(Event ev);
}
