package ru.fitness.service;

import ru.fitness.repository.Booking.BookingRepository;
import ru.fitness.repository.Booking.BookingRepositoryInMemImpl;
import ru.fitness.repository.Client.ClientRepository;
import ru.fitness.repository.Client.ClientRepositoryInMemImpl;
import ru.fitness.repository.Coach.CoachRepository;
import ru.fitness.repository.Coach.CoachRepositoryInMemImpl;
import ru.fitness.repository.Specialization.SpecializationRepository;
import ru.fitness.repository.Specialization.SpecializationRepositoryInMemImpl;
import ru.fitness.repository.Workout.WorkoutRepository;
import ru.fitness.repository.Workout.WorkoutRepositoryInMemImpl;
import ru.fitness.service.Booking.BookingService;
import ru.fitness.service.Booking.BookingServiceImpl;
import ru.fitness.service.Client.ClientService;
import ru.fitness.service.Client.ClientServiceImpl;
import ru.fitness.service.Coach.CoachService;
import ru.fitness.service.Coach.CoachServiceImpl;
import ru.fitness.service.Specialization.SpecializationService;
import ru.fitness.service.Specialization.SpecializationServiceImpl;
import ru.fitness.service.Workout.WorkoutService;
import ru.fitness.service.Workout.WorkoutServiceImpl;

//public class ServiceFactory {
//    private static CoachService coachService;
//    private static SpecializationService specializationService;
//    private static ClientService clientService;
//    private static WorkoutService workoutService;
//    private static BookingService bookingService;
//
//    public static CoachService getCoachService() {
//        if (coachService == null) {
//            CoachRepository repository = CoachRepositoryInMemImpl.getInstance();
//            coachService = CoachServiceImpl.getInstance(repository);
//        }
//        return coachService;
//    }
//
//    public static SpecializationService getSpecializationService() {
//        if (specializationService == null) {
//            SpecializationRepository repository = SpecializationRepositoryInMemImpl.getInstance();
//            specializationService = SpecializationServiceImpl.getInstance(repository);
//        }
//        return specializationService;
//    }
//
//    public static ClientService getClientService() {
//        if (clientService == null) {
//            ClientRepository repository = ClientRepositoryInMemImpl.getInstance();
//            clientService = ClientServiceImpl.getInstance(repository);
//        }
//        return clientService;
//    }
//
//    public static WorkoutService getWorkoutService() {
//        if (workoutService == null) {
//            WorkoutRepository repository = WorkoutRepositoryInMemImpl.getInstance();
//            workoutService = WorkoutServiceImpl.getInstance(repository);
//        }
//        return workoutService;
//    }
//
//    public static BookingService getBookingService() {
//        if (bookingService == null) {
//            BookingRepository repository = BookingRepositoryInMemImpl.getInstance();
//            bookingService = BookingServiceImpl.getInstance(repository);
//        }
//        return bookingService;
//    }
//}

import ru.fitness.config.DatabaseConnection;
import ru.fitness.repository.*;
import ru.fitness.repository.JDBC.*;

public class ServiceFactory {
    private static CoachService coachService;
    private static SpecializationService specializationService;
    private static ClientService clientService;
    private static WorkoutService workoutService;
    private static BookingService bookingService;

    private static final boolean USE_DATABASE = DatabaseConnection.isDatabaseAvailable();

    static {
        System.out.println(USE_DATABASE ?
                "🗄️  Используется PostgreSQL база данных" :
                "💾 Используется In-Memory хранилище");
    }

    public static CoachService getCoachService() {
        if (coachService == null) {
            CoachRepository repository = USE_DATABASE ?
                    new CoachRepositoryJdbcImpl() :
                    CoachRepositoryInMemImpl.getInstance();
            coachService = CoachServiceImpl.getInstance(repository);
        }
        return coachService;
    }

    public static SpecializationService getSpecializationService() {
        if (specializationService == null) {
            SpecializationRepository repository = USE_DATABASE ?
                    new SpecializationRepositoryJdbcImpl() :
                    SpecializationRepositoryInMemImpl.getInstance();
            specializationService = SpecializationServiceImpl.getInstance(repository);
        }
        return specializationService;
    }

    public static ClientService getClientService() {
        if (clientService == null) {
            ClientRepository repository = USE_DATABASE ?
                    new ClientRepositoryJdbcImpl() :
                    ClientRepositoryInMemImpl.getInstance();
            clientService = ClientServiceImpl.getInstance(repository);
        }
        return clientService;
    }

    public static WorkoutService getWorkoutService() {
        if (workoutService == null) {
            WorkoutRepository repository = USE_DATABASE ?
                    new WorkoutRepositoryJdbcImpl() :
                    WorkoutRepositoryInMemImpl.getInstance();
            workoutService = WorkoutServiceImpl.getInstance(repository);
        }
        return workoutService;
    }

    public static BookingService getBookingService() {
        if (bookingService == null) {
            BookingRepository repository = USE_DATABASE ?
                    new BookingRepositoryJdbcImpl() :
                    BookingRepositoryInMemImpl.getInstance();
            bookingService = BookingServiceImpl.getInstance(repository);
        }
        return bookingService;
    }

    public static boolean isUsingDatabase() {
        return USE_DATABASE;
    }
}