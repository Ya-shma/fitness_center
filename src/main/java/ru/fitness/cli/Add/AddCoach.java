package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class AddCoach implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.print("Enter the coach's full name: ");
            String name = scn.nextLine();

            Specialization specialization = selectSpecialization();
            if (specialization == null) {
                return;
            }

            Coach coach = new Coach(0, name, specialization);
            CoachService service = ServiceFactory.getCoachService();
            service.create(coach);

            System.out.println("Coach '" + name + "' was added! Specialization: " + specialization.getName());
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
                System.out.println("No specializations were found. Add specializations first.");
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
                System.out.println("Error: Specialization with ID " + specId + " does not exist!");
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
        return "Add coach";
    }
}