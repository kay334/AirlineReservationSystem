import java.util.Scanner;

public class AirlineReservationSystem {

    public static void welcome() {
        System.out.println("\t\t|-----------------------------------------------------------|");
        System.out.println("\t\t| Welcome to Airline Flight Reservation System             |");
        System.out.println("\t\t|-----------------------------------------------------------|");
        System.out.println("\t\t| Choose your option:                                      |");
        System.out.println("\t\t|-----------------------------------------------------------|");
        System.out.println("\t\t| 1) Book Ticket                                            |");
        System.out.println("\t\t| 2) Cancel Ticket                                          |");
        System.out.println("\t\t| 3) Change Reservation                                     |");
        System.out.println("\t\t| 4) Passenger Details                                      |");
        System.out.println("\t\t| 5) Get Booking Details                                    |");
        System.out.println("\t\t| 6) Exit                                                   |");
        System.out.println("\t\t|-----------------------------------------------------------|");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Flight flight = new Flight();
        
        while (true) {
            welcome();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    flight.bookTicket();
                    break;
                case 2:
                    flight.cancelTicket();
                    break;
                case 3:
                    flight.changeReservation();
                    break;
                case 4:
                    flight.passengerDetails();
                    break;
                case 5:
                    flight.getBookingDetails();
                    break;
                case 6:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
