package service;

import model.Customer;
import model.Reservation;
import model.iRoom;

import java.util.*;

/**
 * The reservationService Class is a service class that will instantiate a Singleton object
 * This singleton object is called instance and can be "communicated" with in the Resource layer
 * through getInstance(). This class will also create HashMaps called rooms and reservations
 * that will have a Room Number and Email key, respectively, and an iRoom and Collection<Reservation> value respectively
 *
 * This Singleton object can then be used to add a Room in the application, get a particular Room
 * from the application, or get ALL Rooms from the application. Additionally, this object can find and book rooms
 * as well as print out all existing reservations in the application
 */
public class reservationService {

    //create the only instance of this Singleton class
    private static reservationService instance = new reservationService();

    //this private constructor prevents the app from creating the class instance
    private reservationService() {};

    //method that can be called in the resource layer to leverage this class instance
    public static reservationService getInstance() {
        return instance;
    }

    //HashMaps and a static variable created for use by the instance's methods detailed below
    private final Map<String, iRoom> rooms = new HashMap<String, iRoom>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<String, Collection<Reservation>>();
    private static final int additionalRoomWindow = 7;

    /**
     * addRoom will look to see if a room number currently exists in the rooms HashMap. If it does, it will throw an exception
     * If it does not exist, the room will be added
     * @param room iRoom object that has the necessary detail around room number, price, and room type
     */
    public final void addRoom(iRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("This room number already exists in the hotel");
        } else {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    /**
     * getARoom will take in a room number and if it exists in the rooms HashMap will return the iRoom object
     * If it doesn't, an exception is thrown noting that the room does not exist in the hotel
     * @param roomId room number that the application user is interested in getting
     * @return the iRoom object associated with the room number
     */
    public final iRoom getARoom(String roomId) {
        if (rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        } else {
            throw new IllegalArgumentException("This is not a valid room in the hotel");
        }
    }

    public Collection<iRoom> getAllRooms() {
        return rooms.values();
    }

    /**
     * getCustomersReservation will return the reservations that are under a particular Customer in the application
     * @param email Requesting customer's email address
     * @return the Collection <Reservation> associated with that Customer's email address
     */
    public Collection<Reservation> getCustomersReservation(String email) {
        System.out.println(reservations.get(email));
        return reservations.get(email);
    }

    /**
     * Each Collection of Reservation types found as values in reservations are added to a LinkedList named
     * allReservations.
     * @return the LinkedList that includes all reservations currently in the application
     */
    private Collection<Reservation> getAllReservedRooms() {
        Collection<Reservation> allReservations = new LinkedList<Reservation>();
        for (Collection<Reservation> reservationCollection : reservations.values()) {
            allReservations.addAll(reservationCollection);
        }
        return allReservations;
    }

    /**
     * findOpenRooms first gets all existing reservations as a Collection. Empty Linked Lists for open and booked rooms
     * are also created. Next, all existing reservations are reviewed and if the Customer requested check-in and check-out
     * dates overlap with an already existing reservation, the room is added to the bookedRooms LinkedList
     *
     * After evaluating all reservations, if the room is not in the bookedRooms list, the room is considered open
     * @param checkInDate Customer requested check-in date
     * @param checkOutDate Customer requested check-out date
     * @return a LinkedList of open/available rooms in the hotel application
     */
    private Collection<iRoom> findOpenRooms(Date checkInDate, Date checkOutDate) {
        Collection<Reservation> existingReservations = getAllReservedRooms();
        Collection<iRoom> bookedRoomNumbers = new LinkedList<iRoom>();
        Collection<iRoom> openRooms = new LinkedList<iRoom>();
        for (Reservation reservation : existingReservations) {
            if (checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate())) {
                bookedRoomNumbers.add(reservation.getRoom());
            }
        }
        for (iRoom room : rooms.values()) {
            if (!bookedRoomNumbers.contains(room)) {
                openRooms.add(room);
            }
        }
        return openRooms;
    }

    /**
     * This is a "helper" function creates a calendar instance and sets the time based on a received check-in or check-out date
     * Seven days are then added to the date and the new date is returned
     *
     * @param date Customer requested check-in or check-out
     * @return date + 7 days
     */
    public Date additionalWindowCreation(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, additionalRoomWindow);
        return calendar.getTime();
    }

    /**
     * Leverages the findOpenRooms method detailed above to return a collection of iRooms that are available for a particular
     * check-in and check-out window. If there are no rooms available, the function will look 7 days forward and attempt to
     * provide additional options for the application user
     *
     * @param checkInDate Customer requested check-in date
     * @param checkOutDate Customer requested check-out date
     * @return a Collection of iRooms that are open for the requested period
     */
    public Collection<iRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<iRoom> openRooms = findOpenRooms(checkInDate, checkOutDate);

        if (openRooms.isEmpty()) {
            System.out.println("No Rooms Available during this window. There may be availability the following week:\n"
                    + additionalWindowCreation(checkInDate) + " - " + additionalWindowCreation(checkOutDate));
            openRooms = findOpenRooms(additionalWindowCreation(checkInDate), additionalWindowCreation(checkOutDate));
        }
        return openRooms;
    }

    /**
     * findRooms detailed above is leveraged here to provide a collection of all Open rooms for the requested time period.
     * If no rooms are available for the time period, the following 7-day window is then assessed. A Collection called
     * newReservation is added to the reservations HashMap with the Customer email as a key and the Collection as the value
     * if the requested room is one of the open rooms
     * @param customer Customer
     * @param room iRoom
     * @param checkInDate Customer requested check-in date
     * @param checkOutDate Customer requested check-out date
     * @return placeholder
     */
    public Reservation reserveARoom(Customer customer, iRoom room, Date checkInDate, Date checkOutDate) {
        Collection<iRoom> openRooms = findOpenRooms(checkInDate, checkOutDate);

        if (openRooms.isEmpty()) {
            System.out.println("No Rooms Available during this window. There may be availability the following week:\n"
                    + additionalWindowCreation(checkInDate) + " - " + additionalWindowCreation(checkOutDate));

            openRooms = findOpenRooms(additionalWindowCreation(checkInDate), additionalWindowCreation(checkOutDate));

            Collection<Reservation> newReservation = new HashSet<Reservation>();
            Reservation addedReservation = new Reservation(customer, room, additionalWindowCreation(checkInDate), additionalWindowCreation(checkOutDate));
            newReservation.add(new Reservation(customer, room, additionalWindowCreation(checkInDate), additionalWindowCreation(checkOutDate)));
            if (openRooms.contains(room)) {
                System.out.println("Reservation created successfully!");
                reservations.put(customer.getEmail(), (Collection<Reservation>) newReservation);
                return addedReservation;
            }
            else {
                System.out.println("This room is not available to book. Please try again.");
                return null;}
        }
        else {
            Collection<Reservation> newReservation = new HashSet<Reservation>();
            Reservation addedReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            newReservation.add(new Reservation(customer, room, checkInDate, checkOutDate));
            if (openRooms.contains(room)) {
                System.out.println("Reservation created successfully!");
                reservations.put(customer.getEmail(), (Collection<Reservation>) newReservation);
                return addedReservation;
            }
            else {
                System.out.println("This room is not available to book. Please try again.");
                return null;}
        }
    }

    /**
     * For each reservation in the Collection of reservations, the details around the stay are printed out to the console
     */
    public void printAllReservation() {
        Collection<Reservation> allReservations = getAllReservedRooms();
        for (Reservation reservation : allReservations) {
            System.out.println(reservation);
        }
        }
    }

