package ru.fitness.cli.Delete;

import ru.fitness.cli.Command;
import ru.fitness.cli.Get.GetAllCoaches;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Coach.CoachService;

import java.util.Scanner;

public class DeleteCoach implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        try {
            new GetAllCoaches().execute();

            System.out.print("Enter the coach's ID to delete: ");
            int id = scn.nextInt();
            scn.nextLine();

            CoachService service = ServiceFactory.getCoachService();
            boolean deleted = service.delete(id);

            if (deleted) {
                System.out.println("Coach with ID " + id + " has been deleted!");
            } else {
                System.out.println("Coach with ID " + id + " wasn't found");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            scn.nextLine();
        }
    }

    @Override
    public String getCommandName() {
        return "Удалить тренера по ID";
    }
}