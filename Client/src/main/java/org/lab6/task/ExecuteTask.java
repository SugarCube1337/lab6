package org.lab6.task;

import org.lab6.Main;

import java.io.*;
import java.util.*;

/**
 * The `ExecuteTask` class represents a task that executes a script from a specified file.
 * It processes the commands in the script file, allowing for recursive execution of scripts.
 */
public class ExecuteTask implements Task {
    public static List<String> executedScripts = new ArrayList<>();
    @Override
    public void execute(String[] args) {
        if(args.length < 1) {
            System.out.println("You need to specify the file name, the usage: execute_script [file_name]");
            return;
        }
        List<String> commands = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        executedScripts.add(args[0]);
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(args[0]));
            int c;
            while ((c = reader.read()) != -1) {
                if ((char) c == '\n') {
                    String finished = line.toString();
                    line = new StringBuilder();
                    if(finished.startsWith("execute_script")) {
                        boolean pass = true;
                        for(String script : new ArrayList<>(executedScripts))
                            if(finished.contains(script)) {
                                System.out.println("You cannot run a script that has been run before");
                                pass = false;
                                break;
                            }
                        if(pass) {
                            executedScripts.add(finished.replace("\r", ""));
                            commands.add(finished.replace("\r", "")); // Windows adaptation
                        }
                    } else
                        commands.add(finished.replace("\r", "")); // Windows adaptation
                } else
                    line.append((char) c);
            }
        } catch(IOException ex) {
            System.out.println("Failed to read data from file");
        }
        if(line.length() > 0 && line.toString().replace("\r", "").replace("\n", "").length() > 0) {
            if(line.toString().startsWith("execute_script")) {
                boolean pass = true;
                for(String script : new ArrayList<>(executedScripts))
                    if(line.toString().contains(script)) {
                        System.out.println("You cannot run a script that has been run before");
                        pass = false;
                        break;
                    }
                if(pass) {
                    executedScripts.add(line.toString().replace("\r", "").replace("\n", ""));
                    commands.add(line.toString().replace("\r", "").replace("\n", "")); // Windows adaptation
                }
            }
            else
                commands.add(line.toString().replace("\r", "").replace("\n", "")); // Windows adaptation
        }
        for(String command : commands)
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




