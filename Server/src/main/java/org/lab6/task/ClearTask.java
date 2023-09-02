package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class ClearTask implements Task {

    @Override
    public void execute() {
        System.out.println("Clearing the collection");
        CollectionManager.clear();
    }
}
