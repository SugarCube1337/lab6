package org.lab6.actions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.lab6.Utils;

import org.lab6.Main;
import org.lab6.collection.data.Route;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;

/**
 * Command for showing current objects in collection
 */
public class ShowAction implements Action {
    private final int PAGE_SIZE = 5;

    @Override
    public ServerCommand execute(byte[] args) {
        HashSet<Route> routeSet = Main.getStorageManager().getAll();
        int size = routeSet.size();

        if (size == 0)
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("The collection is empty"));

        int page = Utils.fromByteArray(args) - 1;
        int maxPage = (int) Math.ceil((float) size / (float) PAGE_SIZE);

        if (page + 1 > maxPage)
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Maximum page number - " + maxPage));

        List<Object> response = new ArrayList<>();
        response.add(maxPage);

        List<Route> routeList = new ArrayList<>(routeSet);
        int startIndex = page * PAGE_SIZE;
        int endIndex = Math.min((page + 1) * PAGE_SIZE, size);

        List<Route> parsed = new ArrayList<>(routeList.subList(startIndex, endIndex)); // Создаем копию подсписка
        response.add(parsed);

        return new ServerCommand(ServerCommandType.SHOW, Utils.serializeObject(response));
    }
}

