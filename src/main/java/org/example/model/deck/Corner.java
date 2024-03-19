package org.example.model.deck;

public class Corner {
    private Position position;
    private PropertiesCorner propertiesCorner;
    public Corner(Position position, PropertiesCorner propertiesCorner) {
        this.propertiesCorner = propertiesCorner;
        this.position = position;
    }
}
