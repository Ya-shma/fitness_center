package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.Scanner;

public class GetCoachById implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Введите ID тренера: ");
        try {
            int id = scn.nextInt();
            scn.nextLine();

            CoachService service = ServiceFactory.getCoachService();
            Coach coach = service.getById(id);

            if (coach != null) {
                System.out.println("Найден тренер: " + coach.getFullName());
            } else {
                System.out.println("Тренер с ID " + id + " не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Найти тренера по ID";
    }
}