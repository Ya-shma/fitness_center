package ru.fitness.cli;

import ru.fitness.cli.Get.GetActiveBookings;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Booking.BookingService;

import java.util.Scanner;

public class CompleteBooking implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetActiveBookings().execute();

            System.out.print("Enter the booking ID to complete: ");
            int bookingId = scn.nextInt();
            scn.nextLine();

            BookingService service = ServiceFactory.getBookingService();
            boolean completed = service.completeBooking(bookingId);

            if (completed) {
                System.out.println("Booking completed!");
            } else {
                System.out.println("Couldn't complete booking");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Complete booking";
    }
}