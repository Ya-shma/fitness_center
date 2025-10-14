package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllWorkouts;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.util.Scanner;

public class DeleteWorkout implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllWorkouts().execute();

            System.out.print("Enter the workout ID to delete: ");
            int id = scn.nextInt();
            scn.nextLine();

            WorkoutService service = ServiceFactory.getWorkoutService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Workout with ID " + id + " has been deleted!");
            } else {
                System.out.println("Workout with ID " + id + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Delete a workout by ID";
    }
}