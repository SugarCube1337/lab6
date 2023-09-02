package org.lab6.task;

import org.lab6.parser.CommandParser;
import org.lab6.parser.InputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The `ExecuteTask` class represents a task that executes a script from a specified file.
 * It processes the commands in the script file, allowing for recursive execution of scripts.
 */
public class ExecuteTask implements Task {

    private final CommandParser parser;
    private final String filepath;
    private final InputManager inputManager;

    public ExecuteTask(CommandParser parser, String filepath, InputManager inputManager) {
        this.parser = parser;
        this.filepath = filepath;
        this.inputManager = inputManager;
    }

    @Override
    public void execute() {
        Set<String> used = new HashSet<>();
        processScript(filepath, used);
    }

    private void processScript(String scriptPath, Set<String> used) {
        if (used.contains(scriptPath)) {
            System.out.println("Recursion detected: " + scriptPath);
            return;
        }

        used.add(scriptPath);

        try (Scanner scanner = new Scanner(new File(scriptPath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
                if (line.startsWith("execute_script")) {
                    String match = line.substring("execute_script".length()).trim();
                    if (used.contains(match)) {
                        System.out.println("Recursion detected: " + match);
                    } else {
                        processScript(match, new HashSet<>(used));
                    }
                } else {
                    parser.parse(line, inputManager);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Error while reading the file: " + e.getMessage());
        }
    }
}




