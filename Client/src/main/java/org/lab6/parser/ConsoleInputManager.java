package org.lab6.parser;

import org.lab6.exception.AbortException;

import java.util.Scanner;

/**
 * Manages input from console
 */
public class ConsoleInputManager implements InputManager, AutoCloseable {
    private boolean isRunning = false;
    private final Scanner scanner;
    private final CommandParser parser;

    /**
     * Creates new instance of <i>ConsoleInputManager</i>
     * @param parser parser to use
     */
    public ConsoleInputManager(CommandParser parser) {
        scanner = new Scanner(System.in);
        this.parser = parser;
    }

    /**
     * Starts manager
     */
    @Override
    public void start() {
        if (isRunning) {
            throw new IllegalStateException("Trying to start active instance of manager");
        }
        isRunning = true;

        System.out.println("Ready to work");
        System.out.println("Type help to get more info");
        while (this.hasNext() && isRunning) {
            var input = scanner.nextLine();
            if (!input.isBlank()) {
                parser.parse(input, this);
            }
        }
    }

    /**
     * Stops manager
     */
    @Override
    public void stop() {
        if (!isRunning) {
            throw new IllegalStateException("Trying to stop inactive instance of manager");
        }
        isRunning = false;
        scanner.close();
    }

    /**
     * Checks if manager has data to input
     * @return true if manager has data to input and can continue
     */
    @Override
    public boolean hasNext() {
        return isRunning && scanner.hasNextLine();
    }

    /**
     * Requests data
     * @return requested data
     */
    @Override
    public String request() {
        if (!hasNext()) {
            throw new AbortException();
        }
        return scanner.nextLine();
    }

    /**
     * Method for auto close
     */
    @Override
    public void close() {
        if (isRunning) {
            this.stop();
        }
    }
}
