package org.lab6.task;

import org.lab6.Lab5;
import org.lab6.collection.CollectionManager;

import java.io.IOException;

public class SaveTask implements Task {

    @Override
    public void execute() {
        try {
            System.out.println("Saving the collection");
            CollectionManager.save(Lab5.getPath());
        } catch (IOException ex) {
            System.out.println("Error while saving data: " + ex.getMessage());
        }
    }
}

