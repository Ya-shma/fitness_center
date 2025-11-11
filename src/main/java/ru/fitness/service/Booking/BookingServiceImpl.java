package ru.fitness.service.Booking;

import ru.fitness.entities.Booking;
import ru.fitness.entities.Client;
import ru.fitness.entities.Workout;
import ru.fitness.repository.Booking.BookingRepository;
import ru.fitness.service.Client.ClientService;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Workout.WorkoutService;

import java.time.LocalDateTime;
import java.util.List;

public class BookingServiceImpl implements BookingService {
    private static BookingServiceImpl obj;
    private final BookingRepository repository;
    private final ClientService clientService;
    private final WorkoutService workoutService;

    private BookingServiceImpl(BookingRepository repository) {
        this.repository = repository;
        this.clientService = ServiceFactory.getClientService();
        this.workoutService = ServiceFactory.getWorkoutService();
    }

    public static BookingService getInstance(BookingRepository repository) {
        if (obj == null) {
            obj = new BookingServiceImpl(repository);
        }
        return obj;
    }

    @Override
    public Booking createBooking(int clientId, int workoutId, LocalDateTime bookingDate) {
        Client client = clientService.getById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client with ID " + clientId + " wasn't found");
        }

        Workout workout = workoutService.getById(workoutId);
        if (workout == null) {
            throw new IllegalArgumentException("Workout with ID " + workoutId + " wasn't found");
        }

        if (isWorkoutFull(workoutId)) {
            throw new IllegalArgumentException("The workout is fully booked. There are no available places");
        }

        if (hasClientActiveBooking(clientId, workoutId)) {
            throw new IllegalArgumentException("The client already has an active booking for this activity");
        }

        // Проверяем что бронирование не в прошлом
        if (bookingDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You can't create a booking in the past");
        }

        Booking booking = new Booking(0, clientId, workoutId, bookingDate, "active");
        repository.add(booking);
        return booking;
    }

    @Override
    public List<Booking> getBookingsByClient(int clientId) {
        return repository.getBookingsByClient(clientId);
    }

    @Override
    public List<Booking> getBookingsByWorkout(int workoutId) {
        return repository.getBookingsByWorkout(workoutId);
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        return repository.getBookingsByStatus(status);
    }

    @Override
    public List<Booking> getBookingsByDateRange(LocalDateTime start, LocalDateTime end) {
        return repository.getBookingsByDateRange(start, end);
    }

    @Override
    public List<Booking> getActiveBookings() {
        return repository.getActiveBookings();
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        Booking booking = repository.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " wasn't found");
        }

        if (!booking.isActive()) {
            throw new IllegalArgumentException("You can't cancel an already canceled or completed booking");
        }

        return repository.cancelBooking(bookingId);
    }

    @Override
    public boolean completeBooking(int bookingId) {
        Booking booking = repository.getById(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " wasn't found");
        }

        if (!booking.isActive()) {
            throw new IllegalArgumentException("You can't complete a booking that has already been canceled or completed");
        }

        return repository.completeBooking(bookingId);
    }

    @Override
    public int getActiveBookingsCountByWorkout(int workoutId) {
        return repository.getBookingsCountByWorkout(workoutId);
    }

    @Override
    public boolean hasClientActiveBooking(int clientId, int workoutId) {
        return repository.hasActiveBooking(clientId, workoutId);
    }

    @Override
    public boolean isWorkoutFull(int workoutId) {
        Workout workout = workoutService.getById(workoutId);
        if (workout == null) return true;

        int activeBookings = getActiveBookingsCountByWorkout(workoutId);
        return activeBookings >= workout.getMaxCapacity();
    }

    @Override
    public List<Booking> getTodayBookings() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return getBookingsByDateRange(startOfDay, endOfDay);
    }

    @Override
    public List<Booking> getUpcomingBookings() {
        return getActiveBookings().stream()
                .filter(booking -> booking.getBookingDate().isAfter(LocalDateTime.now()))
                .collect(java.util.stream.Collectors.toList());
    }

    // CRUD методы из Service interface
    @Override
    public void create(Booking object) {
        // Используем специализированный метод вместо прямого создания
        createBooking(object.getClientId(), object.getWorkoutId(), object.getBookingDate());
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    @Override
    public void update(int id, Booking newObject) {
        Booking existing = repository.getById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Booking with ID " + id + " wasn't found");
        }

//        // Проверяем бизнес-правила при обновлении
//        if (!existing.getClientId().equals(newObject.getClientId()) ||
//                !existing.getWorkoutId().equals(newObject.getWorkoutId())) {
//            throw new IllegalArgumentException("Нельзя изменять клиента или занятие в существующем бронировании");
//        }

        repository.update(id, newObject);
    }

    @Override
    public Booking getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Booking> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(int id) {
        Booking booking = repository.getById(id);
        if (booking != null && booking.isActive()) {
            throw new IllegalArgumentException("You can't delete an active booking. Please cancel it first.");
        }
        return repository.delete(id);
    }
}