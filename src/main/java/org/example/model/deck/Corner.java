package org.example.model.deck;

import org.example.enumeration.Position;
import org.example.enumeration.PropertiesCorner;

/**
 * Class for handling card corners.
 */
public class Corner {
    private Position position;
    private PropertiesCorner propertiesCorner;

    /**
     * Constructs a Corner with the specified position and properties.
     *
     * @param position the position of the corner
     * @param propertiesCorner the properties of the corner
     */
    public Corner(Position position, PropertiesCorner propertiesCorner) {
        this.propertiesCorner = propertiesCorner;
        this.position = position;
    }

    /**
     * Getter for the position of the corner.
     *
     * @return the position of the corner
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Getter for the properties of the corner.
     *
     * @return the properties of the corner
     */
    public PropertiesCorner getPropertiesCorner() {
        return this.propertiesCorner;
    }

    @Override
    public String toString() {
        return "Position: " + position + ", Properties: " + propertiesCorner;
    }
}
