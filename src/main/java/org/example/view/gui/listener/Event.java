package org.example.view.gui.listener;

import org.example.client.TCPClient;
import org.example.view.View;
import org.example.view.gui.utilities.CornerButton;

import java.util.EventObject;

/**
 * Custom event class that extends EventObject.
 * It provides additional properties to handle application-specific events with optional data.
 */
public class Event extends EventObject {
    private String event; // Describes the type or name of the event
    private String data; // Optional data or details associated with the event
    private TCPClient tcpClient;
    private CornerButton cornerButton;
    private View view;
    private int id;
    private int side;
    private int x;
    private int y;
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

    public Event(Object source, String event, TCPClient tcpClient, String username, View view){
        super(source);
        this.event = event;
        this.tcpClient = tcpClient;
        this.data = username;
        this.view = view;
    }
    public Event(Object source, String event, CornerButton cornerButton){
        super(source);
        this.event = event;
        this.cornerButton = cornerButton;
    }
    public Event(Object source, String event, int id) {
        super(source);
        this.event = event;
        this.id = id;
    }
    public Event(Object source, String event, int id, int side, int x, int y) {
        super(source);
        this.event = event;
        this.id = id;
        this.side = side;
        this.x = x;
        this.y = y;
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

    public TCPClient getTcpClient() {
        return tcpClient;
    }

    public void setTcpClient(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    public View getView() {
        return view;
    }

    public int getId() {return id;}

    public int getSide() {
        return side;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setView(View view) {
        this.view = view;
    }

    public CornerButton getCornerButton() {
        return cornerButton;
    }

    public void setCornerButton(CornerButton cornerButton) {
        this.cornerButton = cornerButton;
    }

}