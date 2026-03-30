**Airline Reservation System**

**Project Description**
The project is a console-based Airline Reservation System implemented in Java. 
The system provides the facility to manage flights, book tickets, cancel reservations, and generate tickets in an efficient way.
The system provides the facility for: Domestic Flights & International Flights 

**Features**
Add Flight (Domestic / International)
Remove Flight
Search Flights (by departure & arrival)
Book Seats
View Flight Schedule
Save Flights to File
Load Flights from File
View Booking History
Cancel Reservation
Generate Ticket

**Technologies Used**
Java (Core Java)
OOP Concepts (Abstraction, Inheritance, Polymorphism)
File Handling (BufferedReader, BufferedWriter)

**Classes Used**
**Flight (Abstract Class)**
Common properties for all flights
Methods for booking and displaying details
**DomesticFlight**
Inherits Flight
Includes cabin class and meal options
**InternationalFlight**
Inherits Flight
Includes visa requirements and language options
**Booking**
Includes information like:
    Customer name
    Flight number
    Seats booked
    Booking date
**AirlineReservationSystem**
Core logic of the system
Includes:
    Flight management
    Booking system
    File operations
