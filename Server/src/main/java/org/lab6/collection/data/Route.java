package org.lab6.collection.data;

import org.lab6.collection.CollectionManager;
import org.lab6.exception.IllegalFieldException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a route with a unique ID, name, coordinates, creation date, start and end locations, and distance.
 */
public final class Route implements Comparable<Route>, Serializable {
    private static final long serialVersionUID = 6701593067860758465L;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location.From from; //Поле не может быть null
    private Location.To to; //Поле не может быть null
    private int distance; //Значение поля должно быть больше 1

    public static final long MIN_ID = 0;
    public static final int MIN_DISTANCE = 1;

    public Route() {}

    public Route(String name,
                 Coordinates coordinates,
                 Location.From from,
                 Location.To to,
                 int distance) {
        setId(CollectionManager.genId());
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(LocalDateTime.now());
        setFrom(from);
        setTo(to);
        setDistance(distance);
    }

    public void setId(long id) {
        if (id < MIN_ID) {
            throw new IllegalFieldException("id", this);
        }
        this.id = id;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalFieldException("name", this);
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalFieldException("coordinates", this);
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalFieldException("creationDate", this);
        }
        this.creationDate = creationDate;
    }

    public void setFrom(Location.From from) {
        if (from == null) {
            throw new IllegalFieldException("from", this);
        }
        this.from = from;
    }

    public void setTo(Location.To to) {
        if (to == null) {
            throw new IllegalFieldException("to", this);
        }
        this.to = to;
    }

    public void setDistance(int distance) {
        if (distance < MIN_DISTANCE) {
            throw new IllegalFieldException("distance", this);
        }
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Location.From getFrom() {
        return from;
    }

    public Location.To getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Route[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate.format(DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy")) +
                ", from=" + from +
                ", to=" + to +
                ", distance=" + distance +
                ']';
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Route route)) return false;
        return id == route.id
                && distance == route.distance
                && name.equals(route.name)
                && coordinates.equals(route.coordinates)
                && creationDate.equals(route.creationDate)
                && from.equals(route.from)
                && to.equals(route.to);
    }

    @Override
    public int compareTo(Route route) {
        return this.getDistance() - route.getDistance();
    }
}