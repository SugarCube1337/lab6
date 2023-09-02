package org.lab6.task;

import org.lab6.collection.CollectionManager;
import org.lab6.parser.InputManager;

/**
 * Represents a task to add a minimal route to the collection.
 */
public class AddMinTask implements Task {
    private final InputManager inputManager;

    public AddMinTask(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public void execute() {
        boolean refillElement = true;
        while (refillElement) {
            var route = Tasks.getRoute(inputManager);
            System.out.println("You are about to add minimal route:");
            System.out.println(route);
            if (Tasks.getApproval("Proceed", inputManager)) {
                if (CollectionManager.addIfMin(route)) {
                    System.out.println("Successfully added element");
                } else {
                    System.out.println("Element wasn't added");
                }
                return;
            }
            refillElement = Tasks.getApproval("Do you want to refill element with another values", inputManager);
        }
    }
}
