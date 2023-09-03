package org.lab6.task;

import org.lab6.Main;
import org.lab6.collection.data.Route;

public class MaxByDistanceTask implements Task {
    @Override
    public void execute(String[] args) {
        Route result = Main.getConnectionManager().getMaxByDistance();
        if(result == null)
            System.out.println("The collection is empty");
        else
            System.out.println(result);
    }
    @Override
    public String getDesctiption() {
        return "output the route with the longest distance";
    }
    @Override
    public String[] getArgumentNames() {
        return new String[0];
    }
}