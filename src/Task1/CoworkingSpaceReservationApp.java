package Task1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CoworkingSpaceReservationApp {
    static Scanner scanner = new Scanner(System.in);
    static List<CoworkingSpace> spaces = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();
    static int reservationCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. Remove a coworking space");
            System.out.println("3. View all reservations");
            System.out.println("4. Go back to main menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCoworkingSpace();
                    break;
                case 2:
                    removeCoworkingSpace();
                    break;
                case 3:
                    viewAllReservations();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Go back to main menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    browseSpaces();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewMyReservations();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void addCoworkingSpace() {
        System.out.print("Enter space ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter space type (e.g., Open, Private): ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Map<LocalDate, List<String>> dateTimeSlots = new HashMap<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Enter available dates (format: yyyy-MM-dd). Type 'done' to finish:");
        while (true) {
            String dateInput = scanner.nextLine();
            if ("done".equalsIgnoreCase(dateInput)) break;

            LocalDate date;
            try {
                date = LocalDate.parse(dateInput, dateFormatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Try again.");
                continue;
            }

            System.out.println("Enter time slots for " + date + " (e.g., 9:00-11:00). Type 'done' to finish:");
            List<String> timeSlots = new ArrayList<>();
            while (true) {
                String slot = scanner.nextLine();
                if ("done".equalsIgnoreCase(slot)) break;
                timeSlots.add(slot);
            }
            dateTimeSlots.put(date, timeSlots);
        }

        spaces.add(new CoworkingSpace(id, type, price, dateTimeSlots));
        System.out.println("Coworking space added successfully.");
    }



    public static void removeCoworkingSpace() {
        System.out.print("Enter space ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        spaces.removeIf(space -> space.id == id);
        System.out.println("Coworking space removed successfully.");
    }

    public static void viewAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            reservations.forEach(System.out::println);
        }
    }

    public static void browseSpaces() {
        spaces.stream().filter(space -> space.isAvailable).forEach(System.out::println);
    }

    public static void makeReservation() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter space ID to reserve: ");
        int spaceId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter reservation date (format: yyyy-MM-dd): ");
        String dateInput = scanner.nextLine();

        LocalDate date;
        try {
            date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            System.out.println("Invalid date format. Try again.");
            return;
        }

        System.out.print("Enter desired time slot (e.g., 9:00-11:00): ");
        String timeSlot = scanner.nextLine();

        for (CoworkingSpace space : spaces) {
            if (space.id == spaceId && space.isAvailable && space.availableDateTimeSlots.containsKey(date)) {
                List<String> timeSlots = space.availableDateTimeSlots.get(date);
                if (timeSlots.contains(timeSlot)) {
                    timeSlots.remove(timeSlot);
                    if (timeSlots.isEmpty()) {
                        space.availableDateTimeSlots.remove(date);
                        if (space.availableDateTimeSlots.isEmpty()) {
                            space.isAvailable = false;
                        }
                    }
                    reservations.add(new Reservation(reservationCounter++, spaceId, name, date.toString(), timeSlot));
                    System.out.println("Reservation successful.");
                    return;
                }
            }
        }
        System.out.println("Invalid ID, date, or time slot unavailable.");
    }



    public static void viewMyReservations() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        reservations.stream()
                .filter(reservation -> reservation.customerName.equalsIgnoreCase(name))
                .forEach(System.out::println);
    }

    public static void cancelReservation() {
        System.out.print("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
            if (reservation.reservationId == id) {
                iterator.remove();
                for (CoworkingSpace space : spaces) {
                    if (space.id == reservation.spaceId) {
                        space.isAvailable = true;
                    }
                }
                System.out.println("Reservation cancelled successfully.");
                return;
            }
        }
        System.out.println("Reservation ID not found.");
    }
}
