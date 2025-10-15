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
            System.out.println("\n--- ALL CLIENTS ---");
            ClientService service = ServiceFactory.getClientService();
            var clients = service.getAll();

            if (clients.isEmpty()) {
                System.out.println("No clients found");
                return;
            }

            for (var client : clients) {
                System.out.println(client.getId() + ". " + client.getFullName() +
                        " | Phone number: " + client.getPhoneNumber());
            }

            System.out.print("\nEnter the client's ID for the update: ");
            int id = scn.nextInt();
            scn.nextLine();

            Client existing = service.getById(id);
            if (existing == null) {
                System.out.println("Client with ID " + id + " wasn't found");
                return;
            }

            System.out.print("Enter the new full name of the client: ");
            String newName = scn.nextLine();

            System.out.print("Enter the client's new phone number: ");
            String newPhone = scn.nextLine();

            Client updatedClient = new Client(id, newName, newPhone);

            service.update(id, updatedClient);

            System.out.println("The client has been updated: " + newName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Update client";
    }
}