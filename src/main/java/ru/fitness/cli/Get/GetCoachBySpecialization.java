package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.List;
import java.util.Scanner;

public class GetCoachBySpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Введите ID специализации: ");
        try {
            int specId = scn.nextInt();
            scn.nextLine();

            CoachService service = ServiceFactory.getCoachService();
            List<Coach> coaches = service.getCoachBySpecialization(specId);

            System.out.println("\n--- ТРЕНЕРЫ ПО СПЕЦИАЛИЗАЦИИ " + specId + " ---");
            if (coaches.isEmpty()) {
                System.out.println("Тренеры не найдены для этой специализации");
            } else {
                for (Coach coach : coaches) {
                    System.out.println(coach.getId() + ". " + coach.getFullName());
                }
            }
            System.out.println("Найдено: " + coaches.size() + " тренеров");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Найти тренеров по специализации";
    }
}