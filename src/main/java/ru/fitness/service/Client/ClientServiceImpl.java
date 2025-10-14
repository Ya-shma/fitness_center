package ru.fitness.service.Client;

import ru.fitness.entities.Client;
import ru.fitness.repository.Client.ClientRepository;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private static ClientServiceImpl obj;
    private final ClientRepository repository;

    private ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    public static ClientService getInstance(ClientRepository repository) {
        if (obj == null) {
            obj = new ClientServiceImpl(repository);
        }
        return obj;
    }

    @Override
    public Client getClientByPhoneNumber(String phoneNumber) {
        return repository.getClientByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Client> getClientsByName(String name) {
        return repository.getClientsByName(name);
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{10,15}$");
    }

    @Override
    public int getClientBookingsCount(int clientId) {
        return 0;
    }

    @Override
    public void create(Client object) {
        if (object.getFullName() == null || object.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client's name can't be empty");
        }

        if (!validatePhoneNumber(object.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        Client existing = repository.getClientByPhoneNumber(object.getPhoneNumber());
        if (existing != null) {
            throw new IllegalArgumentException("Client with this phone already exists");
        }

        repository.add(object);
    }

    @Override
    public void update(int id, Client newObject) {
        if (newObject.getFullName() == null || newObject.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Client's name can't be empty");
        }

        if (!validatePhoneNumber(newObject.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        repository.update(id, newObject);
    }

    @Override
    public Client getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Client> getAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(int id) {
        Client client = repository.getById(id);
        if (client == null) {
            return false;
        }
        return repository.delete(id);
    }
}