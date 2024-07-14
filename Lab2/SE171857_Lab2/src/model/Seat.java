
package model;

public class Seat {
    private String seatNumber;
    private boolean isOccupied;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isOccupied = false;
    }

    public Seat() {
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isIsOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void isFlase() {
        isOccupied = false;
    }
    
    @Override
    public String toString() {
        return seatNumber+", ";
    }
    
}