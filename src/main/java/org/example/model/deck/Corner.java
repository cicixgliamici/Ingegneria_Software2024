package org.example.model.deck;

import org.example.model.deck.enumeration.*;

public class Corner {
    private Position position;
    private PropertiesCorner propertiesCorner;
    public Corner(Position position, PropertiesCorner propertiesCorner) {
        this.propertiesCorner = propertiesCorner;
        this.position = position;
    }
}
