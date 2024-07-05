package model;

import java.util.Date;

/**
 * Define Reservation class, with expected Customer, iRoom, and Check-In + Out data
 * Accessor methods are provided given the private class variables
 */
public class Reservation {
    private final Customer customer;
    private final iRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, iRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final iRoom getRoom() {
        return room;
    }

    public final Date getCheckInDate() {
        return checkInDate;
    }

    public final Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public final String toString() {
        return customer.toString() + "\n" + room.toString() + "\n" + checkInDate + "\n" + checkOutDate;
    }
}
