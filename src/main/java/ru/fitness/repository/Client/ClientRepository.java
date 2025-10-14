package ru.fitness.repository.Client;

import ru.fitness.entities.Client;
import ru.fitness.repository.Repository;

import java.util.List;

public interface ClientRepository extends Repository<Client> {
    Client getClientByPhoneNumber(String phoneNumber);

    List<Client> getClientsByName(String name);
}