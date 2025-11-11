package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Booking;
import ru.fitness.entities.Client;
import ru.fitness.entities.Workout;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;
import ru.fitness.service.Client.ClientService;
import ru.fitness.service.Workout.WorkoutService;

import java.util.List;

public class GetAllBookings implements Command {

    @Override
    public void execute() {
        BookingService service = ServiceFactory.getBookingService();
        ClientService clientService = ServiceFactory.getClientService();
        WorkoutService workoutService = ServiceFactory.getWorkoutService();

        List<Booking> bookings = service.getAll();

        System.out.println("\n--- ALL BOOKINGS ---");
        if (bookings.isEmpty()) {
            System.out.println("Bookings not found");
        } else {
            for (Booking booking : bookings) {
                Client client = clientService.getById(booking.getClientId());
                Workout workout = workoutService.getById(booking.getWorkoutId());

                String clientName = client != null ? client.getFullName() : "Unknown";
                String workoutName = workout != null ? workout.getName() : "Unknown";

                System.out.printf("ID: %d | Client: %s | Workout: %s | Date: %s | Status: %s%n",
                        booking.getId(),
                        clientName,
                        workoutName,
                        booking.getBookingDate(),
                        booking.getStatus()
                );
            }
        }
        System.out.println("Total: " + bookings.size() + " bookings");
    }

    @Override
    public String getCommandName() {
        return "Get all bookings";
    }
}