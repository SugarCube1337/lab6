package org.lab6.parser;

import org.lab6.task.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Basic command parser based on regular expressions
 */
public class BaseCommandParser implements CommandParser {

    private static final Pattern commandPattern;

    /**
     * Checks if input is matched with command pattern
     *
     * @param command parsed command represented as array of keywords
     * @param args    command pattern represented as array of keywords,
     *                values represent command keywords <b>null</b> represents parameters
     * @return true if matches, otherwise false
     */
    private static boolean isMatching(List<? extends CharSequence> command, String... args) {
        if (command.size() != args.length) {
            return false;
        }
        for (var it = 0; it < command.size(); ++it) {
            if (args[it] == null) {
                continue;
            }
            if (!Objects.equals(command.get(it), args[it])) {
                return false;
            }
        }
        return true;
    }
}

    /**
     *
     * @param command command represented as array of keywords
     * @param inputManager handles the input
     * @return matched task to execute
     * @throws IOException thrown when there are troubles with file
     * @throws NoSuchMethodException thrown when command is not matched
     */
    /**private Task match(List<String> command, InputManager inputManager) throws IOException, NoSuchMethodException {
        if (isMatching(command, "help")) {
            return new HelpTask();
        }
        if (isMatching(command, "info")) {
            return new InfoTask();
        }
        if (isMatching(command, "show")) {
            return new ShowTask();
        }
        if (isMatching(command, "add")) {
            return new AddTask(inputManager);
        }
        if (isMatching(command, "update", null)) {
            return new UpdateTask(inputManager, command.get(1));
        }
        if (isMatching(command, "remove_by_id", null)) {
            return new RemoveTask(command.get(1));
        }
        if (isMatching(command, "clear")) {
            return new ClearTask();
        }
        if (isMatching(command, "save")) {
            return new SaveTask();
        }
        if (isMatching(command, "execute_script", null)) {
            return new ExecuteTask(this, command.get(1), inputManager);
        }
        if (isMatching(command, "exit")) {
            return inputManager::stop;
        }
        if (isMatching(command, "add_if_min")) {
            return new AddMinTask(inputManager);
        }
        if (isMatching(command, "remove_greater")) {
            return new RemoveGreaterTask(inputManager);
        }
        if (isMatching(command, "remove_lower")) {
            return new RemoveLowerTask(inputManager);
        }
        if (isMatching(command, "group_counting_by_to")) {
            return new GroupByToTask();
        }
        if (isMatching(command, "count_greater_than_distance", null)) {
            return new CountGreaterDistance(command.get(1));
        }
        if (isMatching(command, "print_unique_distance")) {
            return new PrintUniqueDistanceTask();
        }
        throw new NoSuchMethodException("Can't match command: " + command);
    }

    /**
     * Parses the command using regular expressions
     * @param input line to be parsed
     * @param inputManager handles the input
     *
    @Override
    public void parse(String input, InputManager inputManager) {
        input = input.trim();
        var args = new ArrayList<String>();
        var matcher = commandPattern.matcher(input);
        while (matcher.find()) {
            args.add(matcher.group());
        }
        try {
            var sus = match(args, inputManager);
            sus.execute();
        } catch (IOException | NoSuchMethodException ex) {
            System.out.println("Error while processing the command: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Incorrect arguments: " + ex.getMessage());
        }
    }

    static {
        commandPattern = Pattern.compile("\\S+");
    }
}

*/