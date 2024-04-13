package org.example.model.PlayArea;

public class PlaceHolder {
    int x;
    int y;
    public PlaceHolder(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Placeholder " + x + " : " + y;
    }

}
