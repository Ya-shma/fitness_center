package ru.fitness.cli.Update;

import ru.fitness.cli.Command;
import ru.fitness.entities.Coach;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class UpdateCoach implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            System.out.println("\n--- ALL COACHES ---");
            CoachService coachService = ServiceFactory.getCoachService();
            var coaches = coachService.getAll();

            if (coaches.isEmpty()) {
                System.out.println("Тренеры не найдены");
                return;
            }

            for (var coach : coaches) {
                String specName = coach.getSpecialization() != null ?
                        coach.getSpecialization().getName() : "не указана";
                System.out.println(coach.getId() + ". " + coach.getFullName() +
                        " (Specialization: " + specName + ")");
            }

            System.out.print("\nВведите ID тренера для обновления: ");
            int id = scn.nextInt();
            scn.nextLine();

            Coach existing = coachService.getById(id);
            if (existing == null) {
                System.out.println("Тренер с ID " + id + " не найдена");
                return;
            }

            System.out.print("Введите новое ФИО тренера: ");
            String newName = scn.nextLine();

            Specialization specialization = selectSpecialization();
            if (specialization == null) {
                return;
            }

            Coach updatedCoach = new Coach(id, newName, specialization);

            coachService.update(id, updatedCoach);

            System.out.println("✓ Тренер обновлен: " + newName);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    private Specialization selectSpecialization() {
        try {
            SpecializationService specService = ServiceFactory.getSpecializationService();
            var specializations = specService.getAll();

            System.out.println("\n--- ДОСТУПНЫЕ СПЕЦИАЛИЗАЦИИ ---");
            if (specializations.isEmpty()) {
                System.out.println("Специализации не найдены");
                return null;
            }

            for (var spec : specializations) {
                System.out.println(spec.getId() + ". " + spec.getName());
            }

            System.out.print("\nВведите ID специализации: ");
            int specId = scn.nextInt();
            scn.nextLine();

            Specialization specialization = specService.getById(specId);
            if (specialization == null) {
                System.out.println("Ошибка: Специализация с ID " + specId + " не существует!");
                return null;
            }

            return specialization;

        } catch (Exception e) {
            System.out.println("Ошибка при выборе специализации: " + e.getMessage());
            scn.nextLine();
            return null;
        }
    }

    @Override
    public String getCommandName() {
        return "Update coach";
    }
}