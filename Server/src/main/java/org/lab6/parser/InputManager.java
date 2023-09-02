package org.lab6.parser;

public interface InputManager {

    /**
     * Starts manager
     */
    void start();

    /**
     * Stops manager
     */
    void stop();

    /**
     * Checks if manager has data to input
     * @return true if manager has data to input and can continue
     */
    boolean hasNext();

    /**
     * Requests data
     * @return requested data
     */
    String request();
}