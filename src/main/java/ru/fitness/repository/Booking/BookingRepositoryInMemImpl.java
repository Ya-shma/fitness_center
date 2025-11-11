package ru.fitness.repository.Booking;

import ru.fitness.entities.Booking;
import ru.fitness.repository.Booking.BookingRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BookingRepositoryInMemImpl implements BookingRepository {
    private static BookingRepositoryInMemImpl obj;
    private final Map<Integer, Booking> bookings = new HashMap<>();
    private int currentId = 1;

    private BookingRepositoryInMemImpl() {
    }

    public static BookingRepository getInstance() {
        if (obj == null) {
            obj = new BookingRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public List<Booking> getBookingsByClient(int clientId) {
        return bookings.values().stream()
                .filter(booking -> booking.getClientId() == clientId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookingsByWorkout(int workoutId) {
        return bookings.values().stream()
                .filter(booking -> booking.getWorkoutId() == workoutId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        return bookings.values().stream()
                .filter(booking -> booking.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end) {
        return bookings.values().stream()
                .filter(booking -> !booking.getBookingDate().isBefore(start) &&
                        !booking.getBookingDate().isAfter(end))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getActiveBookings() {
        return getBookingsByStatus("active");
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.isActive()) {
            booking.setStatus("cancelled");
            return true;
        }
        return false;
    }

    @Override
    public boolean completeBooking(int bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking != null && booking.isActive()) {
            booking.setStatus("completed");
            return true;
        }
        return false;
    }

    @Override
    public int getBookingsCountByWorkout(int workoutId) {
        return (int) bookings.values().stream()
                .filter(booking -> booking.getWorkoutId() == workoutId && booking.isActive())
                .count();
    }

    @Override
    public boolean hasActiveBooking(int clientId, int workoutId) {
        return bookings.values().stream()
                .anyMatch(booking -> booking.getClientId() == clientId &&
                        booking.getWorkoutId() == workoutId &&
                        booking.isActive());
    }

    // CRUD методы из Repository interface
    @Override
    public void add(Booking object) {
        if (object.getId() == 0) {
            object.setId(currentId++);
        }
        bookings.put(object.getId(), object);
    }

    @Override
    public void removeAll() {
        bookings.clear();
        currentId = 1;
    }

    @Override
    public void update(int id, Booking newObject) {
        if (bookings.containsKey(id)) {
            newObject.setId(id);
            bookings.put(id, newObject);
        }
    }

    @Override
    public Booking getById(int id) {
        return bookings.get(id);
    }

    @Override
    public List<Booking> getAll() {
        return new ArrayList<>(bookings.values());
    }

    @Override
    public boolean delete(int id) {
        return bookings.remove(id) != null;
    }
}