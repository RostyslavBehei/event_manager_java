package org.example;

import org.example.model.DatabaseHelper;
import org.example.model.Event;
import org.example.service.EventManagerImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventManagerImpl eventManager = new EventManagerImpl();

    public static void main(String[] args) {
        DatabaseHelper.createTable();
        boolean quit = false;

        while (!quit) {
            System.out.println("\n\uD83D\uDCC5 Event Manager Menu:"
                    + "\n1. ➕ Add event"
                    + "\n2. \uD83D\uDCCB View all events"
                    + "\n3. \uD83D\uDD0D Find events by name"
                    + "\n4. \uD83D\uDD0D Find events between dates"
                    + "\n5. \uD83D\uDEAE Remove all events"
                    + "\n6. \uD83D\uDDD1 Delete event by ID"
                    + "\n7. \uD83D\uDDD1 Delete event by name"
                    + "\n0. \uD83D\uDD1A Quit");

            System.out.print("\n\uD83D\uDC4B Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: addEvent(); break;
                case 2: getEvents(); break;
                case 3: getEventByName(); break;
                case 4: getEventBetween(); break;
                case 5: removeAllEvents(); break;
                case 6: removeEventById(); break;
                case 7: removeEventByName(); break;
                case 0: quit = true; break;
                default: System.out.println("Invalid choice!");
            }
        }
        System.out.println("\n\uD83D\uDC4B Goodbye!");
    }

    private static void addEvent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        scanner.nextLine();
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter start date (dd.MM.yyyy): ");
        LocalDate start = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.print("Enter end date (dd.MM.yyyy): ");
        LocalDate end = LocalDate.parse(scanner.nextLine(), formatter);
        eventManager.addEvent(new Event(name, start.atStartOfDay(), end.atStartOfDay()));
    }


    private static void getEvents() {
        eventManager.getEvents().forEach(System.out::println);
    }

    private static void getEventByName() {
        scanner.nextLine();
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        eventManager.getEventByName(name).forEach(System.out::println);
    }

    private static void getEventBetween() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        scanner.nextLine();
        System.out.print("Enter start date (dd.MM.yyyy): ");
        LocalDate start = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.print("Enter end date (dd.MM.yyyy): ");
        LocalDate end = LocalDate.parse(scanner.nextLine(), formatter);
        eventManager.getEventBetween(start.atStartOfDay(), end.atStartOfDay()).forEach(System.out::println);
    }
    private static void removeAllEvents() {
        eventManager.removeAllEvents();
    }
    private static void removeEventById() {
        scanner.nextLine();
        System.out.print("Enter event id: ");
        int id = scanner.nextInt();
        eventManager.removeEventById(id);
    }
    private static void removeEventByName() {
        scanner.nextLine();
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        eventManager.removeEventsByName(name);
    }
}