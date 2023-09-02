package org.lab6;


import org.lab6.exception.AbortException;
import org.lab6.parser.BaseCommandParser;
import org.lab6.parser.CommandParser;
import org.lab6.parser.ConsoleInputManager;


import java.io.IOException;

public class Main {
    private static final String DEFAULT_PATH = "data.json";
    private static String path = DEFAULT_PATH;
    private static CommandManager commandManager;
    private static ConnectionManager connectionManager;

    public static void main(String[] args) {
        if (args.length > 0) {
            path = args[0];
        } else {
            System.err.println("Path to data is not provided, using default: " + DEFAULT_PATH);
        }

        CollectionLoader.loadCollection(path);

        final CommandParser parser = new BaseCommandParser();

        try (ConsoleInputManager inputManager = new ConsoleInputManager(parser)) {
            inputManager.start();
        } catch (AbortException ex) {
            System.out.println("Operation aborted, stopping the program");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("An unexpected error occurred, stopping the program");
        }

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
     * Returns path to file where data is located
     *
     * @return path to file with data
     */
    public static String getPath() {
        return path;
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


