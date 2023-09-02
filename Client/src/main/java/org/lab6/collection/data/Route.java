package org.lab6.collection.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.lab6.exception.IllegalFieldException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Represents a route with a unique ID, name, coordinates, creation date, start and end locations, and distance.
 */
public final class Route implements Comparable<Route>, Serializable {
    private static final long serialVersionUID = 6529685098267757690L-3L;

    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location.From from; //Поле не может быть null
    private Location.To to; //Поле не может быть null
    private Float distance; //Значение поля должно быть больше 1

    public static final long MIN_ID = 0;
    public static final int MIN_DISTANCE = 1;

    public Route() {
        id = -1;
        creationDate = LocalDateTime.now();
    }

    private Route(long id, String name, Coordinates coordinates, LocalDateTime creationDate, Float distance, Location.From from, Location.To to) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.distance = distance;
        this.to = to;
        this.from = from;
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

    public void setDistance(Scanner scan) {
        float routeDistance;
        try {
            routeDistance = scan.nextFloat();
        } catch(Exception ex) {
            scan.nextLine();
            throw new IllegalArgumentException("The value of the route distance must be a number");
        }
        if(routeDistance == 0.0f)
            throw new IllegalArgumentException("The route distance value cannot be empty");
        scan.nextLine();
        this.distance = routeDistance;
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

    public Float getDistance() {
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

    public static Route parseJSON(String json) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        long id = jsonObject.get("id").getAsLong();
        String name = jsonObject.get("name").getAsString();
        float x = jsonObject.get("coordinates").getAsJsonObject().get("x").getAsFloat();
        int y = jsonObject.get("coordinates").getAsJsonObject().get("y").getAsInt();
        float distance = jsonObject.get("distance").getAsFloat();

        JsonObject locationObject = jsonObject.get("location").getAsJsonObject();
        float xL = locationObject.get("x").getAsFloat();
        float yL = locationObject.get("y").getAsFloat();
        String nameL = locationObject.get("name").getAsString();

        Coordinates coordinates = new Coordinates(x, y);
        Location location = new Location(xL, yL, nameL);
        LocalDate creationDate = LocalDate.now();

        return new Route(id, name, coordinates, creationDate, distance, location);
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
}