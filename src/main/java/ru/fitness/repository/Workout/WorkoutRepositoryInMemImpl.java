package ru.fitness.repository.Workout;

import ru.fitness.entities.Workout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutRepositoryInMemImpl implements WorkoutRepository {

    private static WorkoutRepositoryInMemImpl obj;

    private final Map<Integer, Workout> workouts = new HashMap<>();
    private int currentId = 1;

    private WorkoutRepositoryInMemImpl() {

    }

    public static WorkoutRepository getInstance() {
        if (obj == null) {
            obj = new WorkoutRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public List<Workout> getWorkoutsByCoach(int coachId) {
        List<Workout> result = new ArrayList<>();
        for (Workout workout : workouts.values()) {
            if (workout.getCoachId() == coachId) {
                result.add(workout);
            }
        }
        return result;
    }

    @Override
    public List<Workout> getWorkoutsByName(String name) {
        List<Workout> result = new ArrayList<>();
        for (Workout workout : workouts.values()) {
            if (workout.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(workout);
            }
        }
        return result;
    }

    @Override
    public void add(Workout object) {
        if (object.getId() == 0) {
            object.setId(currentId++);
        }
        workouts.put(object.getId(), object);
    }

    @Override
    public void removeAll() {
        workouts.clear();
        currentId = 1;
    }

    @Override
    public void update(int id, Workout newObject) {
        if (workouts.containsKey(id)) {
            newObject.setId(id);
            workouts.put(id, newObject);
        }
    }

    @Override
    public Workout getById(int id) {
        return workouts.get(id);
    }

    @Override
    public List<Workout> getAll() {
        return new ArrayList<>(workouts.values());
    }

    @Override
    public boolean delete(int id) {
        return workouts.remove(id) != null;
    }
}