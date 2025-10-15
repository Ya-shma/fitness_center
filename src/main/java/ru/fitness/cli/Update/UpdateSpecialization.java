package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class UpdateSpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ALL SPECIALIZATIONS ---");
            SpecializationService service = ServiceFactory.getSpecializationService();
            var specializations = service.getAll();

            if (specializations.isEmpty()) {
                System.out.println("Specializations not found");
                return;
            }

            for (var spec : specializations) {
                System.out.println(spec.getId() + ". " + spec.getName());
            }

            System.out.print("\nEnter the specialization ID to update: ");
            int id = scn.nextInt();
            scn.nextLine();

            Specialization existing = service.getById(id);
            if (existing == null) {
                System.out.println("Specialization with ID " + id + " wasn't found");
                return;
            }

            System.out.print("Enter a new specialization name: ");
            String newName = scn.nextLine();

            Specialization updatedSpec = new Specialization(id, newName);
            service.update(id, updatedSpec);
            System.out.println("Specialization updated: " + newName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Update Specialization";
    }
}