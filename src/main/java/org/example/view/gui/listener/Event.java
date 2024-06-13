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
     *
     * @param source The object on which the event initially occurred.
     * @param event  The event type or name.
     */
    public Event(Object source, String event) {
        super(source);
        this.event = event;
    }

    /**
     * Constructor to create an event with additional data.
     *
     * @param source The object on which the event initially occurred.
     * @param event  The event type or name.
     * @param data   Additional data or details associated with the event.
     */
    public Event(Object source, String event, String data) {
        super(source);
        this.event = event;
        this.data = data;
    }

    /**
     * Constructor to create an event with TCPClient, username, and View.
     *
     * @param source    The object on which the event initially occurred.
     * @param event     The event type or name.
     * @param tcpClient The TCP client associated with the event.
     * @param username  The username associated with the event.
     * @param view      The view associated with the event.
     */
    public Event(Object source, String event, TCPClient tcpClient, String username, View view) {
        super(source);
        this.event = event;
        this.tcpClient = tcpClient;
        this.data = username;
        this.view = view;
    }

    /**
     * Constructor to create an event with a CornerButton.
     *
     * @param source       The object on which the event initially occurred.
     * @param event        The event type or name.
     * @param cornerButton The corner button associated with the event.
     */
    public Event(Object source, String event, CornerButton cornerButton) {
        super(source);
        this.event = event;
        this.cornerButton = cornerButton;
    }

    /**
     * Constructor to create an event with an ID.
     *
     * @param source The object on which the event initially occurred.
     * @param event  The event type or name.
     * @param id     The ID associated with the event.
     */
    public Event(Object source, String event, int id) {
        super(source);
        this.event = event;
        this.id = id;
    }

    /**
     * Constructor to create an event with ID, side, X, and Y coordinates.
     *
     * @param source The object on which the event initially occurred.
     * @param event  The event type or name.
     * @param id     The ID associated with the event.
     * @param side   The side associated with the event.
     * @param x      The X coordinate associated with the event.
     * @param y      The Y coordinate associated with the event.
     */
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
     *
     * @return The event type or name.
     */
    public String getEvent() {
        return event;
    }

    /**
     * Returns the data associated with the event.
     *
     * @return The data associated with the event.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the data associated with the event.
     *
     * @param data The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Sets the type or name of the event.
     *
     * @param event The event type or name to set.
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * Returns the TCP client associated with the event.
     *
     * @return The TCP client.
     */
    public TCPClient getTcpClient() {
        return tcpClient;
    }

    /**
     * Sets the TCP client associated with the event.
     *
     * @param tcpClient The TCP client to set.
     */
    public void setTcpClient(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    /**
     * Returns the view associated with the event.
     *
     * @return The view.
     */
    public View getView() {
        return view;
    }

    /**
     * Returns the ID associated with the event.
     *
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the side associated with the event.
     *
     * @return The side.
     */
    public int getSide() {
        return side;
    }

    /**
     * Returns the X coordinate associated with the event.
     *
     * @return The X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate associated with the event.
     *
     * @return The Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the view associated with the event.
     *
     * @param view The view to set.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Returns the corner button associated with the event.
     *
     * @return The corner button.
     */
    public CornerButton getCornerButton() {
        return cornerButton;
    }

    /**
     * Sets the corner button associated with the event.
     *
     * @param cornerButton The corner button to set.
     */
    public void setCornerButton(CornerButton cornerButton) {
        this.cornerButton = cornerButton;
    }
}
