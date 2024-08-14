import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - $" + price + " per night";
    }
}

class Reservation {
    private static int idCounter = 1;
    private int reservationId;
    private String guestName;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(String guestName, Room room, Date checkInDate, Date checkOutDate) {
        this.reservationId = idCounter++;
        this.guestName = guestName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest: " + guestName + ", Room: " + room.getRoomNumber() +
                ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate;
    }
}

public class HotelReservationSystem {

    private List<Room> rooms;
    private List<Reservation> reservations;

    public HotelReservationSystem() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 1000.0));
        rooms.add(new Room(102, "Double", 1500.0));
        rooms.add(new Room(201, "Suite", 2000.0));
        rooms.add(new Room(202, "Suite", 2000.0));
        rooms.add(new Room(301, "Single", 1000.0));
        rooms.add(new Room(302, "Double", 1500.0));
    }

    public void searchAvailableRooms(String category, Date checkIn, Date checkOut) {
        System.out.println("Available rooms for " + category + " category:");
        for (Room room : rooms) {
            if (room.isAvailable() && room.getCategory().equalsIgnoreCase(category)) {
                System.out.println(room);
            }
        }
    }

    public void makeReservation(String guestName, int roomNumber, Date checkIn, Date checkOut) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                Reservation reservation = new Reservation(guestName, room, checkIn, checkOut);
                reservations.add(reservation);
                System.out.println("Reservation successful: " + reservation);
                return;
            }
        }
        System.out.println("Room not available or invalid room number.");
    }

    public void viewBookings() {
        System.out.println("Current bookings:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem hotel = new HotelReservationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Search Available Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. View Bookings");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter room category (Single, Double, Suite): ");
                    String category = scanner.nextLine();
                    // In a real application, you would parse dates properly
                    Date checkIn = new Date(); // Current date
                    Date checkOut = new Date(System.currentTimeMillis() + 86400000L); // Next day
                    hotel.searchAvailableRooms(category, checkIn, checkOut);
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Date checkInDate = new Date(); // Current date
                    Date checkOutDate = new Date(System.currentTimeMillis() + 86400000L); // Next day
                    hotel.makeReservation(guestName, roomNumber, checkInDate, checkOutDate);
                    break;
                case 3:
                    hotel.viewBookings();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
