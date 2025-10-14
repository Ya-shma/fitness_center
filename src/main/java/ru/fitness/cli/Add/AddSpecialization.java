package ru.fitness.cli.Add;

import ru.fitness.cli.Command;
import ru.fitness.entities.Specialization;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Specialization.SpecializationService;

import java.util.Scanner;

public class AddSpecialization implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Enter the name of the specialization: ");
        String name = scn.nextLine();

        try {
            Specialization spec = new Specialization(0, name);
            SpecializationService service = ServiceFactory.getSpecializationService();
            service.create(spec);

            System.out.println("Specialization '" + name + "' was added!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Add specialization";
    }
}