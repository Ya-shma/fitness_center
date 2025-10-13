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

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private static Scanner scn = new Scanner(System.in);
    private static Command[] commands = new Command[]{
            new GetAllSpecializations(),
            new GetSpecializationById(),
            new AddSpecialization(),
            new DeleteSpecialization(),
            new GetAllCoaches(),
            new GetCoachById(),
            new GetCoachBySpecialization(),
            new AddCoach(),
            new DeleteCoach(),
            new GetAllClients(),
            new GetClientByPhoneNumber(),
            new AddClient(),
            new DeleteClient(),
            new GetAllWorkouts(),
            new GetWorkoutsByCoach(),
            new AddWorkout(),
            new DeleteWorkout()
    };

    public static void run(){
        System.out.println("=== ФИТНЕС-ЦЕНТР ===");
        while (true){
            System.out.println("\n--- ГЛАВНОЕ МЕНЮ ---");
            for (int i = 1; i <= commands.length; i++) {
                System.out.println(i + ". " + commands[i - 1].getCommandName());
            }
            System.out.println("0. Выход");
            System.out.print("Выбор: ");

            int inputCommand = 0;
            try {
                inputCommand = scn.nextInt();
                scn.nextLine();
            } catch (InputMismatchException ime){
                System.out.println("Неверная команда");
                scn.nextLine();
                continue;
            }

            if(inputCommand == 0){
                System.out.println("Завершение работы...");
                return;
            }

            if(inputCommand > commands.length){
                System.out.println("Неверная команда");
                continue;
            }

            commands[inputCommand - 1].execute();
        }
    }
}