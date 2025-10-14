package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.List;
import java.util.Scanner;

public class GetCoachBySpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the specialization ID: ");
        try {
            int specId = scn.nextInt();
            scn.nextLine();

            CoachService service = ServiceFactory.getCoachService();
            List<Coach> coaches = service.getCoachBySpecialization(specId);

            System.out.println("\n--- COACHES BY SPECIALIZATION " + specId + " ---");
            if (coaches.isEmpty()) {
                System.out.println("No coaches have been found for this specialization.");
            } else {
                for (Coach coach : coaches) {
                    System.out.println(coach.getId() + ". " + coach.getFullName());
                }
            }
            System.out.println("Found: " + coaches.size() + " coaches");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Find coaches by specialization";
    }
}