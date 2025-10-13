package ru.fitness.repository.Client;

import ru.fitness.entities.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRepositoryInMemImpl implements ClientRepository {

    private static ClientRepositoryInMemImpl obj;

    private final Map<Integer, Client> clients = new HashMap<>();
    private int currentId = 1;

    private ClientRepositoryInMemImpl() {

    }

    public static ClientRepository getInstance() {
        if (obj == null) {
            obj = new ClientRepositoryInMemImpl();
        }
        return obj;
    }

    @Override
    public Client getClientByPhoneNumber(String phoneNumber) {
        for (Client client : clients.values()) {
            if (client.getPhoneNumber().equals(phoneNumber)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public List<Client> getClientsByName(String name) {
        List<Client> result = new ArrayList<>();
        for (Client client : clients.values()) {
            if (client.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(client);
            }
        }
        return result;
    }

    @Override
    public void add(Client object) {
        if (object.getId() == 0) {
            object.setId(currentId++);
        }
        clients.put(object.getId(), object);
    }

    @Override
    public void removeAll() {
        clients.clear();
        currentId = 1;
    }

    @Override
    public void update(int id, Client newObject) {
        if (clients.containsKey(id)) {
            newObject.setId(id);
            clients.put(id, newObject);
        }
    }

    @Override
    public Client getById(int id) {
        return clients.get(id);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(clients.values());
    }

    @Override
    public boolean delete(int id) {
        return clients.remove(id) != null;
    }
}