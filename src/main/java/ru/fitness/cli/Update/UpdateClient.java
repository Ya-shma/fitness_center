package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Client;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.Scanner;

public class UpdateClient implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ВСЕ КЛИЕНТЫ ---");
            ClientService service = ServiceFactory.getClientService();
            var clients = service.getAll();

            if (clients.isEmpty()) {
                System.out.println("Клиенты не найдены");
                return;
            }

            for (var client : clients) {
                System.out.println(client.getId() + ". " + client.getFullName() +
                        " | Телефон: " + client.getPhoneNumber());
            }

            System.out.print("\nВведите ID клиента для обновления: ");
            int id = scn.nextInt();
            scn.nextLine();

            Client existing = service.getById(id);
            if (existing == null) {
                System.out.println("Клиент с ID " + id + " не найден");
                return;
            }

            System.out.print("Введите новое ФИО клиента: ");
            String newName = scn.nextLine();

            System.out.print("Введите новый телефон клиента: ");
            String newPhone = scn.nextLine();

            Client updatedClient = new Client(id, newName, newPhone);

            service.update(id, updatedClient);

            System.out.println("Клиент обновлен: " + newName);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Обновить клиента";
    }
}