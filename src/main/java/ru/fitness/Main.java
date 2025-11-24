package ru.fitness;

import ru.fitness.cli.Menu;
import ru.fitness.config.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        Menu.run();
        ru.fitness.config.DatabaseConnection.closeConnection();
    }
}