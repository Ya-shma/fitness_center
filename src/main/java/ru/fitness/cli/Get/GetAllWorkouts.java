package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.util.List;

public class GetAllWorkouts implements Command {
    @Override
    public void execute() {
        WorkoutService service = ServiceFactory.getWorkoutService();
        List<Workout> workouts = service.getAll();

        System.out.println("\n--- ALL WORKOUTS ---");
        if (workouts.isEmpty()) {
            System.out.println("No workout found");
        } else {
            for (Workout workout : workouts) {
                System.out.println(workout.getId() + ". " + workout.getName() +
                        " | Time: " + workout.getDateTime() +
                        " | Capacity: " + workout.getMaxCapacity());
            }
        }
        System.out.println("Total: " + workouts.size() + " workouts");
    }

    @Override
    public String getCommandName() {
        return "Show all workouts";
    }
}