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
            System.out.print("Enter the client's full name: ");
            String name = scn.nextLine();

            System.out.print("Enter the client's phone number: ");
            String phone = scn.nextLine();

            Client client = new Client(0, name, phone);
            ClientService service = ServiceFactory.getClientService();
            service.create(client);

            System.out.println("Client '" + name + "' was added!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Add client";
    }
}