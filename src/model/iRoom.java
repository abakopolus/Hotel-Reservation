package model;

/**
 * Interface iRoom was created to support polymorphism and multiple inheritance
 * The Room Class implements this interface
 */
public interface iRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public roomType getRoomType();
    public boolean isFree();
}
