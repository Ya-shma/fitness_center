package ru.fitness.cli;

import ru.fitness.cli.Get.GetActiveBookings;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;

import java.util.Scanner;

public class CancelBooking implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetActiveBookings().execute();

            System.out.print("Enter the booking ID to cancel: ");
            int bookingId = scn.nextInt();
            scn.nextLine();

            BookingService service = ServiceFactory.getBookingService();
            boolean cancelled = service.cancelBooking(bookingId);

            if (cancelled) {
                System.out.println("Booking cancelled!");
            } else {
                System.out.println("Couldn't cancel booking");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Cancel booking";
    }
}