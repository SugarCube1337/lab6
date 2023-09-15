package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

import java.io.IOException;

public class ServerSaveCommand implements Action {
    public ServerCommand execute(byte[] args) {try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.SAVE, null);
    }
}
