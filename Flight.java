import java.util.Scanner;
import java.sql.*;

public class Flight {

    static final int totalSeats = 100; // Total seats in the flight
    static int[] seats = new int[totalSeats]; // Array to track seat bookings
    static int reservedSeats = 1000; // Track the reserved seat count
    static int canceledTickets = 0; // Track canceled tickets
    Connection connection;

    // Constructor to establish database connection
    public Flight() {
        try {
            // Replace with your actual username and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AirlineReservationSystem", "username", "password");
            if (connection != null) {
                System.out.println("Database connected successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    // Book ticket
    public void bookTicket() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        Scanner scanner = new Scanner(System.in); // Initialize scanner
        try {
            System.out.println("Enter your first name: ");
            String fname = scanner.nextLine();
            System.out.println("Enter your last name: ");
            String lname = scanner.nextLine();
            System.out.println("Enter your ID: ");
            String id = scanner.nextLine();
            System.out.println("Enter your phone number: ");
            String phoneNumber = scanner.nextLine();

            int seatNumber;
            do {
                System.out.println("Enter the seat number (1-100): ");
                seatNumber = scanner.nextInt();
                if (seatNumber < 1 || seatNumber > totalSeats) {
                    System.out.println("Invalid seat number, please try again.");
                } else if (seats[seatNumber - 1] == -1) {
                    System.out.println("Seat already taken, please select another.");
                } else {
                    seats[seatNumber - 1] = -1; // Mark seat as reserved
                    break;
                }
            } while (true);

            scanner.nextLine(); // Consume newline
            System.out.println("Enter your food preference (1. Dried-Fruits&Biltong , 2. Sea Food&Steak, 3. Fruit-Salad&Veggies): ");
            int choice = scanner.nextInt();
            String foodMenu = (choice == 1) ? "Dried-Fruits&Biltong" : (choice == 2) ? "Sea Food&Steak" : "Fruit-Salad&Veggies";

            int reservationNumber = ++reservedSeats;

            // Insert into database
            try (PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO Passengers (first_name, last_name, id_number, phone_number, seat_number, reservation_number, food_preference) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
                pstmt.setString(1, fname);
                pstmt.setString(2, lname);
                pstmt.setString(3, id);
                pstmt.setString(4, phoneNumber);
                pstmt.setInt(5, seatNumber);
                pstmt.setInt(6, reservationNumber);
                pstmt.setString(7, foodMenu);
                pstmt.executeUpdate();
                System.out.println("Ticket booked successfully. Your reservation number is: " + reservationNumber);
            } catch (SQLException e) {
                System.out.println("Failed to book ticket: " + e.getMessage());
            }

        } finally {
            scanner.close(); // Close scanner after use
        }
    }

    // Cancel ticket
    public void cancelTicket() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        Scanner scanner = new Scanner(System.in); // Initialize scanner
        try {
            System.out.println("Enter your reservation number to cancel: ");
            int reservationNumber = scanner.nextInt();

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE Passengers SET is_canceled = TRUE WHERE reservation_number = ?")) {
                pstmt.setInt(1, reservationNumber);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Ticket canceled successfully.");
                } else {
                    System.out.println("Reservation number not found.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to cancel ticket: " + e.getMessage());
            }

        } finally {
            scanner.close(); // Close scanner after use
        }
    }

    // Change reservation
    public void changeReservation() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        Scanner scanner = new Scanner(System.in); // Initialize scanner
        try {
            System.out.println("Enter your current seat number: ");
            int currentSeat = scanner.nextInt();

            System.out.println("Choose a new seat number (1-100): ");
            int newSeat = scanner.nextInt();
            if (newSeat < 1 || newSeat > totalSeats || seats[newSeat - 1] == -1) {
                System.out.println("Invalid seat choice.");
                return;
            }

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "UPDATE Passengers SET seat_number = ? WHERE seat_number = ? AND is_canceled = FALSE")) {
                pstmt.setInt(1, newSeat);
                pstmt.setInt(2, currentSeat);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    seats[currentSeat - 1] = 0; // Mark old seat as available
                    seats[newSeat - 1] = -1; // Mark new seat as reserved
                    System.out.println("Reservation changed successfully.");
                } else {
                    System.out.println("Seat not found or is already canceled.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to change reservation: " + e.getMessage());
            }

        } finally {
            scanner.close(); // Close scanner after use
        }
    }

    // Show passenger details
    public void passengerDetails() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        Scanner scanner = new Scanner(System.in); // Initialize scanner
        try {
            System.out.println("Enter your reservation number: ");
            int reservationNumber = scanner.nextInt();

            try (PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT * FROM Passengers WHERE reservation_number = ? AND is_canceled = FALSE")) {
                pstmt.setInt(1, reservationNumber);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    System.out.println("Reservation Number | First Name | Last Name | ID | Phone Number | Seat Number | Food Menu");
                    System.out.println(rs.getInt("reservation_number") + " | " + rs.getString("first_name") + " | " + 
                        rs.getString("last_name") + " | " + rs.getString("id_number") + " | " + rs.getString("phone_number") + 
                        " | " + rs.getInt("seat_number") + " | " + rs.getString("food_preference"));
                } else {
                    System.out.println("Reservation number not found.");
                }
            } catch (SQLException e) {
                System.out.println("Failed to retrieve passenger details: " + e.getMessage());
            }

        } finally {
            scanner.close(); // Close scanner after use
        }
    }

    // Show all booking details
    public void getBookingDetails() {
        if (connection == null) {
            System.out.println("Database connection is not established.");
            return;
        }

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Passengers WHERE is_canceled = FALSE");
            System.out.println("Reservation Number | First Name | Last Name | ID | Phone Number | Seat Number | Food Menu");
            while (rs.next()) {
                System.out.println(rs.getInt("reservation_number") + " | " + rs.getString("first_name") + " | " +
                    rs.getString("last_name") + " | " + rs.getString("id_number") + " | " + rs.getString("phone_number") + 
                    " | " + rs.getInt("seat_number") + " | " + rs.getString("food_preference"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve booking details: " + e.getMessage());
        }
    }
}
