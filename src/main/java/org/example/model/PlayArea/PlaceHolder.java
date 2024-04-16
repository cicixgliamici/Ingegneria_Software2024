package org.example.model.PlayArea;

public class PlaceHolder {
    public int x;
    public int y;
    public PlaceHolder(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "Placeholder " + x + " : " + y;
    }

    public void setTopL(PlaceHolder topL){}

    public void setTopR(PlaceHolder topR) {
    }

    public void setBotL(PlaceHolder botL) {}

    public void setBotR(PlaceHolder botR) {
    }
}
