package org.lab6.actions;

import org.lab6.Main;
import org.lab6.Utils;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class InfoAction implements Action {
    @Override
    public ServerCommand execute(byte[] args) {
        HashSet<Route> elements = Main.getStorageManager().getAll();
        String elementType = "Unknown";

        if (!elements.isEmpty()) {
            // Получаем первый элемент множества
            Route firstElement = elements.iterator().next();
            elementType = firstElement.getClass().getName();
        }

        LocalDate initializationDate = Main.getStorageManager().getInitializationDate();
        int numberOfElements = elements.size();

        List<String> result = new ArrayList<>();
        result.add("Type of elements: " + elementType);
        result.add("Initialization date: " + initializationDate);
        result.add("Number of elements: " + numberOfElements);

        return new ServerCommand(ServerCommandType.GET_INFO, Utils.serializeObject(result));
    }
}
