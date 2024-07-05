package api;

import model.Customer;
import model.Reservation;
import model.iRoom;
import service.customerService;
import service.reservationService;

import java.util.Collection;
import java.util.Date;

/**
 * The hotelResource is a Singleton class in the resource layer that references the Singleton service classes that were
 * created in another layer. This class leverages the service class methods to accomplish tasks required as part of the
 * main menu
 */
public class hotelResource {

    //create singleton objects based on the class' methods specified in the service layer
    private final static reservationService reservationService = service.reservationService.getInstance();
    private final static customerService customerService = service.customerService.getInstance();

    //create the only instance of this Singleton class
    private static hotelResource instance  = new hotelResource();

    //this private constructor prevents the app from creating the class instance
    private hotelResource() {};

    //method that can be called in the UI/Menu layer to leverage this class instance
    public static hotelResource getInstance() {
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public iRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, iRoom room, Date checkInDate, Date checkOutDate) {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomersReservation(customerEmail);
    }

    public Collection<iRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }
}
