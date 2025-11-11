package ru.fitness.cli;

import ru.fitness.cli.Add.AddClient;
import ru.fitness.cli.Add.AddCoach;
import ru.fitness.cli.Add.AddSpecialization;
import ru.fitness.cli.Add.AddWorkout;
import ru.fitness.cli.Delete.DeleteClient;
import ru.fitness.cli.Delete.DeleteCoach;
import ru.fitness.cli.Delete.DeleteSpecialization;
import ru.fitness.cli.Delete.DeleteWorkout;
import ru.fitness.cli.Get.*;
import ru.fitness.cli.Update.UpdateClient;
import ru.fitness.cli.Update.UpdateCoach;
import ru.fitness.cli.Update.UpdateSpecialization;
import ru.fitness.cli.Update.UpdateWorkout;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static Scanner scn = new Scanner(System.in);
    private static Command[] commands = new Command[]{
            // Специализации
            new GetAllSpecializations(),
            new GetSpecializationById(),
            new AddSpecialization(),
            new UpdateSpecialization(),
            new DeleteSpecialization(),

            // Тренеры
            new GetAllCoaches(),
            new GetCoachById(),
            new GetCoachBySpecialization(),
            new AddCoach(),
            new UpdateCoach(),
            new DeleteCoach(),

            // Клиенты
            new GetAllClients(),
            new GetClientByPhoneNumber(),
            new AddClient(),
            new UpdateClient(),
            new DeleteClient(),

            // Занятия
            new GetAllWorkouts(),
            new GetWorkoutsByCoach(),
            new AddWorkout(),
            new UpdateWorkout(),
            new DeleteWorkout()
    };

    public static void run(){
        System.out.println("=== FITNESS CENTRE ===");
        while (true){
            System.out.println("\n--- MAIN MENU ---");
            for (int i = 1; i <= commands.length; i++) {
                System.out.println(i + ". " + commands[i - 1].getCommandName());
            }
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int inputCommand;
            try {
                inputCommand = scn.nextInt();
                scn.nextLine();
            } catch (InputMismatchException ime){
                System.out.println("Wrong command");
                scn.nextLine();
                continue;
            }

            if(inputCommand == 0){
                System.out.println("End of work...");
                return;
            }

            if(inputCommand > commands.length){
                System.out.println("Wrong command");
                continue;
            }

            commands[inputCommand - 1].execute();
        }
    }
}