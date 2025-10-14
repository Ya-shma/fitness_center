package ru.fitness.service.Specialization;

import ru.fitness.entities.Specialization;
import ru.fitness.service.Service;

import java.util.List;

public interface SpecializationService extends Service<Specialization> {
    Specialization getSpecializationByName(String name);

    List<Specialization> getPopularSpecializations(int limit);

    boolean isSpecializationAvailable(String name);
}