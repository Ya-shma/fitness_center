package ru.fitness.service;

import ru.fitness.repository.Client.ClientRepository;
import ru.fitness.repository.Client.ClientRepositoryInMemImpl;
import ru.fitness.repository.Coach.CoachRepository;
import ru.fitness.repository.Coach.CoachRepositoryInMemImpl;
import ru.fitness.repository.Specialization.SpecializationRepository;
import ru.fitness.repository.Specialization.SpecializationRepositoryInMemImpl;
import ru.fitness.repository.Workout.WorkoutRepository;
import ru.fitness.repository.Workout.WorkoutRepositoryInMemImpl;
import ru.fitness.service.Client.ClientService;
import ru.fitness.service.Client.ClientServiceImpl;
import ru.fitness.service.Coach.CoachService;
import ru.fitness.service.Coach.CoachServiceImpl;
import ru.fitness.service.Specialization.SpecializationService;
import ru.fitness.service.Specialization.SpecializationServiceImpl;
import ru.fitness.service.Workout.WorkoutService;
import ru.fitness.service.Workout.WorkoutServiceImpl;

public class ServiceFactory {
    private static CoachService coachService;
    private static SpecializationService specializationService;
    private static ClientService clientService;
    private static WorkoutService workoutService;

    public static CoachService getCoachService() {
        if (coachService == null) {
            CoachRepository repository = CoachRepositoryInMemImpl.getInstance();
            coachService = CoachServiceImpl.getInstance(repository);
        }
        return coachService;
    }

    public static SpecializationService getSpecializationService() {
        if (specializationService == null) {
            SpecializationRepository repository = SpecializationRepositoryInMemImpl.getInstance();
            specializationService = SpecializationServiceImpl.getInstance(repository);
        }
        return specializationService;
    }

    public static ClientService getClientService() {
        if (clientService == null) {
            ClientRepository repository = ClientRepositoryInMemImpl.getInstance();
            clientService = ClientServiceImpl.getInstance(repository);
        }
        return clientService;
    }

    public static WorkoutService getWorkoutService() {
        if (workoutService == null) {
            WorkoutRepository repository = WorkoutRepositoryInMemImpl.getInstance();
            workoutService = WorkoutServiceImpl.getInstance(repository);
        }
        return workoutService;
    }
}