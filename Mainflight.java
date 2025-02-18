import java.io.*;
import java.util.*;
abstract class Flight {
    String flightNumber;
    String departureAirport;
    String arrivalAirport;
    String departureTime;
    String arrivalTime;
    int availableSeats;
    public Flight(String flightNumber, String departureAirport, String arrivalAirport,
                  String departureTime, String arrivalTime, int availableSeats) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }
    public String getFlightNumber() {
		return flightNumber; 
	}
    public String getDepartureAirport() {
		return departureAirport; 
	}
    public String getArrivalAirport() {
		return arrivalAirport; 
	}
    public String getDepartureTime() { 
		return departureTime;
	}
    public String getArrivalTime() {
		return arrivalTime;
	}
    public int getAvailableSeats() { 
		return availableSeats; 
	}
    public void setAvailableSeats(int availableSeats) { 
		this.availableSeats = availableSeats; 
	}
    public abstract void displayFlightDetails();
    public boolean bookSeat(int bookingSeats) throws Exception {
        if (availableSeats >= bookingSeats) {
            availableSeats -= bookingSeats;
            System.out.println("Booking successful. Now the number of available seats: " + availableSeats);
			setAvailableSeats(availableSeats);
            return true;
        } else {
            throw new Exception("No available seats on this flight.");
        }
    }
    public abstract String toFileString();
}

class Booking {
    String flightNumber;
    String customerName;
    int bookedSeats;
    String bookingDate;
    
