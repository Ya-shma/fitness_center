package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.entities.Client;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.Scanner;

public class AddClient implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.print("Введите ФИО клиента: ");
            String name = scn.nextLine();

            System.out.print("Введите телефон клиента: ");
            String phone = scn.nextLine();

            Client client = new Client(0, name, phone);
            ClientService service = ServiceFactory.getClientService();
            service.create(client);

            System.out.println("✓ Клиент '" + name + "' добавлен!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Добавить клиента";
    }
}