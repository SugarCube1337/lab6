package org.lab6;


import org.lab6.exception.AbortException;
import org.lab6.parser.BaseCommandParser;
import org.lab6.parser.CommandParser;
import org.lab6.parser.ConsoleInputManager;


/**
 * Main class for lab 6
 * test
 */


public class Main {

    private static final String DEFAULT_PATH = "data.json";
    private static String path = DEFAULT_PATH;

    /**
     * Entry point for the project
     *
     * @param args contains information about collection's data
     */
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
}



