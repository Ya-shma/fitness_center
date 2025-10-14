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
            System.out.print("Enter the name of the workout: ");
            String name = scn.nextLine();

            System.out.print("Enter the date and time (dd.mm.yyyy hh:mm): ");
            String dateTimeStr = scn.nextLine();

            System.out.print("Enter the duration (minutes): ");
            int duration = scn.nextInt();

            System.out.print("Enter the capacity: ");
            int capacity = scn.nextInt();

            System.out.print("Enter the coach's ID: ");
            int coachId = scn.nextInt();
            scn.nextLine();

            LocalDateTime dateTime = parseDateTime(dateTimeStr);
            Workout workout = new Workout(0, name, dateTime, duration, capacity, coachId);

            WorkoutService service = ServiceFactory.getWorkoutService();
            service.create(workout);

            System.out.println("Workout '" + name + "' was added!");
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
        return "Add workout";
    }
}