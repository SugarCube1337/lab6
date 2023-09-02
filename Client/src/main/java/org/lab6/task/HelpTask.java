package org.lab6.task;

import org.lab6.Main;

public class HelpTask implements Task {
    @Override
    public void execute(String[] args) {
        for (String commandName : Main.getCommandManager().getCommands().keySet()) {
            System.out.print(commandName);
            Task command = Main.getCommandManager().getCommands().get(commandName);
            for (String argument : command.getArgumentNames())
                System.out.print(" [" + argument + "]");
            System.out.println(": " + command.getDesctiption());
        }
    }

    @Override
    public String getDesctiption() {
        return "help on available commands";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[0];
    }
}

