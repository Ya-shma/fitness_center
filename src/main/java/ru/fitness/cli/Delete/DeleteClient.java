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

            System.out.print("Enter the client's ID to delete: ");
            int id = scn.nextInt();
            scn.nextLine();

            ClientService service = ServiceFactory.getClientService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Client with ID " + id + " has been deleted!");
            } else {
                System.out.println("Client with ID " + id + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Delete a client by ID";
    }
}