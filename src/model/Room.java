package model;

/**
 * Define Room class, with an expected room number, price, and room type enumeration
 * As this class implements an interface, all interface methods are overridden below
 * Accessor methods are provided given the protected class variables
 */
public class Room implements iRoom {
    protected final String roomNumber;
    protected final Double price;
    protected final roomType enumeration;

    public Room(String roomNumber, Double price, roomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public final String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public final Double getRoomPrice() {
        return price;
    }

    @Override
    public final roomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\nRoom Type: " + enumeration + "\nRoom Price: " + price + "\n";
    }
}
