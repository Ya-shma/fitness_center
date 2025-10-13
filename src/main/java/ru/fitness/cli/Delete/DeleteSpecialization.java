package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllSpecializations;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class DeleteSpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllSpecializations().execute();

            System.out.print("Введите ID специализации для удаления: ");
            int id = scn.nextInt();
            scn.nextLine();

            SpecializationService service = ServiceFactory.getSpecializationService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Специализация с ID " + id + " удалена!");
            } else {
                System.out.println("Специализация с ID " + id + " не найдена");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Удалить специализацию по ID";
    }
}