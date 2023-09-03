package org.lab6;


import org.lab6.collection.data.Route;
import org.lab6.udp.Client;
import org.lab6.udp.ServerCommand;
import org.lab6.udp.ServerCommandType;
import org.lab6.udp.exception.ServerRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionManager {
    private Client client;


    public ConnectionManager() throws IOException {
        client = new Client();
    }

    public ServerCommand send(ServerCommand command) {
        try {
            byte[] response = client.sendMsg(Utils.serializeObject(command));
            if (response == null)
                return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("An error occurred while receiving packets from the server"));
            return (ServerCommand) Utils.deserializeObject(response);
        } catch (IOException e) {
            return new ServerCommand(ServerCommandType.ERROR, Utils.serializeObject("Server response timeout exceeded"));
        }
    }

    public void clear() {
        if (send(new ServerCommand(ServerCommandType.CLEAR, null)).type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Failed to clear the collection.");
    }

    public List<String> getInfo() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET_INFO, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Failed to get the information.");
        return (ArrayList<String>) Utils.deserializeObject(response.data);
    }

    public void removeGreater(int id) {
        if (send(new ServerCommand(ServerCommandType.REMOVE_GREATER, Utils.intToBytes(id))).type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Failed to carry out the deletion.");
    }

    public boolean removeLast() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REMOVE_LAST, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось удалить элемент.");
        return response.data[0] == 1;
    } // false = коллекция пуста

    public void reorder() {
        if (send(new ServerCommand(ServerCommandType.REORDER, null)).type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось выполнить операцию.");
    }

    public Route getMaxByDistance() {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET_MAX_DISTANCE, null));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Не удалось получить информацию.");
        return (Route) Utils.deserializeObject(response.data);
    }

    public List<Object> show(int page) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.SHOW, Utils.intToBytes(page)));
        if (response.type == ServerCommandType.ERROR) {
            if (response.data == null || response.data.length == 0)
                throw new ServerRuntimeException("Failed to get the information.");
            throw new ServerRuntimeException((String) Utils.deserializeObject(response.data));
        }
        return (List<Object>) Utils.deserializeObject(response.data);
    }

    public boolean removeById(int id) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.REMOVE_BY_ID, Utils.intToBytes(id)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("Указанный элемент не найден.");
        return response.data[0] == 1;
    }

    public boolean add(Route organization) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.ADD, Utils.serializeObject(organization)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String) Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    } // по дефолту отправляется org.id = -1; false - ошибка валидации, передавать в ответе текст ошибки

    public Route get(int id) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.GET, Utils.intToBytes(id)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException("The object with the given \"id\" does not exist.");
        return (Route) Utils.deserializeObject(response.data);
    } // часть команды Update - перед обновлением происходит проверка на существование объекта и получение его данных

    public boolean update(Route organization) {
        ServerCommand response = send(new ServerCommand(ServerCommandType.UPDATE, Utils.serializeObject(organization)));
        if (response.type == ServerCommandType.ERROR)
            throw new ServerRuntimeException((String) Utils.deserializeObject(response.data));
        return response.data[0] == 1;
    } // отправляется org.id = объект для изменения; false - ошибка валидации, передавать в ответе текст ошибки
}
