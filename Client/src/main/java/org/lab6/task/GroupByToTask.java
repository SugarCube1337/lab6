package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class GroupByToTask implements Task {

    @Override
    public void execute() {
        System.out.print(CollectionManager.countByTo());
    }
}
