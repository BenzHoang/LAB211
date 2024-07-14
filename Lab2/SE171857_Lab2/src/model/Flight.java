/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bravee06
 */
public class Flight {

    // số hiệu chuyến bay 
    private String flightNumber;
    // nơi bắt đầu 
    private String departureCity;
    // nơi đến 
    private String destinationCity;
    // thơi gian bắt đầu 
    private String departureTime;
    // thơi gian đến 
    private String arrivalTime;
    
    private String Price;
    
    private List<Seat> seats;
    
    private List<Crew> assignedCrewMembers;

    public Flight() {
    }

    public Flight(String flightNumber, String departureCity, String destinationCity, String departureTime, String arrivalTime, String Price, List<Seat> seats, List<Crew> assignedCrewMembers) {
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.Price = Price;
        this.seats = seats;
        this.assignedCrewMembers = new ArrayList<>();
    }
    
    

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void assignCrewMember(Crew crewMember) {
        assignedCrewMembers.add(crewMember);
    }

    public List<Crew> getAssignedCrewMembers() {
        return assignedCrewMembers;
    }

    public void setAssignedCrewMembers(List<Crew> assignedCrewMembers) {
        this.assignedCrewMembers = assignedCrewMembers;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

   
    public String Show() {
        return String.format("|%-15s|%-20s|%-20s|%-16s|%-10s|%-10s|\n",
                    flightNumber, departureCity, destinationCity,
                    departureTime, arrivalTime,Price);
    }
    
    @Override
    public String toString() {
        StringBuilder crewIds = new StringBuilder();
        for (Crew crew : assignedCrewMembers) {
            if (crewIds.length() > 0) {
                crewIds.append("|");
            }
            crewIds.append(crew.getId());
        }
        return flightNumber + ", " + departureCity + ", " + destinationCity + ", " + departureTime + ", " + arrivalTime + ", "+Price+ ", " + seats.size() + ", " + crewIds.toString();
    }

}
