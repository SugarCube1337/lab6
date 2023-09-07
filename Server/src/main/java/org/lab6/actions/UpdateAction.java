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
 * Command for updating element of the collection by id, based on AddCommand
 */
public class UpdateAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Route parsed = (Route) Utils.deserializeObject(args);
        Route existed;
        try {
            existed = Main.getStorageManager().get(parsed.getId());
            if (existed == null)
                throw new IllegalArgumentException();
        } catch (Exception ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Object with the specified id not found!"));
        }
        existed.setName(parsed.getName());
        Coordinates createdCoords = new Coordinates(0, 0);
        createdCoords.setX(parsed.getCoordinates().getX());
        createdCoords.setX(parsed.getCoordinates().getY());
        existed.setCoordinates(createdCoords);
        existed.setDistance(parsed.getDistance());


        Location location = new Location(0, 0, "");
        location.setName(parsed.getLocation().getName());
        location.setX(parsed.getLocation().getX());
        location.setY(parsed.getLocation().getY());
        existed.setLocation(location);

        Main.getStorageManager().update(existed);
        try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.UPDATE, new byte[]{1});
    }
}