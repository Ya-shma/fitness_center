package ru.fitness.service.Specialization;

import ru.fitness.entities.Specialization;
import ru.fitness.repository.Specialization.SpecializationRepository;

import java.util.List;

public class SpecializationServiceImpl implements SpecializationService {
    private static SpecializationServiceImpl obj;
    private final SpecializationRepository repository;

    private SpecializationServiceImpl(SpecializationRepository repository) {
        this.repository = repository;
    }

    public static SpecializationService getInstance(SpecializationRepository repository) {
        if (obj == null) {
            obj = new SpecializationServiceImpl(repository);
        }
        return obj;
    }

    @Override
    public void update(int id, Specialization newObject) {
        Specialization existing = repository.getById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Specialization with ID " + id + " not found");
        }

        if (newObject.getName() == null || newObject.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization name cannot be empty");
        }

        Specialization sameName = repository.getSpecializationByName(newObject.getName());
        if (sameName != null && sameName.getId() != id) {
            throw new IllegalArgumentException("Specialization with name '" + newObject.getName() + "' already exists");
        }

        newObject.setId(id);
        repository.update(id, newObject);
    }

    @Override
    public Specialization getSpecializationByName(String name) {
        return repository.getSpecializationByName(name);
    }

    @Override
    public boolean isSpecializationAvailable(String name) {
        return false;
    }

    @Override
    public void create(Specialization object) {
        if (object.getName() == null || object.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Specialization name cannot be empty");
        }

        Specialization existing = repository.getSpecializationByName(object.getName());
        if (existing != null) {
            throw new IllegalArgumentException("Specialization with this name already exists");
        }

        repository.add(object);
    }

    @Override
    public Specialization getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Specialization> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(int id) {
        Specialization specialization = repository.getById(id);
        if (specialization == null) {
            return false;
        }
        return repository.delete(id);
    }
}