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
        System.out.print("Введите ID тренера: ");
        try {
            int coachId = scn.nextInt();
            scn.nextLine();

            WorkoutService service = ServiceFactory.getWorkoutService();
            List<Workout> workouts = service.getWorkoutsByCoach(coachId);

            System.out.println("\n--- ЗАНЯТИЯ ТРЕНЕРА " + coachId + " ---");
            if (workouts.isEmpty()) {
                System.out.println("Занятия не найдены для этого тренера");
            } else {
                for (Workout workout : workouts) {
                    System.out.println(workout.getId() + ". " + workout.getName() +
                            " | Время: " + workout.getDateTime());
                }
            }
            System.out.println("Найдено: " + workouts.size() + " занятий");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Найти занятия по тренеру";
    }
}