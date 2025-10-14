package ru.fitness.service.Workout;

import ru.fitness.entities.Workout;
import ru.fitness.repository.Workout.WorkoutRepository;

import java.util.List;
import java.util.stream.Collectors;

public class WorkoutServiceImpl implements WorkoutService {

    private static WorkoutServiceImpl obj;
    private final WorkoutRepository repository;

    private WorkoutServiceImpl(WorkoutRepository repository) {
        this.repository = repository;
    }

    public static WorkoutService getInstance(WorkoutRepository repository) {
        if (obj == null) {
            obj = new WorkoutServiceImpl(repository);
        }
        return obj;
    }

    @Override
    public List<Workout> getWorkoutsByCoach(int coachId) {
        return repository.getWorkoutsByCoach(coachId);
    }

    @Override
    public List<Workout> getWorkoutsByName(String name) {
        return repository.getWorkoutsByName(name);
    }

    @Override
    public List<Workout> getAvailableWorkouts() {
        return repository.getAll().stream()
                .filter(workout -> workout.getDateTime().isAfter(java.time.LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkWorkoutCapacity(int workoutId) {
        Workout workout = repository.getById(workoutId);
        if (workout == null) return false;

        int registeredCount = getRegisteredClientsCount(workoutId);
        return registeredCount < workout.getMaxCapacity();
    }

    @Override
    public int getRegisteredClientsCount(int workoutId) {
        //бизнес-логика
        return 0;
    }

    @Override
    public void create(Workout object) {

        if (object.getName() == null || object.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout's name can't be empty");
        }

        if (object.getDateTime().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Workout can't be in the past");
        }

        if (object.getMaxCapacity() <= 0) {
            throw new IllegalArgumentException("Workout's capacity must be positive");
        }

        repository.add(object);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    @Override
    public void update(int id, Workout newObject) {
        if (newObject.getName() == null || newObject.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Workout's name can't be empty");
        }

        if (newObject.getDateTime().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Workout can't be in the past");
        }

        repository.update(id, newObject);
    }

    @Override
    public Workout getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Workout> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(int id) {
        Workout workout = repository.getById(id);
        if (workout == null) {
            return false;
        }
        return repository.delete(id);
    }
}