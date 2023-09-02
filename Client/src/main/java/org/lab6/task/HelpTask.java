package org.lab6.task;

import java.util.HashMap;
import java.util.Map;

/**
 * A task that displays information about available commands.
 */
public class HelpTask implements Task {

    private Map<String, String> commands;

    public HelpTask() {
        commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put("help", "shows information about commands");
        commands.put("info", "shows information about collection");
        commands.put("add", "inserts new element to collection");
        commands.put("update [id]", "updates element in collection");
        commands.put("remove_by_id", "removes element by its id");
        commands.put("clear", "clears the collection");
        commands.put("save", "saves collection to file");
        commands.put("execute_script", "executes script in file");
        commands.put("exit", "stops the program");
        commands.put("add_if_min", "inserts new element if it is minimal in collection");
        commands.put("remove_greater", "removes all the elements in collection greater than provided");
        commands.put("remove_lower", "removes all the elements in collection lower than provided");
        commands.put("group_counting_by_to", "groups elements by Location.To field and shows size of each");
        commands.put("count_greater_than_distance", "counts number of elements with distance greater than provided");
        commands.put("print_unique_distance", "prints all the unique values of distances in collection");
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");
        for (Map.Entry<String, String> entry : commands.entrySet()) {
            System.out.println("> " + entry.getKey() + " - " + entry.getValue());
        }
    }
}

