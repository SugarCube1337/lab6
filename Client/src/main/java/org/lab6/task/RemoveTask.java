package org.lab6.task;

import org.lab6.Main;

/**
 * Command for removing collection element by id
 */
public class RemoveTask implements Task {
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("Необходимо указать id, использование: remove_by_id [id]");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
            System.out.println("Введено неверное число");
            return;
        }
        if (Main.getConnectionManager().removeById(id))
            System.out.println("Объект удален");
        else
            System.out.println("Объекта с данным id не существует");
    }

    @Override
    public String getDesctiption() {
        return "удалить элемент из коллекции";
    }

    @Override
    public String[] getArgumentNames() {
        return new String[]{"id"};
    }
}