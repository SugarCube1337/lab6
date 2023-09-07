package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

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
