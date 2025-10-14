package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.List;

public class GetAllCoaches implements Command {
    @Override
    public void execute() {
        CoachService service = ServiceFactory.getCoachService();
        List<Coach> coaches = service.getAll();

        System.out.println("\n--- ALL COACHES ---");
        if (coaches.isEmpty()) {
            System.out.println("No coaches found");
        } else {
            for (Coach coach : coaches) {
                System.out.println(coach.getId() + ". " + coach.getFullName() +
                        " (Specialization: " + coach.getSpecialization().getId() + ")");
            }
        }
        System.out.println("Total: " + coaches.size() + " coaches");
    }

    @Override
    public String getCommandName() {
        return "Show all coaches";
    }
}