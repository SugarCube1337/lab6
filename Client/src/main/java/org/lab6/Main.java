package org.lab6;


import java.io.IOException;

public class Main {

    private static CommandManager commandManager;
    private static ConnectionManager connectionManager;

    public static void main(String[] args) {


        System.out.println("Route management");

        while (true) {
            try {
                connectionManager = new ConnectionManager();
            } catch (IOException e) {
                System.out.println("client destroyed himself.");
            }
            commandManager = new CommandManager();
            try {
                commandManager.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Restarting the app...");
            }
        }
    }




    /**
     * Get command manager
     *
     * @return Command manager
     */
    public static CommandManager getCommandManager() {
        return commandManager;
    }

    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}


