package org.lab6.task;

import org.lab6.collection.CollectionManager;
import org.lab6.parser.InputManager;

public class UpdateTask implements Task {

    private final InputManager inputManager;
    private final long id;

    public UpdateTask(InputManager inputManager, String id) throws IllegalArgumentException {
        this.inputManager = inputManager;
        this.id = Long.parseLong(id);
        if (!CollectionManager.isIdPresent(this.id)) {
            throw new IllegalArgumentException("No element with such id");
        }
    }

    @Override
    public void execute() {
        while (true) {
            var route = Tasks.getRoute(inputManager);
            System.out.println("You are about to update route with id " + id + ":");
            System.out.println(route);
            if (Tasks.getApproval("Proceed", inputManager)) {
                if (CollectionManager.update(route, id)) {
                    System.out.println("Successfully updated element");
                } else {
                    System.out.println("Element wasn't updated");
                }
                return;
            }
            if (!Tasks.getApproval("Do you want to refill element with another values", inputManager)) {
                return;
            }
        }
    }
}
