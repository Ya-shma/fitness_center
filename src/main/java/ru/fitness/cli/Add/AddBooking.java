package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllClients;
import ru.fitness.entities.Booking;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;
import ru.fitness.service.Workout.WorkoutService;

import java.util.Scanner;

public class AddBooking implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllClients().execute();

            System.out.print("Enter the client's ID: ");
            int clientId = scn.nextInt();
            scn.nextLine();

            System.out.println("\n--- AVAILABLE WORKOUTS ---");
            new ru.fitness.cli.Get.GetAllWorkouts().execute();

            System.out.print("Enter the workout ID: ");
            int workoutId = scn.nextInt();
            scn.nextLine();

            WorkoutService workoutService = ServiceFactory.getWorkoutService();
            Workout selectedWorkout = workoutService.getById(workoutId);

            if (selectedWorkout == null) {
                throw new IllegalArgumentException("Workout with ID " + workoutId + " not found");
            }

            System.out.print("\nConfirm booking? (yes/no): ");
            String confirmation = scn.nextLine();

            if (!confirmation.equalsIgnoreCase("yes")) {
                System.out.println("Booking cancelled.");
                return;
            }

            BookingService service = ServiceFactory.getBookingService();
            Booking booking = service.createBooking(clientId, workoutId, selectedWorkout.getDateTime());

            System.out.println("Booking created successfully! ID: " + booking.getId());
            System.out.println("Workout: " + selectedWorkout.getName());
            System.out.println("Date: " + formatDateTime(selectedWorkout.getDateTime()));
            System.out.println("Client ID: " + clientId);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    private String formatDateTime(java.time.LocalDateTime dateTime) {
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return dateTime.format(formatter);
    }

    @Override
    public String getCommandName() {
        return "Add booking";
    }
}