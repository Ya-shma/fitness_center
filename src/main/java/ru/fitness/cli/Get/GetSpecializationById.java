package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class GetSpecializationById implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the specialization ID: ");
        try {
            int id = scn.nextInt();
            scn.nextLine();

            SpecializationService service = ServiceFactory.getSpecializationService();
            Specialization spec = service.getById(id);

            if (spec != null) {
                System.out.println("Specialization found: " + spec.getName());
            } else {
                System.out.println("Specialization with ID " + id + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Find a specialization by ID";
    }
}