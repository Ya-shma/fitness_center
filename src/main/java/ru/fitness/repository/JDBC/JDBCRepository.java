package ru.fitness.repository.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ru.fitness.config.DatabaseConnection;

public abstract class JDBCRepository {

    protected Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
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