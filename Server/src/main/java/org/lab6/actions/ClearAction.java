package org.lab6.actions;

import java.io.IOException;

import org.lab6.Utils;
import org.lab6.udp.ServerCommand;
import org.lab6.Main;
import org.lab6.udp.ServerCommandType;

/**
 * Command for clearing the whole collection
 */
public class ClearAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Main.getStorageManager().clear();
        try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.CLEAR, null);
    }
}
