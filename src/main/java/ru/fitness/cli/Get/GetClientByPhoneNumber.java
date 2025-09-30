package ru.fitness.cli.Get;

import ru.fitness.cli.Command;
import ru.fitness.entities.Client;
import ru.fitness.service.ServiceFactory;
import ru.fitness.service.Client.ClientService;

import java.util.Scanner;

public class GetClientByPhoneNumber implements Command {
    private Scanner scn = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.print("Введите телефон для поиска: ");
        String phoneNumber = scn.nextLine();

        try {
            ClientService service = ServiceFactory.getClientService();
            Client client = service.getClientByPhoneNumber(phoneNumber);

            if (client != null) {
                System.out.println("Найден клиент: " + client.getFullName() + " | ID: " + client.getId());
            } else {
                System.out.println("Клиент с телефоном " + phoneNumber + " не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "Найти клиента по телефону";
    }
}