package org.lab6.task;

public class ExitTask implements Task {
    @Override
    public void execute(String[] args) {
        System.exit(0);
    }
    @Override
    public String getDesctiption() {
        return "terminate the program (without saving to a file)";
    }
    @Override
    public String[] getArgumentNames() {
        return new String[0];
    }
}


