package org.lab6.parser;

/**
 * Interface that represents parsers for commands
 */
public interface CommandParser {

    /**
     * Parse input and execute command
     * @param input line to be parsed
     * @param inputManager handles the input
     */
    void parse(String input, InputManager inputManager);
}
