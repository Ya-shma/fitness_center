package ru.fitness.service.Booking;

import ru.fitness.entities.Booking;
import ru.fitness.service.Service;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService extends Service<Booking> {
    Booking createBooking(int clientId, int workoutId, LocalDateTime bookingDate);

    List<Booking> getBookingsByClient(int clientId);

    List<Booking> getBookingsByWorkout(int workoutId);

    List<Booking> getBookingsByStatus(String status);

    List<Booking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end);

    List<Booking> getActiveBookings();

    boolean cancelBooking(int bookingId);

    boolean completeBooking(int bookingId);

    int getActiveBookingsCountByWorkout(int workoutId);

    boolean hasClientActiveBooking(int clientId, int workoutId);

    boolean isWorkoutFull(int workoutId);

    List<Booking> getTodayBookings();

    List<Booking> getUpcomingBookings();

    void removeAll();
}