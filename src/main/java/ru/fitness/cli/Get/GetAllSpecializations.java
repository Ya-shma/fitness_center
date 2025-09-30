package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.List;

public class GetAllSpecializations implements Command {
    @Override
    public void execute() {
        SpecializationService service = ServiceFactory.getSpecializationService();
        List<Specialization> specializations = service.getAll();

        System.out.println("\n--- ВСЕ СПЕЦИАЛИЗАЦИИ ---");
        if (specializations.isEmpty()) {
            System.out.println("Специализации не найдены");
        } else {
            for (Specialization spec : specializations) {
                System.out.println(spec.getId() + ". " + spec.getName());
            }
        }
        System.out.println("Всего: " + specializations.size() + " специализаций");
    }

    @Override
    public String getCommandName() {
        return "Показать все специализации";
    }
}