
package model;

import java.util.List;

public class Passenger {

    private String name;
    // phone 
    private String contactInfo;
    
    private int numberSeatsPassenger;
    
    private List<String>  NamePassengerToFlight;
    public Passenger() {
    }
    
    
    //them 2 attribute la: So luong ghe va ten nhung nguoi bay
    // +=>> R2, F0021, Tran Huy Hanh, 0963254123, 4, Hanh | Tan | Thai | Quy , S1, S2, S3, S4

    public Passenger(String name, String contactInfo, int numberSeatsPassenger, List<String> NamePassengerToFlight) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.numberSeatsPassenger = numberSeatsPassenger;
        this.NamePassengerToFlight = NamePassengerToFlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getNumberSeatsPassenger() {
        return numberSeatsPassenger;
    }

    public void setNumberSeatsPassenger(int numberSeatsPassenger) {
        this.numberSeatsPassenger = numberSeatsPassenger;
    }

    public List<String> getNamePassengerToFlight() {
        return NamePassengerToFlight;
    }
    
    public void setNamePassengerToFlight(List<String> NamePassengerToFlight) {
        this.NamePassengerToFlight = NamePassengerToFlight;
    }

    @Override
    public String toString() {
        StringBuilder NameTotal = new StringBuilder();
        for(String st : NamePassengerToFlight){
            if (NameTotal.length() > 0) {
                NameTotal.append("|");
            }
            NameTotal.append(st);
        }
        return name + ", " + contactInfo + ", " + numberSeatsPassenger + ", " + NameTotal;
    }
    
    

    

}
