
package model;

public class Reservation {

    private String reservationId;

    // thông tin của chuyến bay 
    private Flight flight;
    // thông tin của khách hàng 
    private Passenger passenger;

    public Reservation(String reservationId, Flight flight, Passenger passenger) {
        this.reservationId = reservationId;
        this.flight = flight;
        this.passenger = passenger;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return reservationId + ", " + flight.getFlightNumber() + ", " + passenger.toString() ;
    }

}
