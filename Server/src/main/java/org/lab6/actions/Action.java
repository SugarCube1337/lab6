package org.lab6.actions;

import org.lab6.udp.ServerCommand;

public interface Action {
    /**
     * Method called when a command executed
     * @param args Given arguments
     * @return Result
     */
    public ServerCommand execute(byte[] args);
}