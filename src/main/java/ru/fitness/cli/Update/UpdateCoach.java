package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class UpdateCoach implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ALL COACHES ---");
            CoachService coachService = ServiceFactory.getCoachService();
            var coaches = coachService.getAll();

            if (coaches.isEmpty()) {
                System.out.println("No coaches found");
                return;
            }

            for (var coach : coaches) {
                String specName = coach.getSpecialization() != null ?
                        coach.getSpecialization().getName() : "not specified";
                System.out.println(coach.getId() + ". " + coach.getFullName() +
                        " (Specialization: " + specName + ")");
            }

            System.out.print("\nEnter the coach's ID to update: ");
            int id = scn.nextInt();
            scn.nextLine();

            Coach existing = coachService.getById(id);
            if (existing == null) {
                System.out.println("Coach with ID " + id + " wasn't found");
                return;
            }

            System.out.print("Enter the coach's new full name: ");
            String newName = scn.nextLine();

            Specialization specialization = selectSpecialization();
            if (specialization == null) {
                return;
            }

            Coach updatedCoach = new Coach(id, newName, specialization);

            coachService.update(id, updatedCoach);

            System.out.println("The coach has been updated: " + newName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    private Specialization selectSpecialization() {
        try {
            SpecializationService specService = ServiceFactory.getSpecializationService();
            var specializations = specService.getAll();

            System.out.println("\n--- AVAILABLE SPECIALIZATIONS ---");
            if (specializations.isEmpty()) {
                System.out.println("Specializations not found");
                return null;
            }

            for (var spec : specializations) {
                System.out.println(spec.getId() + ". " + spec.getName());
            }

            System.out.print("\nEnter the specialization ID: ");
            int specId = scn.nextInt();
            scn.nextLine();

            Specialization specialization = specService.getById(specId);
            if (specialization == null) {
                System.out.println("Error: Specialization with ID " + specId + " doesn't exist!");
                return null;
            }

            return specialization;

        } catch (Exception e) {
            System.out.println("Error when choosing a specialization: " + e.getMessage());
            scn.nextLine();
            return null;
        }
    }

    @Override
    public String getCommandName() {
        return "Update coach";
    }
}