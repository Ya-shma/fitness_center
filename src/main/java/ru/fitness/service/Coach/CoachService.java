package ru.fitness.service.Coach;

import ru.fitness.entities.Coach;
import ru.fitness.service.Service;

import java.util.List;

public interface CoachService extends Service<Coach, Integer> {
    Coach getCoachByName(String name);

    List<Coach> getCoachBySpecialization(int specializationId);

    boolean validateCoachAge(int age);
}
