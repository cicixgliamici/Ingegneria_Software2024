package org.example.view.gui.listener;

import java.util.EventObject;

/**
 * Custom event class that extends EventObject.
 * It provides additional properties to handle application-specific events with optional data.
 */
public class Event extends EventObject {
    private String event; // Describes the type or name of the event
    private String data; // Optional data or details associated with the event

    /**
     * Constructor to create an event without additional data.
     * @param source The object on which the event initially occurred.
     * @param event The event type or name.
     */
    public Event(Object source, String event) {
        super(source);
        this.event = event;
    }

    /**
     * Constructor to create an event with additional data.
     * @param source The object on which the event initially occurred.
     * @param event The event type or name.
     * @param data Additional data or details associated with the event.
     */
    public Event(Object source, String event, String data){
        super(source);
        this.event = event;
        this.data = data;
    }

    /**
     * Returns the type or name of the event.
     */
    public String getEvent() {
        return event;
    }

    /**
     * Returns the data associated with the event.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data associated with the event.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Sets the type or name of the event.
     */
    public void setEvent(String event) {
        this.event = event;
    }
}
