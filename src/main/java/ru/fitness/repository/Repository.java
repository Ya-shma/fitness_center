package ru.fitness.repository;

import java.util.List;

public interface Repository<T> {
    void add(T object);

    void update(int id, T newObject);

    T getById(int id);

    List<T> getAll();

    boolean delete(int id);
}