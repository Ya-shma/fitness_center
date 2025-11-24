package ru.fitness.repository.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Базовый класс для JDBC репозиториев
 * Содержит общую логику работы с соединениями
 */
public abstract class JDBCBaseRepository {

    protected Connection getConnection() throws SQLException {
        return ru.fitness.config.DatabaseConnection.getConnection();
    }

    protected void closeResources(ResultSet rs, PreparedStatement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }

    protected void closeResources(PreparedStatement stmt) {
        closeResources(null, stmt);
    }
}