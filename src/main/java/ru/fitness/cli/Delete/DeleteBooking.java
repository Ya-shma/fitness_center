package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllBookings;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;

import java.util.Scanner;

public class DeleteBooking implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllBookings().execute();

            System.out.print("Enter the booking ID to delete: ");
            int bookingId = scn.nextInt();
            scn.nextLine();

            BookingService service = ServiceFactory.getBookingService();
            boolean deleted = service.delete(bookingId);

            if (deleted) {
                System.out.println("Booking deleted!");
            } else {
                System.out.println("Booking with an ID " + bookingId + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Delete booking";
    }
}