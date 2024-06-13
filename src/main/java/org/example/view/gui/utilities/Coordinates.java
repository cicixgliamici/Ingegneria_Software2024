package org.example.view.gui.utilities;

import java.util.Objects;

/**
 * The Coordinates class represents a point in a two-dimensional space with x and y coordinates.
 */
public class Coordinates {
    private int x; // The x-coordinate
    private int y; // The y-coordinate

    /**
     * Constructs a Coordinates object with specified x and y values.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Compares this Coordinates object to another object for equality.
     *
     * @param o The object to compare to.
     * @return true if the specified object is equal to this Coordinates object; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates coordinates = (Coordinates) o;
        return x == coordinates.x && y == coordinates.y;
    }

    /**
     * Returns a hash code value for this Coordinates object.
     *
     * @return A hash code value for this Coordinates object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of this Coordinates object.
     *
     * @return A string representation of this Coordinates object.
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
