package org.example.view.GUI.listener;

import java.util.EventObject;

public class Event extends EventObject {
    private String event;
    private String data;
    public Event(Object source, String event) {
        super(source);
        this.event = event;
    }
    public Event(Object source, String event, String data){
        super(source);
        this.event = event;
        this.data = data;
    }

    public String getEvent() {
        return event;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
