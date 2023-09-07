package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

public class GetAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Route existed;
        try {
            existed = Main.getStorageManager().get(Utils.fromByteArray(args));
            if (existed == null)
                throw new IllegalArgumentException();
        } catch (Exception ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Object with the specified id was not found!"));
        }
        return new ServerCommand(ServerCommandType.GET, Utils.serializeObject(existed));
    }
}
