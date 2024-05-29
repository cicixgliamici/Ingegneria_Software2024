package org.example.exception;


public class PlaceholderNotValid extends Exception {
    private final int x;
    private final int y;

    public PlaceholderNotValid(String message, int x, int y) {
        super(message);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
