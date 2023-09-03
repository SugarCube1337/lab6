package org.lab6;


import org.lab6.task.*;
import org.lab6.task.Task;
import org.lab6.udp.exception.ServerRuntimeException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandManager {
    private Map<String, Task> commands = new LinkedHashMap<>();
    public CommandManager() {
        registerCommand("help", new HelpTask());
        registerCommand("info", new InfoTask());
        registerCommand("show", new ShowTask());
        registerCommand("add", new AddTask());
        registerCommand("update", new UpdateTask());
        registerCommand("remove_by_id", new RemoveTask());
        registerCommand("clear", new ClearTask());
        registerCommand("execute_script", new ExecuteTask());
        registerCommand("exit", new ExitTask());
        registerCommand("remove_greater", new RemoveGreaterTask());
        registerCommand("remove_last", new RemoveLastTask());
        registerCommand("reorder", new ReorderTask());
        registerCommand("max_by_distance", new MaxByDistanceTask());

    }
    /**
     * Start reading user prompt
     */
    public void run() {
        System.out.println("To display the available commands, write help");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            ExecuteTask.executedScripts.clear();
            System.out.print("> ");
            try {
                executeInput(scanner.nextLine());
            } catch(ServerRuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    /**
     * Execute command
     * @param input Command with arguments
     */
    public final void executeInput(String input) {
        String[] command = input.split(" ", 2);
        if(!commands.containsKey(command[0]))
            System.out.println("Unknown command: '"+command[0]+"'");
        else
            commands.get(command[0]).execute(command.length > 1 ? command[1].split(" ") : new String[0]);
    }
    /**
     * Register command
     * @param name Command name
     * @param command Object with command interface
     */
    public final void registerCommand(String name, Task command) {
        commands.put(name, command);
    }
    /**
     * Get list of commands
     * @return Available commands
     */
    public Map<String, Task> getCommands() {
        return commands;
    }
}
