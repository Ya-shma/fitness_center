package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllSpecializations;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class DeleteSpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllSpecializations().execute();

            System.out.print("Enter the specialization ID to delete: ");
            int id = scn.nextInt();
            scn.nextLine();

            SpecializationService service = ServiceFactory.getSpecializationService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Specialization with ID " + id + " has been deleted!");
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
        return "Delete a specialization by ID";
    }
}