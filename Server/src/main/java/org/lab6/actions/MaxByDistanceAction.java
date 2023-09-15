package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

/**
 * Executes the 'max_by_distance' task to find and output the route with the longest distance.
 */
public class MaxByDistanceAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Route result = null;
        for (Route organization : Main.getStorageManager().getAll()) {
            if (result == null || organization.getDistance() > result.getDistance())
                result = organization;
        }
        return new ServerCommand(ServerCommandType.GET_MAX_DISTANCE, Utils.serializeObject(result));
    }
}
