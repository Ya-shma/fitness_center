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

        System.out.println("\n--- ВСЕ ЗАНЯТИЯ ---");
        if (workouts.isEmpty()) {
            System.out.println("Занятия не найдены");
        } else {
            for (Workout workout : workouts) {
                System.out.println(workout.getId() + ". " + workout.getName() +
                        " | Время: " + workout.getDateTime() +
                        " | Вместимость: " + workout.getMaxCapacity());
            }
        }
        System.out.println("Всего: " + workouts.size() + " занятий");
    }

    @Override
    public String getCommandName() {
        return "Показать все занятия";
    }
}