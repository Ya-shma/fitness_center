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
            System.out.println("\n--- ALL WORKOUTS ---");
            WorkoutService service = ServiceFactory.getWorkoutService();
            var workouts = service.getAll();

            if (workouts.isEmpty()) {
                System.out.println("No workout found");
                return;
            }

            for (var workout : workouts) {
                System.out.println(workout.getId() + ". " + workout.getName() +
                        " | Time: " + workout.getDateTime() +
                        " | Capacity: " + workout.getMaxCapacity());
            }

            System.out.print("\nEnter the workout ID to update: ");
            int id = scn.nextInt();
            scn.nextLine();

            Workout existing = service.getById(id);
            if (existing == null) {
                System.out.println("Workout with ID " + id + " wasn't found");
                return;
            }

            System.out.print("Enter a new workout name: ");
            String newName = scn.nextLine();

            System.out.print("Enter a new date and time (dd.mm.yyyy hh:mm): ");
            String dateTimeStr = scn.nextLine();

            System.out.print("Enter the new duration (minutes): ");
            int newDuration = scn.nextInt();

            System.out.print("Enter a new capacity: ");
            int newCapacity = scn.nextInt();

            System.out.print("Enter a new coach ID: ");
            int newCoachId = scn.nextInt();
            scn.nextLine();

            LocalDateTime newDateTime = parseDateTime(dateTimeStr);
            Workout updatedWorkout = new Workout(id, newName, newDateTime, newDuration, newCapacity, newCoachId);
            service.update(id, updatedWorkout);
            System.out.println("The workout has been updated: " + newName);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use: dd.mm.yyyy hh:mm");
        }
    }

    @Override
    public String getCommandName() {
        return "Обновить занятие";
    }
}