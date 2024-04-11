package org.example.model.deck;

import org.example.model.deck.enumeration.*;

/**
 * Class for handling card corners
 */
public class Corner {
    private Position position;
    private PropertiesCorner propertiesCorner;
    public Corner(Position position, PropertiesCorner propertiesCorner) {
        this.propertiesCorner = propertiesCorner;
        this.position = position;
    }

    public Position getPosition(){
        return this.position;
    }

    public PropertiesCorner getPropertiesCorner(){
        return this.propertiesCorner;
    }
}
