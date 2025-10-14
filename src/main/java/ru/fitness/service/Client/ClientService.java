package ru.fitness.service.Client;

import ru.fitness.entities.Client;
import ru.fitness.service.Service;

import java.util.List;

public interface ClientService extends Service<Client> {
    Client getClientByPhoneNumber(String phoneNumber);

    List<Client> getClientsByName(String name);

    boolean validatePhoneNumber(String phoneNumber);

    int getClientBookingsCount(int clientId);
}