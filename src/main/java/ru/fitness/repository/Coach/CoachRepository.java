package ru.fitness.repository.Coach;

import ru.fitness.entities.Coach;
import ru.fitness.repository.Repository;

import java.util.List;

public interface CoachRepository extends Repository<Coach> {
    Coach getCoachByName(String name);

    List<Coach> getCoachBySpecialization(int specializationId);
}
