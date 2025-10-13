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

            System.out.print("Введите ID занятия для удаления: ");
            int id = scn.nextInt();
            scn.nextLine();

            WorkoutService service = ServiceFactory.getWorkoutService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Занятие с ID " + id + " удалено!");
            } else {
                System.out.println("Занятие с ID " + id + " не найдено");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Удалить занятие по ID";
    }
}