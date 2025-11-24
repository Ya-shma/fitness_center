package ru.fitness.repository.JDBC;

import ru.fitness.config.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс для JDBC репозиториев
 * Содержит общую логику работы с базой данных
 */
public abstract class JdbcRepository<T> {

    /**
     * Выполнить запрос и преобразовать ResultSet в список объектов
     */
    protected List<T> executeQuery(String sql, ResultSetMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();

        try (PreparedStatement stmt = createPreparedStatement(sql, params);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                results.add(mapper.map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database query failed: " + e.getMessage(), e);
        }

        return results;
    }

    /**
     * Выполнить запрос и вернуть один объект
     */
    protected T executeQueryForObject(String sql, ResultSetMapper<T> mapper, Object... params) {
        List<T> results = executeQuery(sql, mapper, params);
        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Выполнить update запрос (INSERT, UPDATE, DELETE)
     */
    protected int executeUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = createPreparedStatement(sql, params)) {
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database update failed: " + e.getMessage(), e);
        }
    }

    /**
     * Выполнить INSERT и вернуть сгенерированный ID
     */
    protected int executeInsert(String sql, Object... params) {
        try (PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(stmt, params);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

            throw new RuntimeException("Failed to get generated ID");

        } catch (SQLException e) {
            throw new RuntimeException("Database insert failed: " + e.getMessage(), e);
        }
    }

    /**
     * Создать PreparedStatement с параметрами
     */
    private PreparedStatement createPreparedStatement(String sql, Object... params) throws SQLException {
        PreparedStatement stmt = DatabaseManager.getConnection().prepareStatement(sql);
        setParameters(stmt, params);
        return stmt;
    }

    /**
     * Установить параметры в PreparedStatement
     */
    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * Интерфейс для маппинга ResultSet в объекты
     */
    @FunctionalInterface
    protected interface ResultSetMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}