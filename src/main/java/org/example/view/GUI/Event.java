package org.example.view.GUI;

import java.util.EventObject;

public class Event extends EventObject {
    private String event;
    public Event(Object source, String event) {
        super(source);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
