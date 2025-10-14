package ru.fitness.service.Workout;

import ru.fitness.entities.Workout;
import ru.fitness.service.Service;

import java.util.List;

public interface WorkoutService extends Service<Workout> {
    List<Workout> getWorkoutsByCoach(int coachId);

    List<Workout> getWorkoutsByName(String name);

    List<Workout> getAvailableWorkouts();

    boolean checkWorkoutCapacity(int workoutId);

    int getRegisteredClientsCount(int workoutId);
}