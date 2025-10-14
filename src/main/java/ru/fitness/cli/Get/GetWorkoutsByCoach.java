package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.util.List;
import java.util.Scanner;

public class GetWorkoutsByCoach implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the coach's ID: ");
        try {
            int coachId = scn.nextInt();
            scn.nextLine();

            WorkoutService service = ServiceFactory.getWorkoutService();
            List<Workout> workouts = service.getWorkoutsByCoach(coachId);

            System.out.println("\n--- WORKOUTS OF COACH " + coachId + " ---");
            if (workouts.isEmpty()) {
                System.out.println("No workouts were found for this coach");
            } else {
                for (Workout workout : workouts) {
                    System.out.println(workout.getId() + ". " + workout.getName() +
                            " | Time: " + workout.getDateTime());
                }
            }
            System.out.println("Found: " + workouts.size() + " workouts");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Find workouts by coach";
    }
}