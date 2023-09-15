package org.lab6.task;

import org.lab6.Main;

import java.io.*;
import java.util.*;

/**
 * The `ExecuteTask` class represents a task that executes a script from a specified file.
 * It processes the commands in the script file, allowing for recursive execution of scripts.
 */
public class ExecuteTask implements Task {
    public static List<String> executedScripts = new ArrayList<>(); // List to keep track of executed scripts to prevent recursion

    /**
     * Executes the 'execute_script' task to run a script from a given file.
     * This method reads the specified script file, processes its commands, and ensures recursion prevention.
     *
     * @param args Command-line arguments, where args[0] should be the file name of the script.
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("You need to specify the file name, the usage: execute_script [file_name]");
            return;
        }
        List<String> commands = new ArrayList<>();// List to store the commands from the script
        StringBuilder line = new StringBuilder();
        executedScripts.add(args[0]);// Add the current script to the executedScripts list
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(args[0]));
            int c;
            // Read the script file character by character
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    String finished = line.toString();
                    line = new StringBuilder();
                    if (finished.startsWith("execute_script")) {// Check if the line starts with "execute_script" (recursion)
                        boolean pass = true;
                        // Check if the script has already been executed to prevent recursion
                        for (String script : new ArrayList<>(executedScripts))
                            if (finished.contains(script)) {
                                System.out.println("You cannot run a script that has been run before");
                                pass = false;
                                break;
                            }
                        // If not executed before, add it to the list of commands
                        if (pass) {
                            executedScripts.add(finished.replace("\r", ""));
                            commands.add(finished.replace("\r", "")); // Windows adaptation
                        }
                    } else
                        // If it's not an "execute_script" command, add it to the list of commands
                        commands.add(finished.replace("\r", "")); // Windows adaptation
                } else
                    // Append characters to the current line
                    line.append((char) c);
            }
        } catch (IOException ex) {
            System.out.println("Failed to read data from file");
        }
        if (line.length() > 0 && line.toString().replace("\r", "").replace("\n", "").length() > 0) {
            if (line.toString().startsWith("execute_script")) {
                boolean pass = true;
                for (String script : new ArrayList<>(executedScripts))
                    if (line.toString().contains(script)) {
                        System.out.println("You cannot run a script that has been run before");
                        pass = false;
                        break;
                    }
                if (pass) {
                    executedScripts.add(line.toString().replace("\r", "").replace("\n", ""));
                    commands.add(line.toString().replace("\r", "").replace("\n", "")); // Windows adaptation
                }
            } else
                commands.add(line.toString().replace("\r", "").replace("\n", "")); // Windows adaptation
        }
        for (String command : commands)
            Main.getCommandManager().executeInput(command);
    }

    @Override
    public String getDesctiption() {
        return "execute the script from the given file";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[]{"file_name"};
    }
}




