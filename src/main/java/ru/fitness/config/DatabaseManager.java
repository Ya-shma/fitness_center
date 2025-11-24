package ru.fitness.config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static Connection connection;

    static {
        try {
            Class.forName(DatabaseConfig.getDriver());
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword()
            );
        }
        return connection;
    }

    public static void initializeDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS specializations (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(100) NOT NULL UNIQUE,
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS coaches (
                    id SERIAL PRIMARY KEY,
                    full_name VARCHAR(200) NOT NULL,
                    specialization_id INTEGER NOT NULL,
                    FOREIGN KEY (specialization_id) REFERENCES specializations(id) ON DELETE RESTRICT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS clients (
                    id SERIAL PRIMARY KEY,
                    full_name VARCHAR(200) NOT NULL,
                    phone VARCHAR(20) NOT NULL UNIQUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            stmt.execute("""
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
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS bookings (
                    id SERIAL PRIMARY KEY,
                    client_id INTEGER NOT NULL,
                    workout_id INTEGER NOT NULL,
                    booking_date TIMESTAMP NOT NULL,
                    status VARCHAR(20) DEFAULT 'active' CHECK (status IN ('active', 'cancelled', 'completed')),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
                    FOREIGN KEY (workout_id) REFERENCES workouts(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("CREATE INDEX IF NOT EXISTS idx_coaches_specialization ON coaches(specialization_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_workouts_coach ON workouts(coach_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_workouts_date ON workouts(date_time)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_bookings_client ON bookings(client_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_bookings_workout ON bookings(workout_id)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings(status)");
            stmt.execute("CREATE INDEX IF NOT EXISTS idx_clients_phone ON clients(phone_number)");

            System.out.println("Database tables created successfully");

        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            throw e;
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeQuery();
    }

    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeUpdate();
    }

    public static int executeInsert(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        stmt.executeUpdate();

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating record failed, no ID obtained.");
            }
        }
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    public static void commitTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
        conn.setAutoCommit(true);
    }

    public static void rollbackTransaction() {
        try {
            Connection conn = getConnection();
            conn.rollback();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println("Error rolling back transaction: " + e.getMessage());
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }

    public static void clearAllTables() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("SET session_replication_role = 'replica'");

            stmt.execute("DELETE FROM bookings");
            stmt.execute("DELETE FROM workouts");
            stmt.execute("DELETE FROM clients");
            stmt.execute("DELETE FROM coaches");
            stmt.execute("DELETE FROM specializations");

            stmt.execute("SET session_replication_role = 'origin'");

            stmt.execute("ALTER SEQUENCE bookings_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE workouts_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE clients_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE coaches_id_seq RESTART WITH 1");
            stmt.execute("ALTER SEQUENCE specializations_id_seq RESTART WITH 1");

        } catch (SQLException e) {
            System.err.println("Error clearing tables: " + e.getMessage());
            throw e;
        }
    }
}