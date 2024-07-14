
package model;

import java.util.List;


public class ReservationChecked extends Reservation {
    private List<String> seatNumberChecked;

    public ReservationChecked(List<String> seatNumberChecked, String reservationId, Flight flight, Passenger passenger) {
        super(reservationId, flight, passenger);
        this.seatNumberChecked = seatNumberChecked;
    }
    

    public List<String> getSeatNumberChecked() {
        return seatNumberChecked;
    }

    public void setSeatNumberChecked(List<String> seatNumberChecked) {
        this.seatNumberChecked = seatNumberChecked;
    }

    

    @Override
    public String toString() {
        StringBuilder listSeatChecked = new StringBuilder();
        for (String st : seatNumberChecked) {
            if (listSeatChecked.length() > 0) {
                listSeatChecked.append("-");
            }
            listSeatChecked.append(st);
        }
        return super.toString() + ", " + listSeatChecked;
    }

    
    
}
