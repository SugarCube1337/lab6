package org.lab6.task;

import org.lab6.Main;

/**
 * Command for reordering collection
 */
public class ReorderTask implements Task {
    @Override
    public void execute(String[] args) {
        Main.getConnectionManager().reorder();
        System.out.println("The objects are sorted in reverse order");
    }
    @Override
    public String getDesctiption() {
        return "sort the collection in reverse order";
    }
    @Override
    public String[] getArgumentNames() {
        return new String[0];
    }
}
