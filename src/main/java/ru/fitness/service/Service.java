package ru.fitness.service;

import java.util.List;

public interface Service<T, Integer> {
    void create(T object);

    void removeAll();

    void update(int id, T newObject);

    T getById(int id);

    List<T> getAll();
}