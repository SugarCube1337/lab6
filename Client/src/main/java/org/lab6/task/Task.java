package org.lab6.task;

/**
 * Interface that represents task for command
 */
@FunctionalInterface
public interface Task {

    /**
     * Execute corresponding task
     */
    void execute();
}
