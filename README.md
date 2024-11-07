# AirlineReservationSystem
Java-based console application that allows users to manage flight bookings. The system lets passengers book tickets, cancel tickets, change seat reservations, and view their booking details. It uses a MySQL database to store and manage passenger information such as reservation numbers, personal details, and flight preferences.

Features:
Book a Ticket: Reserve a seat and provide passenger details.
Cancel a Ticket: Cancel a reservation using the reservation number.
Change a Reservation: Modify an existing seat reservation.
View Passenger Details: Retrieve information for a specific reservation.
View All Booking Details: Display a list of all active reservations.
Requirements:
Java 8 or higher
MySQL Database
JDBC Driver for MySQL
Setup:
Create Database: Set up the AirlineReservationSystem database in MySQL.
Create Table: Create the Passengers table in the database.
Modify Database Credentials: Update the database connection details in the Flight class (username, password, database URL).
