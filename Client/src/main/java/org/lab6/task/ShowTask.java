package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class ShowTask implements Task {

    @Override
    public void execute() {
        System.out.println(CollectionManager.getElements());
    }
}
