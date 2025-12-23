package ru.fitness.config;

import java.sql.*;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        if (!DatabaseConnection.isDatabaseAvailable()) {
            System.out.println("The database is not available. Check the connection settings.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            createTables(statement);

            System.out.println("The database has been initialized successfully");

        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private static void createTables(Statement stmt) throws SQLException {
        String createSpecializationsTable = """
                CREATE TABLE IF NOT EXISTS specializations (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL UNIQUE
                )
                """;
        stmt.execute(createSpecializationsTable);

        String createCoachesTable = """
                CREATE TABLE IF NOT EXISTS coaches (
                    id SERIAL PRIMARY KEY,
                    full_name VARCHAR(200) NOT NULL,
                    specialization_id INTEGER NOT NULL,
                    FOREIGN KEY (specialization_id) REFERENCES specializations(id) ON DELETE RESTRICT
                )
                """;
        stmt.execute(createCoachesTable);

        String createClientsTable = """
                CREATE TABLE IF NOT EXISTS clients (
                    id SERIAL PRIMARY KEY,
                    full_name VARCHAR(200) NOT NULL,
                    phone VARCHAR(20) NOT NULL UNIQUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
        stmt.execute(createClientsTable);

        String createWorkoutsTable = """
                CREATE TABLE IF NOT EXISTS workouts (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(200) NOT NULL,
                    date_time TIMESTAMP NOT NULL,
                    duration_minutes INTEGER NOT NULL CHECK (duration_minutes > 0),
                    max_capacity INTEGER NOT NULL CHECK (max_capacity > 0),
                    coach_id INTEGER NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE RESTRICT
                )
                """;
        stmt.execute(createWorkoutsTable);

        String createBookingsTable = """
                CREATE TABLE IF NOT EXISTS bookings (
                    id SERIAL PRIMARY KEY,
                    client_id INTEGER NOT NULL,
                    workout_id INTEGER NOT NULL,
                    booking_date TIMESTAMP NOT NULL,
                    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'cancelled', 'completed')),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
                    FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE,
                    UNIQUE (client_id, workout_id)
                )
                """;
        stmt.execute(createBookingsTable);
    }

    public static void clearDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS bookings");
            statement.execute("DROP TABLE IF EXISTS workouts");
            statement.execute("DROP TABLE IF EXISTS clients");
            statement.execute("DROP TABLE IF EXISTS coaches");
            statement.execute("DROP TABLE IF EXISTS specializations");

            System.out.println("База данных очищена");

        } catch (SQLException e) {
            System.err.println("Ошибка очистки базы данных: " + e.getMessage());
        }
    }
}