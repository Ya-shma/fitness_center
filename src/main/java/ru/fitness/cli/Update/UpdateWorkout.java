package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UpdateWorkout implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ВСЕ ЗАНЯТИЯ ---");
            WorkoutService service = ServiceFactory.getWorkoutService();
            var workouts = service.getAll();

            if (workouts.isEmpty()) {
                System.out.println("Занятия не найдены");
                return;
            }

            for (var workout : workouts) {
                System.out.println(workout.getId() + ". " + workout.getName() +
                        " | Время: " + workout.getDateTime() +
                        " | Вместимость: " + workout.getMaxCapacity());
            }

            System.out.print("\nВведите ID занятия для обновления: ");
            int id = scn.nextInt();
            scn.nextLine();

            Workout existing = service.getById(id);
            if (existing == null) {
                System.out.println("Занятие с ID " + id + " не найдено");
                return;
            }

            System.out.print("Введите новое название занятия: ");
            String newName = scn.nextLine();

            System.out.print("Введите новую дату и время (гггг-мм-дд чч:мм): ");
            String dateTimeStr = scn.nextLine();

            System.out.print("Введите новую продолжительность (минуты): ");
            int newDuration = scn.nextInt();

            System.out.print("Введите новую вместимость: ");
            int newCapacity = scn.nextInt();

            System.out.print("Введите новый ID тренера: ");
            int newCoachId = scn.nextInt();
            scn.nextLine();

            LocalDateTime newDateTime = parseDateTime(dateTimeStr);
            Workout updatedWorkout = new Workout(id, newName, newDateTime, newDuration, newCapacity, newCoachId);
            service.update(id, updatedWorkout);
            System.out.println("Занятие обновлено: " + newName);

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
        return "Обновить занятие";
    }
}