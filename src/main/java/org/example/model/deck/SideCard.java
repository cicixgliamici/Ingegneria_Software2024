package org.example.model.deck;

public class SideCard {
    private Side side;
    private Corner[] corners;
    public SideCard(Side side, PropertiesCorner prop1, PropertiesCorner prop2, PropertiesCorner prop3, PropertiesCorner prop4) {
        this.side= side;
        corners = new Corner[4];
        corners[0] = new Corner(Position.TOPL, prop1);
        corners[1] = new Corner(Position.TOPR, prop2);
        corners[2] = new Corner(Position.BOTTOMR, prop3);
        corners[3] = new Corner(Position.BOTTOML, prop4);
    }

    public Side getSide() {
        return side;
    }

    public Corner[] getCorners() {
        return corners;
    }
}
