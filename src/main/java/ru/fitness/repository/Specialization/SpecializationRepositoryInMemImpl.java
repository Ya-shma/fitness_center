package ru.fitness.repository.Specialization;

import ru.fitness.entities.Specialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecializationRepositoryInMemImpl implements SpecializationRepository {

    private static SpecializationRepositoryInMemImpl obj;

    private final Map<Integer, Specialization> specializations = new HashMap<>();
    private int currentId = 1;

    private SpecializationRepositoryInMemImpl() {

    }

    public static SpecializationRepository getInstance() {
        if (obj == null) {
            obj = new SpecializationRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public Specialization getSpecializationByName(String name) {
        for (Specialization spec : specializations.values()) {
            if (spec.getName().equalsIgnoreCase(name)) {
                return spec;
            }
        }
        return null;
    }

    @Override
    public void add(Specialization object) {
        if (object.getId() == 0) {
            object.setId(currentId++);
        }
        specializations.put(object.getId(), object);
    }

    @Override
    public void update(int id, Specialization newObject) {
        if (!specializations.containsKey(id)) {
            throw new IllegalArgumentException("Specialization with id " + id + " not found");
        }
        newObject.setId(id);
        specializations.put(id, newObject);
    }

    @Override
    public Specialization getById(int id) {
        return specializations.get(id);
    }

    @Override
    public List<Specialization> getAll() {
        return new ArrayList<>(specializations.values());
    }

    @Override
    public boolean delete(int id) {
        return specializations.remove(id) != null;
    }
}