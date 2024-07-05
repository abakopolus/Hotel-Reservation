package api;

import model.Customer;
import model.iRoom;
import service.customerService;
import service.reservationService;

import java.util.Collection;
import java.util.List;

/**
 * The adminResource is a Singleton class in the resource layer that references the Singleton service classes that were
 * created in another layer. This class leverages the service class methods to accomplish tasks required as part of the
 * Admin menu
 */
public class adminResource {

    //create singleton objects based on the class' methods specified in the service layer
    private final reservationService reservationService = service.reservationService.getInstance();
    private final customerService customerService = service.customerService.getInstance();

    //create the only instance of this Singleton class
    private static adminResource instance  = new adminResource();

    //this private constructor prevents the app from creating the class instance
    private adminResource() {};

    //method that can be called in the UI/Menu layer to leverage this class instance
    public static adminResource getInstance() {
        return instance;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    //The double colon in the forEach statement (Class::Method) is leveraged for looping
    public void addRoom(List<iRoom> rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection<iRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
