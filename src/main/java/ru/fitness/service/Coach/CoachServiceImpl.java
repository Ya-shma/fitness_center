package ru.fitness.service.Coach;

import ru.fitness.entities.Coach;
import ru.fitness.repository.Coach.CoachRepository;

import java.util.List;

public class CoachServiceImpl implements CoachService {
    private static CoachServiceImpl obj;
    private final CoachRepository repository;

    private CoachServiceImpl(CoachRepository repository) {
        this.repository = repository;
    }

    public static CoachService getInstance(CoachRepository repository) {
        if (obj == null) {
            obj = new CoachServiceImpl(repository);
        }
        return obj;
    }

    @Override
    public Coach getCoachByName(String name) {
        return repository.getCoachByName(name);
    }

    @Override
    public List<Coach> getCoachBySpecialization(int specializationId) {
        return repository.getCoachBySpecialization(specializationId);
    }

    @Override
    public boolean validateCoachAge(int age) {
        return age >= 18 && age <= 70;
    }

    @Override
    public void create(Coach object) {
        if (object.getFullName() == null || object.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Coach name cannot be empty");
        }
        repository.add(object);
    }

    @Override
    public void removeAll() {
        repository.removeAll();
    }

    @Override
    public void update(int id, Coach newObject) {
        if (newObject.getFullName() == null || newObject.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Coach name cannot be empty");
        }
        repository.update(id, newObject);
    }

    @Override
    public Coach getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Coach> getAll() {
        return repository.getAll();
    }
}