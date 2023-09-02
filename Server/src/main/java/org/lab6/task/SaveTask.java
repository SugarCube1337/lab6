package org.lab6.task;

import org.lab6.Main;
import org.lab6.collection.CollectionManager;

import java.io.IOException;

public class SaveTask implements Task {

    @Override
    public void execute() {
        try {
            System.out.println("Saving the collection");
            CollectionManager.save(Main.getPath());
        } catch (IOException ex) {
            System.out.println("Error while saving data: " + ex.getMessage());
        }
    }
}

