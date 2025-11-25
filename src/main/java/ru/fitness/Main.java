package ru.fitness;

import ru.fitness.cli.Menu;
import ru.fitness.config.DatabaseInitializer;
import ru.fitness.config.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        Menu.run();
        DatabaseConnection.closeConnection();
    }
}