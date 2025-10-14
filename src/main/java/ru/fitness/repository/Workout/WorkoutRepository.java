package ru.fitness.repository.Workout;

import ru.fitness.entities.Workout;
import ru.fitness.repository.Repository;

import java.util.List;

public interface WorkoutRepository extends Repository<Workout> {
    List<Workout> getWorkoutsByCoach(int coachId);

    List<Workout> getWorkoutsByName(String name);
}