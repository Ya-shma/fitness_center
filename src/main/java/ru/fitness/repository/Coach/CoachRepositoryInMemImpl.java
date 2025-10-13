package ru.fitness.repository.Coach;

import ru.fitness.entities.Coach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoachRepositoryInMemImpl implements CoachRepository {

    private static CoachRepositoryInMemImpl obj;

    private final Map<Integer, Coach> coaches = new HashMap<>();
    private int currentId = 1;

    private CoachRepositoryInMemImpl() {

    }

    public static CoachRepository getInstance() {
        if (obj == null) {
            obj = new CoachRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public Coach getCoachByName(String name) {
        for (Coach coach : coaches.values()) {
            if (coach.getFullName().equalsIgnoreCase(name)) {
                return coach;
            }
        }
        return null;
    }

    @Override
    public List<Coach> getCoachBySpecialization(int specializationId) {
        List<Coach> result = new ArrayList<>();
        for (Coach coach : coaches.values()) {
            if (coach.getSpecialization().getId() == specializationId) {
                result.add(coach);
            }
        }
        return result;
    }

    @Override
    public void add(Coach object) {
        if (object.getId() == 0) {
            object.setId(currentId++);
        }
        coaches.put(object.getId(), object);
    }

    @Override
    public void removeAll() {
        coaches.clear();
        currentId = 1;
    }

    @Override
    public void update(int id, Coach newObject) {
        if (coaches.containsKey(id)) {
            newObject.setId(id);
            coaches.put(id, newObject);
        }
    }

    @Override
    public Coach getById(int id) {
        return coaches.get(id);
    }

    @Override
    public List<Coach> getAll() {
        return new ArrayList<>(coaches.values());
    }

    @Override
    public boolean delete(int id) {
        return coaches.remove(id) != null;
    }
}
