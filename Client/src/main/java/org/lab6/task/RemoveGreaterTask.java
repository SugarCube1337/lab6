package org.lab6.task;

import org.lab6.collection.CollectionManager;
import org.lab6.parser.InputManager;

public class RemoveGreaterTask implements Task {

    private final InputManager inputManager;

    public RemoveGreaterTask(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void execute() {
        var route = Tasks.getRoute(inputManager);
        if (CollectionManager.removeGreater(route)) {
            System.out.println("Elements were removed");
        } else {
            System.out.println("None of the elements were removed");
        }
    }
}

