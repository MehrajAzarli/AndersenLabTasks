package Task1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CoworkingSpaceReservationApp {
    private static List<CoworkingSpace> spaces = new ArrayList<>();
    private static List<Booking> bookings = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int bookingCounter = 1;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    customerMenu();
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add a new coworking space");
            System.out.println("2. Remove a coworking space");
            System.out.println("3. View all reservations");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addCoworkingSpace() {
        System.out.print("Enter space ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter space type (e.g., open, private, room): ");
        String type = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        spaces.add(new CoworkingSpace(id, type, price, true));
        System.out.println("Space added successfully.");
    }

    private static void removeCoworkingSpace() {
        System.out.print("Enter space ID to remove: ");
        int id = scanner.nextInt();
        spaces.removeIf(space -> space.getId() == id);
        System.out.println("Space removed successfully.");
    }

    private static void viewAllReservations() {
        if (bookings.isEmpty()) {
            System.out.println("No reservations available.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse available spaces");
            System.out.println("2. Make a reservation");
            System.out.println("3. View my reservations");
            System.out.println("4. Cancel a reservation");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void browseSpaces() {
        for (CoworkingSpace space : spaces) {
            System.out.println(space);
        }
    }

    private static void makeReservation() {
        System.out.print("Enter your name: ");
        scanner.nextLine(); // consume newline
        String name = scanner.nextLine();
        System.out.print("Enter workspace ID to reserve: ");
        int workspaceId = scanner.nextInt();
        System.out.print("Enter date (e.g., YYYY-MM-DD): ");
        scanner.nextLine(); // consume newline
        String date = scanner.nextLine();
        System.out.print("Enter time (e.g., 10:00-12:00): ");
        String time = scanner.nextLine();

        for (CoworkingSpace space : spaces) {
            if (space.getId() == workspaceId && space.isAvailable()) {
                space.setAvailable(false);
                bookings.add(new Booking(bookingCounter++, name, workspaceId, date, time));
                System.out.println("Reservation successful.");
                return;
            }
        }
        System.out.println("Workspace not available or invalid ID.");
    }

    private static void viewMyReservations() {
        System.out.print("Enter your name: ");
        scanner.nextLine(); // consume newline
        String name = scanner.nextLine();

        boolean found = false;
        for (Booking booking : bookings) {
            if (booking.toString().contains(name)) {
                System.out.println(booking);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No reservations found for your name.");
        }
    }

    private static void cancelReservation() {
        System.out.print("Enter reservation ID to cancel: ");
        int id = scanner.nextInt();

        Iterator<Booking> iterator = bookings.iterator();
        while (iterator.hasNext()) {
            Booking booking = iterator.next();
            if (booking.getId() == id) {
                for (CoworkingSpace space : spaces) {
                    if (space.getId() == booking.getWorkspaceId()) {
                        space.setAvailable(true);
                    }
                }
                iterator.remove();
                System.out.println("Reservation canceled successfully.");
                return;
            }
        }
        System.out.println("Reservation ID not found.");
    }
}
