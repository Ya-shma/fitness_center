package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.Scanner;

public class GetCoachById implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the coach's ID: ");
        try {
            int id = scn.nextInt();
            scn.nextLine();

            CoachService service = ServiceFactory.getCoachService();
            Coach coach = service.getById(id);

            if (coach != null) {
                System.out.println("A coach has been found: " + coach.getFullName());
            } else {
                System.out.println("Coach with ID " + id + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Find a coach by ID";
    }
}