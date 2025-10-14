package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class UpdateSpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ВСЕ СПЕЦИАЛИЗАЦИИ ---");
            SpecializationService service = ServiceFactory.getSpecializationService();
            var specializations = service.getAll();

            if (specializations.isEmpty()) {
                System.out.println("Специализации не найдены");
                return;
            }

            for (var spec : specializations) {
                System.out.println(spec.getId() + ". " + spec.getName());
            }

            System.out.print("\nВведите ID специализации для обновления: ");
            int id = scn.nextInt();
            scn.nextLine();

            Specialization existing = service.getById(id);
            if (existing == null) {
                System.out.println("Специализация с ID " + id + " не найдена");
                return;
            }

            System.out.print("Введите новое название специализации: ");
            String newName = scn.nextLine();

            Specialization updatedSpec = new Specialization(id, newName);
            service.update(id, updatedSpec);
            System.out.println("Специализация обновлена: " + newName);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Обновить специализацию";
    }
}