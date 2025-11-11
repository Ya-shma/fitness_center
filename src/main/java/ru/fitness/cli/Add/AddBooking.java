package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllClients;
import ru.fitness.entities.Booking;
import ru.fitness.entities.Client;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;
import ru.fitness.service.Client.ClientService;
import ru.fitness.service.Workout.WorkoutService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

            System.out.println("\n--- AVAILABLE CLASSES ---");
            new ru.fitness.cli.Get.GetAllWorkouts().execute();

            System.out.print("Enter the workout ID: ");
            int workoutId = scn.nextInt();
            scn.nextLine();

            System.out.print("Enter the date and time of your reservation (yyyy-mm-dd hh:mm): ");
            String dateTimeStr = scn.nextLine();

            LocalDateTime bookingDate = parseDateTime(dateTimeStr);

            BookingService service = ServiceFactory.getBookingService();
            Booking booking = service.createBooking(clientId, workoutId, bookingDate);

            System.out.println("A reservation has been created! ID: " + booking.getId());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use: yyyy-mm-dd hh:mm");
        }
    }

    @Override
    public String getCommandName() {
        return "Add booking";
    }
}