package ru.fitness.service;

import java.util.List;

public interface Service<T> {
    void create(T object);

    void update(int id, T newObject);

    T getById(int id);

    List<T> getAll();

    boolean delete(int id);
}