package ru.fitness.config;

import java.util.Properties;

public class DatabaseConfig {
    private static final Properties properties = new Properties();

    static {
        setDefaultProperties();
    }

    private static void setDefaultProperties() {
        properties.setProperty("db.url", "jdbc:postgresql://localhost:5432/fitness_center");
        properties.setProperty("db.username", "postgres");
        properties.setProperty("db.password", "1234");
        properties.setProperty("db.driver", "org.postgresql.Driver");
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUsername() {
        return properties.getProperty("db.username");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static String getDriver() {
        return properties.getProperty("db.driver");
    }
}