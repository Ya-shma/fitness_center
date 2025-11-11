package ru.fitness.repository.Booking;

import ru.fitness.entities.Booking;
import ru.fitness.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends Repository<Booking> {
    List<Booking> getBookingsByClient(int clientId);

    List<Booking> getBookingsByWorkout(int workoutId);

    List<Booking> getBookingsByStatus(String status);

    List<Booking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end);

    List<Booking> getActiveBookings();

    boolean cancelBooking(int bookingId);

    boolean completeBooking(int bookingId);

    int getBookingsCountByWorkout(int workoutId);

    boolean hasActiveBooking(int clientId, int workoutId);

    void removeAll();
}