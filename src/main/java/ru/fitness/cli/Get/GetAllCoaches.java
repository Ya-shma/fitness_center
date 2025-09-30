package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.List;

public class GetAllCoaches implements Command {
    @Override
    public void execute() {
        CoachService service = ServiceFactory.getCoachService();
        List<Coach> coaches = service.getAll();

        System.out.println("\n--- ВСЕ ТРЕНЕРЫ ---");
        if (coaches.isEmpty()) {
            System.out.println("Тренеры не найдены");
        } else {
            for (Coach coach : coaches) {
                System.out.println(coach.getId() + ". " + coach.getFullName() +
                        " (Специализация: " + coach.getSpecialization().getId() + ")");
            }
        }
        System.out.println("Всего: " + coaches.size() + " тренеров");
    }

    @Override
    public String getCommandName() {
        return "Показать всех тренеров";
    }
}