package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class InfoTask implements Task {

    @Override
    public void execute() {
        System.out.println(CollectionManager.getInfo());
    }
}
