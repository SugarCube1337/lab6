package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.collection.data.Coordinates;
import org.lab6.collection.data.Location;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

import java.io.IOException;

/**
 * Command for adding a new object to the collection
 */
public class AddAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Route parsed = null;
        try {
            parsed = (Route) Utils.deserializeObject(args);
        } catch (Exception ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Incorrect object data received"));
        }
        Route created = new Route();
        created.setName(parsed.getName());
        Coordinates createdCoords = new Coordinates(0, 0);
        createdCoords.setX(parsed.getCoordinates().getX());
        createdCoords.setY(parsed.getCoordinates().getY());
        created.setCoordinates(createdCoords);
        created.setDistance(parsed.getDistance());

        Location location = new Location(0, 0, "");
        location.setName(parsed.getLocation().getName());
        location.setX(parsed.getLocation().getX());
        location.setY(parsed.getLocation().getY());
        created.setLocation(location);

        Main.getStorageManager().add(created);
        try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.ADD, new byte[]{1});
    }
}
