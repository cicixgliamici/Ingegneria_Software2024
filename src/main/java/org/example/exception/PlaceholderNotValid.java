package org.example.exception;


public class PlaceholderNotValid extends Exception {
    private final int id;
    private final int x;
    private final int y;


    public PlaceholderNotValid(String message,int id,  int x, int y) {
        super(message);
        this.id=id;
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }
}
