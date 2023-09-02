package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class RemoveTask implements Task {

    private final long id;

    public RemoveTask(String id) throws NumberFormatException {
        this.id = Long.parseLong(id);
    }

    @Override
    public void execute() {
        if (CollectionManager.remove(id)) {
            System.out.println("Element was successfully removed");
        } else {
            System.out.println("No element with such id");
        }
    }
}