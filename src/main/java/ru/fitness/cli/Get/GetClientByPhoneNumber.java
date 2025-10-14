package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Client;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.Scanner;

public class GetClientByPhoneNumber implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the phone number for the search: ");
        String phoneNumber = scn.nextLine();

        try {
            ClientService service = ServiceFactory.getClientService();
            Client client = service.getClientByPhoneNumber(phoneNumber);

            if (client != null) {
                System.out.println("A client has been found: " + client.getFullName() + " | ID: " + client.getId());
            } else {
                System.out.println("A client with phone number " + phoneNumber + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Find a client by phone number";
    }
}