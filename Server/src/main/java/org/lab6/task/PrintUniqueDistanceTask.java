package org.lab6.task;

import org.lab6.collection.CollectionManager;

public class PrintUniqueDistanceTask implements Task {

    @Override
    public void execute() {
        var distances = CollectionManager.uniqueDistances();
        if (distances.isEmpty()) {
            System.out.println("Collection is empty");
        }
        var res = new StringBuilder("Unique distances: ");
        var it = distances.iterator();
        while (it.hasNext()) {
            res.append(it.next());
            if (it.hasNext()) {
                res.append(", ");
            }
        }
        System.out.println(res);
    }
}
