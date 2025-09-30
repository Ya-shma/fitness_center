package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Client;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.List;

public class GetAllClients implements Command {
    @Override
    public void execute() {
        ClientService service = ServiceFactory.getClientService();
        List<Client> clients = service.getAll();

        System.out.println("\n--- ВСЕ КЛИЕНТЫ ---");
        if (clients.isEmpty()) {
            System.out.println("Клиенты не найдены");
        } else {
            for (Client client : clients) {
                System.out.println(client.getId() + ". " + client.getFullName() +
                        " | Телефон: " + client.getPhoneNumber());
            }
        }
        System.out.println("Всего: " + clients.size() + " клиентов");
    }

    @Override
    public String getCommandName() {
        return "Показать всех клиентов";
    }
}