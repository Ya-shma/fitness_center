package ru.fitness.cli;

public interface Command {
    void execute();

    String getCommandName();
}