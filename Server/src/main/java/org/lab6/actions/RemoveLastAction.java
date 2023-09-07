package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

import java.io.IOException;

/**
 * Command for removing last element
 */
public class RemoveLastAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        try {
            Main.getStorageManager().removeLast();
        } catch (NullPointerException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject(ex.getMessage()));
        }
        try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.REMOVE_LAST, new byte[]{1});
    }
}