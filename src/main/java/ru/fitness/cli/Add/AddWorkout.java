package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AddWorkout implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.print("Введите название занятия: ");
            String name = scn.nextLine();

            System.out.print("Введите дату и время (гггг-мм-дд чч:мм): ");
            String dateTimeStr = scn.nextLine();

            System.out.print("Введите продолжительность (минуты): ");
            int duration = scn.nextInt();

            System.out.print("Введите вместимость: ");
            int capacity = scn.nextInt();

            System.out.print("Введите ID тренера: ");
            int trainerId = scn.nextInt();
            scn.nextLine();

            LocalDateTime dateTime = parseDateTime(dateTimeStr);
            Workout workout = new Workout(0, name, dateTime, duration, capacity, trainerId);

            WorkoutService service = ServiceFactory.getWorkoutService();
            service.create(workout);

            System.out.println("✓ Занятие '" + name + "' добавлено!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Неверный формат даты. Используйте: гггг-мм-дд чч:мм");
        }
    }

    @Override
    public String getCommandName() {
        return "Добавить занятие";
    }
}