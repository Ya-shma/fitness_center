package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllClients;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.Scanner;

public class DeleteClient implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllClients().execute();

            System.out.print("Введите ID клиента для удаления: ");
            int id = scn.nextInt();
            scn.nextLine();

            ClientService service = ServiceFactory.getClientService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Клиент с ID " + id + " удален!");
            } else {
                System.out.println("Клиент с ID " + id + " не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Удалить клиента по ID";
    }
}