package org.lab6.collection.data;

import org.lab6.exception.IllegalFieldException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * A class representing a pair of coordinates (x, y).
 */
public class Coordinates implements Serializable {

    @Serial
    private static final long serialVersionUID = -5296510993918124324L;

    private Double x; //Поле не может быть null
    private int y;

    public Coordinates() {}

    public Coordinates(Double x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void setX(Double x) {
        if (x == null) {
            throw new IllegalFieldException("x", this);
        }
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordinates that)) return false;
        return getY() == that.getY() && getX().equals(that.getX());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