    public Booking(String flightNumber, String customerName, int bookedSeats, String bookingDate) {
        this.flightNumber = flightNumber;
        this.customerName = customerName;
        this.bookedSeats = bookedSeats;
        this.bookingDate = bookingDate;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String toString() {
        return "Booking [Flight Number: " + flightNumber + ", Customer: " + customerName + 
               ", Seats: " + bookedSeats + ", Date: " + bookingDate + "]";
    }
}

class DomesticFlight extends Flight {
    String cabinClass;
    String[] mealOptions;
    public DomesticFlight(String flightNumber, String departureAirport, String arrivalAirport,
                          String departureTime, String arrivalTime, int availableSeats, String cabinClass,
                          String[] mealOptions) {
        super(flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, availableSeats);
        this.cabinClass = cabinClass;
        this.mealOptions = mealOptions;
    }
    public void displayFlightDetails() {
        System.out.println("Domestic Flight: " + getFlightNumber() + " from " + getDepartureAirport() +
                           " to " + getArrivalAirport() + ". Departure: " + getDepartureTime() +
                           ", Arrival: " + getArrivalTime() + ". Available Seats: " + getAvailableSeats());
        System.out.println("Cabin Class: " + cabinClass);
        System.out.print("Meal Options: ");
        for (String meal : mealOptions) {
            System.out.print(meal + " ");
        }
        System.out.println();
    }
    public String toFileString() {
        return "Domestic," + getFlightNumber() + "," + getDepartureAirport() + "," + getArrivalAirport() +
               "," + getDepartureTime() + "," + getArrivalTime() + "," + getAvailableSeats() + "," +
               cabinClass + "," + String.join(",", mealOptions);
    }
}

class InternationalFlight extends Flight {
    String visaRequirements;
    String[] languageOptions;
    public InternationalFlight(String flightNumber, String departureAirport, String arrivalAirport,
                               String departureTime, String arrivalTime, int availableSeats, String visaRequirements,
                               String[] languageOptions) {
        super(flightNumber, departureAirport, arrivalAirport, departureTime, arrivalTime, availableSeats);
        this.visaRequirements = visaRequirements;
        this.languageOptions = languageOptions;
    }
    public void displayFlightDetails() {
        System.out.println("International Flight: " + getFlightNumber() + " from " + getDepartureAirport() +
                           " to " + getArrivalAirport() + ". Departure: " + getDepartureTime() +
                           ", Arrival: " + getArrivalTime() + ". Available Seats: " + getAvailableSeats());
        System.out.println("Visa Requirements: " + visaRequirements);
        System.out.print("Language Options: ");
        for (String language : languageOptions) {
            System.out.print(language + " ");
        }
        System.out.println();
    }
    public String toFileString() {
        return "International," + getFlightNumber() + "," + getDepartureAirport() + "," + getArrivalAirport() +
               "," + getDepartureTime() + "," + getArrivalTime() + "," + getAvailableSeats() + "," +
               visaRequirements + "," + String.join(",", languageOptions);
    }
}

class AirlineReservationSystem {
    Scanner scanner = new Scanner(System.in);
    Flight[] flightSchedule;
    int flightCount;
	Booking[] bookingHistory; 
    int bookingCount;
    public AirlineReservationSystem(int maxFlights,int maxBookings) {
        flightSchedule = new Flight[maxFlights];
        flightCount = 0;
		bookingHistory = new Booking[maxBookings];
        bookingCount = 0;
    }
    public void addFlight(Flight flight) {
        if (flightCount < flightSchedule.length) {
            flightSchedule[flightCount] = flight;
            flightCount++;
        } else {
            System.out.println("Flight schedule is full. Cannot add more flights.");
        }
    }
    public void removeFlight(String flightNumber) {
        boolean flightRemoved = false;
        for (int i = 0; i < flightCount; i++) {
            if (flightSchedule[i].getFlightNumber().equals(flightNumber)) {
                for (int j = i; j < flightCount - 1; j++) {
                    flightSchedule[j] = flightSchedule[j + 1];
                }
                flightSchedule[flightCount - 1] = null;
                flightCount--;
                flightRemoved = true;
                break;
            }
        }
        if (flightRemoved) {
            System.out.println("Flight removed successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }
    public void searchFlights(String departureAirport, String arrivalAirport) {
        boolean found = false;
        for (int i = 0; i < flightCount; i++) {
            if (flightSchedule[i].getDepartureAirport().equals(departureAirport) &&
                flightSchedule[i].getArrivalAirport().equals(arrivalAirport)) {
                flightSchedule[i].displayFlightDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No flights found for the given route.");
        }
    }
    public boolean bookSeatOnFlight(String flightNumber,String customerName) throws Exception {
        for (int i = 0; i < flightCount; i++) {
            if (flightSchedule[i].getFlightNumber().equals(flightNumber)) {
                System.out.print("How many seats would you like to book?: ");
                int bookingSeats = scanner.nextInt();
                //return flightSchedule[i].bookSeat(bookingSeats);
				if(flightSchedule[i].bookSeat(bookingSeats)){
					scanner.nextLine();
					System.out.print("Enter booking date (yyyy-mm-dd): ");
                    String bookingDate = scanner.nextLine();
                    return addBooking(flightNumber, customerName, bookingSeats,bookingDate);
				}
            }
        }
        throw new Exception("Flight not found.");
    }
    public void displayFlightSchedule() {
        if (flightCount == 0) {
            System.out.println("No flights available.");
        } else {
            for (int i = 0; i < flightCount; i++) {
                flightSchedule[i].displayFlightDetails();
                System.out.println("-------------------------------");
            }
        }
    }
    public void saveFlightsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < flightCount; i++) {
                writer.write(flightSchedule[i].toFileString());
                writer.newLine();
            }
            System.out.println("Flight schedule saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving flight schedule to file: " + e.getMessage());
        }
    }
    public void loadFlightsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals("Domestic")) {
                    String[] mealOptions = data[7].split(" ");
                    addFlight(new DomesticFlight(data[1], data[2], data[3], data[4], data[5],
                            Integer.parseInt(data[6]), data[7], mealOptions));
                } else if (data[0].equals("International")) {
                    String[] languageOptions = data[8].split(" ");
                    addFlight(new InternationalFlight(data[1], data[2], data[3], data[4], data[5],
                            Integer.parseInt(data[6]), data[7], languageOptions));
                }
            }
            System.out.println("Flight schedule loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading flight schedule from file: " + e.getMessage());
        }
    }
    public boolean addBooking(String flightNumber, String customerName, int bookingSeats, String bookingDate) {
        if (bookingCount < bookingHistory.length) {
            Booking newBooking = new Booking(flightNumber, customerName, bookingSeats, bookingDate);
            bookingHistory[bookingCount] = newBooking;
            bookingCount++;
			return true;
        } else {
            System.out.println("Booking history is full.");
			return false;
        }
    }
    public void viewBookingHistory() {
        if (bookingCount == 0) {
            System.out.println("No booking history available.");
        } else {
            for (int i = 0; i < bookingCount; i++) {
                System.out.println(bookingHistory[i]);
            }
        }
    }
    public void cancelBooking(String flightNumber, String customerName) {
        boolean bookingFound = false;
        for (int i = 0; i < bookingCount; i++) {
            if (bookingHistory[i].getFlightNumber().equals(flightNumber) && bookingHistory[i].getCustomerName().equals(customerName)) {
                bookingFound = true;
                for (int j = 0; j < flightCount; j++) {
                    if (flightSchedule[j].getFlightNumber().equals(flightNumber)) {
                        flightSchedule[j].setAvailableSeats(flightSchedule[j].getAvailableSeats() + bookingHistory[i].getBookedSeats());
                        System.out.println("Booking canceled. Seats restored: " + bookingHistory[i].getBookedSeats());
                        break;
                    }
                }
                for (int k = i; k < bookingCount - 1; k++) {
                    bookingHistory[k] = bookingHistory[k + 1];
                }
                bookingHistory[bookingCount - 1] = null;
                bookingCount--;
                break;
            }
        }
        if (!bookingFound) {
            System.out.println("Booking not found.");
        }
    }
    public void generateTicket(String flightNumber, String customerName) {
        for (int i = 0; i < bookingCount; i++) {
            if (bookingHistory[i].getFlightNumber().equals(flightNumber) && bookingHistory[i].getCustomerName().equals(customerName)) {
                for (int j = 0; j < flightCount; j++) {
                    if (flightSchedule[j].getFlightNumber().equals(flightNumber)) {
                        System.out.println("Ticket generated for " + customerName + ":");
                        System.out.println("Flight: " + flightSchedule[j].getFlightNumber());
                        System.out.println("From: " + flightSchedule[j].getDepartureAirport() + " to " + flightSchedule[j].getArrivalAirport());
                        System.out.println("Departure: " + flightSchedule[j].getDepartureTime() + " Arrival: " + flightSchedule[j].getArrivalTime());
                        System.out.println("Seats: " + bookingHistory[i].getBookedSeats());
                        System.out.println("Booking Date: " + bookingHistory[i].getBookingDate());
						break;
					}
				}
			}
		}
        System.out.println("Ticket not found for the given details.");
	}
}
public class Mainflight {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AirlineReservationSystem system = new AirlineReservationSystem(10,100);
        system.loadFlightsFromFile("flights.txt");
		int choice;
        do{
            System.out.println("\n--- Airline Reservation System ---");
            System.out.println("1. Add Flight");
            System.out.println("2. Remove Flight");
            System.out.println("3. Search Flights");
            System.out.println("4. Book a Seat");
            System.out.println("5. Display Flight Schedule");
            System.out.println("6. Save Flight Schedule");
			 System.out.println("7. View Booking History");
            System.out.println("8. Cancel Reservation");
            System.out.println("9. Generate Ticket");
            System.out.println("10. Exit");
            System.out.print("Please choose an option: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
				    scanner.nextLine();
                    System.out.print("Enter Flight Number: ");
                    String flightNumber = scanner.nextLine();
                    System.out.print("Enter Departure Airport: ");
                    String departureAirport = scanner.nextLine();
                    System.out.print("Enter Arrival Airport: ");
                    String arrivalAirport = scanner.nextLine();
                    System.out.print("Enter Departure Time (yyyy-mm-dd hh:mm): ");
                    String departureTime = scanner.nextLine();
                    System.out.print("Enter Arrival Time (yyyy-mm-dd hh:mm): ");
                    String arrivalTime = scanner.nextLine();
                    System.out.print("Enter Available Seats: ");
                    int availableSeats = scanner.nextInt();
					scanner.nextLine();
                    System.out.print("Is this a Domestic Flight? (yes/no): ");
                    String isDomestic = scanner.nextLine();
                    if (isDomestic.equalsIgnoreCase("yes")) {
                        System.out.print("Enter Cabin Class: ");
                        String cabinClass = scanner.nextLine();
                        System.out.print("Enter Meal Options (comma separated): ");
                        String meals = scanner.nextLine();
                        String[] mealOptions = meals.split(",");
                        system.addFlight(new DomesticFlight(flightNumber, departureAirport, arrivalAirport,
                                                             departureTime, arrivalTime, availableSeats,
                                                             cabinClass, mealOptions));
                    } else {
						scanner.nextLine();
                        System.out.print("Enter Visa Requirements: ");
                        String visaRequirements = scanner.nextLine();
                        System.out.print("Enter Language Options (comma separated): ");
                        String languages = scanner.nextLine();
                        String[] languageOptions = languages.split(",");
                        system.addFlight(new InternationalFlight(flightNumber, departureAirport, arrivalAirport,
                                                                  departureTime, arrivalTime, availableSeats,
                                                                  visaRequirements, languageOptions));
                    }
                    break;
                case 2:
				    scanner.nextLine();
                    System.out.print("Enter Flight Number to remove: ");
                    String removeFlightNumber = scanner.nextLine();
                    system.removeFlight(removeFlightNumber);
                    break;
                case 3:
				    scanner.nextLine();
                    System.out.print("Enter Departure Airport: ");
                    String searchDeparture = scanner.nextLine();
                    System.out.print("Enter Arrival Airport: ");
                    String searchArrival = scanner.nextLine();
                    system.searchFlights(searchDeparture, searchArrival);
                    break;
                case 4:
				    scanner.nextLine();
                    System.out.print("Enter Flight Number to book a seat: ");
                    String bookFlightNumber = scanner.nextLine();
					System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    try {
                        boolean success = system.bookSeatOnFlight(bookFlightNumber,customerName);
                        if (success) {
                            System.out.println("Seat booked successfully.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 5:
                    system.displayFlightSchedule();
                    break;
                case 6:
                    system.saveFlightsToFile("flights.txt");
                    break;
				case 7:
                    system.viewBookingHistory();
                    break;
                case 8:
				    scanner.nextLine();
                    System.out.print("Enter Flight Number to cancel reservation: ");
                    String cancelFlightNumber = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String cancelCustomerName = scanner.nextLine();
                    system.cancelBooking(cancelFlightNumber, cancelCustomerName);
                    break;
                case 9:
				    scanner.nextLine();
                    System.out.print("Enter Flight Number to generate ticket: ");
                    String ticketFlightNumber = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String ticketCustomerName = scanner.nextLine();
                    system.generateTicket(ticketFlightNumber, ticketCustomerName);
                    break;					
				case 10:
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }while(choice<=10);
    }
}