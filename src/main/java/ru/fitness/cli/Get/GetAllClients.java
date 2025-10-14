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

        System.out.println("\n--- ALL CLIENTS ---");
        if (clients.isEmpty()) {
            System.out.println("No clients found");
        } else {
            for (Client client : clients) {
                System.out.println(client.getId() + ". " + client.getFullName() +
                        " | Phone number: " + client.getPhoneNumber());
            }
        }
        System.out.println("Total: " + clients.size() + " clients");
    }

    @Override
    public String getCommandName() {
        return "Show all clients";
    }
}