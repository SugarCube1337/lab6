package org.lab6.actions;

import java.io.IOException;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

/**
 * Command for reordering collection
 */
public class ReorderAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        Main.getStorageManager().reverse();
        try {
            Main.getStorageManager().save(Main.getStorageManager().getFilename());
        } catch (IOException ex) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Failed to save file"));
        }
        return new ServerCommand(ServerCommandType.REORDER, null);
    }
}