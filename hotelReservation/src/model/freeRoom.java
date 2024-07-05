package model;

/**
 * Leverage inheritance to extend the Room Class to create the freeRoom Class
 * Only differences for this class are that price is always 0.00 and the isFree() boolean is true
 */
public class freeRoom extends Room {

    public freeRoom(String roomNumber, roomType enumeration) {
        super(roomNumber, 0.0, enumeration);
    }

    @Override
    public final boolean isFree() {
        return true;
    }

    @Override
    public final String toString() {
        return "Room Number: " + roomNumber + "\nRoom Type: " + enumeration + "\nRoom Price: " + price;
    }
}
