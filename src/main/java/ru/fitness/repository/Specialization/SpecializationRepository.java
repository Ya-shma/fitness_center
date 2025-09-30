package ru.fitness.repository.Specialization;

import ru.fitness.entities.Specialization;
import ru.fitness.repository.Repository;

public interface SpecializationRepository extends Repository<Specialization, Integer> {
    Specialization getSpecializationByName(String name);
}