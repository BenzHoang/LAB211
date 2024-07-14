package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import model.Admin;
import model.Crew;
import model.Flight;
import model.FlightAttendant;
import model.GroundStaff;
import model.Passenger;
import model.Pilot;
import model.Reservation;
import model.ReservationChecked;
import model.Seat;

public class FlightManagementSystem {

    public List<String> lSeats;
    public List<Flight> flights;
    public Validation valid;
    public List<Reservation> reservations;
    public List<Crew> listCrews;
    public List<Pilot> lPilots;
    public List<GroundStaff> lGroundStaff;
    public List<FlightAttendant> lFlightAttendants;
    public List<Admin> lAdmins;
    public FileManage fm = new FileManage();
    public List<Flight> flightsNoStart;
    public List<ReservationChecked> lReservationChecked;

    public FlightManagementSystem() {
        flights = new ArrayList<>();
        valid = new Validation();
        reservations = new ArrayList<>();
        listCrews = new ArrayList<>();
        lSeats = new ArrayList<>();
        flightsNoStart = new ArrayList<>();
        lReservationChecked = new ArrayList<>();
    }

    public void addNewFlight() {
        while (true) {

            String flightNumber = valid.checkFlightNumber("Enter flight number (Fxyzt): ", flights);
            String departureCity = valid.checkString("Enter departure location: ");
            String destinationCity = valid.checkString("Enter destination: ");
            String departureTime = valid.checkValidDate("Enter departure time: ");
            //String arrivalTime = valid.checkAfterDate("Nhập thời gian đến", departureTime);
            String duration = valid.checkValidTime("Enter time duration: ");

            // Chuyển departureTime và duration thành đối tượng LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime departureTimePlus = LocalDateTime.parse(departureTime, formatter);
            LocalTime durationPlus = LocalTime.parse(duration, DateTimeFormatter.ofPattern("HH:mm"));

            // Tính toán arrivalTime bằng cách cộng departureTime và duration
            LocalDateTime arrivalTime = departureTimePlus.plusHours(durationPlus.getHour()).plusMinutes(durationPlus.getMinute());

            // Chuyển arrivalTime thành chuỗi theo định dạng dd/MM/yyyy HH:mm
            String arrivalTimeStr = arrivalTime.format(formatter);
            //
            String price = valid.checkString("Enter price for each ticket: ");
            int totalSeats = valid.checkInt("Enter total number of seats: ", 0, Integer.MAX_VALUE);
            int seatID = 1;
            List<Seat> seats = new ArrayList<>();
            for (int i = 0; i < totalSeats; i++) {
                String seatNumber = "S" + (seatID++);
                Seat newSeat = new Seat(seatNumber);
                seats.add(newSeat);
            }

            // ctrl + space 
            Flight flight = new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTimeStr, price, seats, listCrews);
            flights.add(flight);
            flightsNoStart.add(flight);
            System.out.println("The flight has been added to the system !");
            if (valid.checkYesOrNo("Do you want to add more(Y/N)? ")) {
                continue;
            }
            return;
        }

    }

    public void updateFlight() {

        displayFlightsNoStarList();
        Flight flight = getFlightByFlightNumber(valid.checkString("Enter flight number you want to update: "), flightsNoStart);
        if (flight == null) {
            System.out.println("Does not have the flight with this flight number");
            return;
        }
        System.out.println("Information of flight number: " + flight.getFlightNumber());
        System.out.println(flight.getFlightNumber() + ": " + flight.getDepartureCity() + " ----> " + flight.getDestinationCity() + "| " + flight.getDepartureTime() + " ----> " + flight.getArrivalTime());
        String departureCity = valid.checkString("Enter new departure location: ");
        String destinationCity = valid.checkString("Enter new destination: ");
        String departureTime = valid.checkValidDate("Enter new departure time: ");
        String duration = valid.checkValidTime("Enter new time duration: ");
        // Chuyển departureTime và duration thành đối tượng LocalDateTime

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime departureTimePlus = LocalDateTime.parse(departureTime, formatter);
        LocalTime durationPlus = LocalTime.parse(duration, DateTimeFormatter.ofPattern("HH:mm"));

        // Tính toán arrivalTime bằng cách cộng departureTime và duration
        LocalDateTime arrivalTime = departureTimePlus.plusHours(durationPlus.getHour()).plusMinutes(durationPlus.getMinute());

        // Chuyển arrivalTime thành chuỗi theo định dạng dd/MM/yyyy HH:mm
        String arrivalTimeStr = arrivalTime.format(formatter);
        int totalSeats = valid.checkInt("Enter total number of seats: ", 0, Integer.MAX_VALUE);
        int seatID = 1;
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < totalSeats; i++) {
            String seatNumber = "S" + (seatID++);
            Seat newSeat = new Seat(seatNumber);
            seats.add(newSeat);
        }
        flight.setDepartureCity(departureCity);
        flight.setDepartureTime(departureTime);
        flight.setDestinationCity(destinationCity);
        flight.setArrivalTime(arrivalTimeStr);
        flight.setSeats(seats);
        System.out.println("The new information of flight with number:" + flight.getFlightNumber());
        System.out.println(flight.getFlightNumber() + ": " + flight.getDepartureCity() + " ----> " + flight.getDestinationCity() + "| " + flight.getDepartureTime() + " ----> " + flight.getArrivalTime());

    }

    public void deleteFlightByNumber() {
        Flight flight = getFlightByFlightNumber(valid.checkString("Enter flight number you want to remove: "), flights);
        if (flight == null) {
            System.err.println("Does not have the flight with this flight number. No removed");
            return;
        } else {
            flights.remove(flight);
        }
        System.out.println("Remove successfully");
    }

    public void bookPassengerReservation() {

        System.out.println("Book airline tickets and reserve seats for passengers");

        String passengerName = valid.checkString("Enter passenger name: ");

        String contactInfo = valid.checkString("Enter contact information: ");
        
        // Hiển thị danh sách chuyến bay có sẵn để hành khách chọn
        if (!searchFlightBaseOnDepatureArrived2()) {
          return;
        }
        
        String flightNumber = "";
        Flight selectedFlight = new Flight();
        
        while (true) {
            flightNumber = valid.checkString("Enter flight number: ");
            // Kiểm tra xem mã chuyến bay có tồn tại hay không
            selectedFlight = getFlightByFlightNumber(flightNumber, flightsNoStart);
            if (selectedFlight == null) {
                System.err.println("Invalid flight code !!!");
                continue;
            }
            break;
        }
        System.out.println("Price for each ticket: " + Integer.parseInt(selectedFlight.getPrice()));
        // Tạo mã đặt chỗ duy nhất
        String reservationId = "R" + (reservations.size() + lReservationChecked.size() + 1);
        // cofn van de
        // chuyền vào số seat của của chuyến bay
        // đếm số lượng vé đã được đặt(cả đã check in và chưa checkin) --> chuyền cả 2 list vào return số vé đã đặt 
        System.out.println("The remaining number of tickets is: " + (selectedFlight.getSeats().size() - NumberTicketDone(flightNumber)));
        int NumbersSeat = valid.checkInt("Enter number seat you want to book: ", 1, selectedFlight.getSeats().size() - NumberTicketDone(flightNumber));
        List<String> NamePassengerOfSeat = new ArrayList<>();
        for (int i = 0; i < NumbersSeat; i++) {
            NamePassengerOfSeat.add(valid.checkString("Enter name passenger " + (i + 1) + " : "));
        }
        Passenger passenger = new Passenger(passengerName, contactInfo, NumbersSeat, NamePassengerOfSeat);
        Reservation reservation = new Reservation(reservationId, selectedFlight, passenger);
        reservations.add(reservation);
        System.out.println("Total price of " + reservation.getPassenger().getNumberSeatsPassenger() +" is: "+ reservation.getPassenger().getNumberSeatsPassenger()*Integer.parseInt(selectedFlight.getPrice()));
        System.out.println("Reservation successful. Your booking code is: " + reservationId);
    }

    public int NumberTicketDone(String flightNumber) {
        int count = 0;
        for (Reservation r : reservations) {
            if (r.getFlight().getFlightNumber().equals(flightNumber)) {
                count += r.getPassenger().getNumberSeatsPassenger();
            }
        }
        for (ReservationChecked rd : lReservationChecked) {
            if (rd.getFlight().getFlightNumber().equals(flightNumber)) {
                count += rd.getPassenger().getNumberSeatsPassenger();
            }
        }

        return count;
    }

    public boolean searchFlightBaseOnDepatureArrived() throws ParseException {
        String departureCity = valid.checkString("Enter departure location: ");
        String destinationCity = valid.checkString("Enter destination: ");
        String dateFormat = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        String date = valid.checkBeforeDate("Enter date: ");
        Date ngaychon = sdf.parse(date);

        boolean flag = false;

        for (Flight f : flights) {
            Date ngaybay = sdf.parse(f.getDepartureTime().split(" ")[0]);
            if (f.getDepartureCity().equals(departureCity) && f.getDestinationCity().equals(destinationCity) && ngaychon.equals(ngaybay)) {
                //flightNumber + ", " + departureCity + ", " + destinationCity + ", " + departureTime + ", " + arrivalTime
                System.out.println(f.getFlightNumber() + ": " + f.getDepartureCity() + " ----> " + f.getDestinationCity() + "| " + f.getDepartureTime() + " ----> " + f.getArrivalTime() );
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("Does not have flight from: " + departureCity + " ---> " + destinationCity);
        }
        return flag;
    }
    public boolean searchFlightBaseOnDepatureArrived2() {
        String departureCity = valid.checkString("Enter departure location: ");
        String destinationCity = valid.checkString("Enter destination: ");
        boolean flag = false;

        for (Flight f : flightsNoStart) {
            if (f.getDepartureCity().equals(departureCity) && f.getDestinationCity().equals(destinationCity)) {
                //flightNumber + ", " + departureCity + ", " + destinationCity + ", " + departureTime + ", " + arrivalTime
                System.out.println(f.getFlightNumber() + ": " + f.getDepartureCity() + " ----> " + f.getDestinationCity() + "| " + f.getDepartureTime() + " ----> " + f.getArrivalTime());
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Does not have flight from: " + departureCity + " ---> " + destinationCity);
        }
        return flag;
    }

    public void performCheckIn() {
        System.out.println("Check-in process and seat allocation for passengers");

        String reservationId = valid.checkString("Enter your booking code: ");

        // Tìm đặt chỗ dựa trên mã đặt chỗ
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            System.err.println("No reservations found with this code !!!");
            return;
        }

        Flight flight = reservation.getFlight();
        //check
        boolean flag = false;
        for (Flight f : flightsNoStart) {
            if (f.equals(flight)) {
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("The check-in deadline has passed");
            return;
        }

        // Kiểm tra số lượng ghế sẵn có  trên chuyến bay và phân bổ ghế
        List<Seat> availableSeats = findAllAvailableSeat(flight);
        if (availableSeats.isEmpty()) {
            System.err.println("All seats have been booked. Cannot perform check-in !!!");
            return;
        }
        // Hiển thị danh sách chuyến bay có sẵn để hành khách chọn
        System.out.println("List of available seats in flight: ");
        //displayAvaiSeatList(availableSeats);
        displayStatusSeat(flight);
        String seatNumber = "";
        Seat selectedSeat = new Seat();
        List<String> totalSeat = new ArrayList<>();
        int count = reservation.getPassenger().getNumberSeatsPassenger();
        int total = 0;
        while (total != count) {
            seatNumber = valid.checkString("\nEnter the flight seat number " + reservation.getPassenger().getNamePassengerToFlight().get(total) + " want to select: ");
            // Kiểm tra xem ghế có tôn tại hay không 
            selectedSeat = getSeatByNumber(seatNumber, availableSeats);
            if (selectedSeat == null) {
                System.err.println("Seat number not available !!!");
                continue;
            }
            selectedSeat.occupy();
            totalSeat.add(seatNumber);
            total++;
        }
        reservations.remove(reservation);
        //reservation = new ReservationChecked(totalSeat, reservation.getReservationId(), flight, reservation.getPassenger());
        ReservationChecked res = new ReservationChecked(totalSeat, reservation.getReservationId(), flight, reservation.getPassenger());
        lReservationChecked.add(res);

        // Đánh dấu ghế đã được đặt
        // Tạo thẻ lên máy bay với thông tin của hành khách và ghế
        //System.out.println("Thẻ lên máy bay:");
        //System.out.println("Hành khách: " + reservation.getPassenger());
        //System.out.println("Chuyến bay: " + flight.getFlightNumber());
        //System.out.println("Ghế số: " + selectedSeat.getSeatNumber());
        System.out.println("Check-in successfully.");
        for (int i = 0; i < total; i++) {
            System.out.println("|====================================================================================================|");
            System.out.println("|                                            AIRLINE TICKET                                          |");
            System.out.println("|====================================================================================================|");
            System.out.printf("|ID Passenger:%-25s            |***********Ariplane ID: %10s***************|", reservation.getReservationId(), flight.getFlightNumber());
            System.out.println();
            System.out.printf("|Name:        %-25s            |Contact:%-40s |", reservation.getPassenger().getNamePassengerToFlight().get(i), reservation.getPassenger().getContactInfo());
            System.out.println();
            System.out.printf("|Departure:   %-25s            |Departure Date and Time:%-25s|", flight.getDepartureCity(), flight.getDepartureTime());
            System.out.println();
            System.out.printf("|Destination: %-25s            |Seat:  %-20s | Price: %-12s", flight.getDestinationCity(), res.getSeatNumberChecked().get(i), flight.getPrice());
            System.out.println();
            System.out.println("|====================================================================================================|");
            System.out.println();

        }
    }

    public void changeSeat() {
        String reservationId = valid.checkString("Enter your booking code: ");
        ReservationChecked reser = findReservationCheckedById(reservationId);
        if (reser == null) {
            System.err.println("You have not checkIn or No reservations found with this code !!!");
            return;
        }

        Flight flight = reser.getFlight();
        //check
        boolean flag = false;
        for (Flight f : flightsNoStart) {
            if (f.equals(flight)) {
                flag = true;
            }
        }
        if (!flag) {
            System.err.println("The check-in deadline has passed");
            return;
        }
        // Kiểm tra số lượng ghế sẵn có  trên chuyến bay và phân bổ ghế
        List<Seat> availableSeats = findAllAvailableSeat(flight);
        if (availableSeats.isEmpty()) {
            System.err.println("All seats have been booked. Cannot perform check-in !!!");
            return;
        }
        displayStatusSeat(flight);
        while (true) {
            System.out.println("Seat in reservationID: " + reser.getSeatNumberChecked().toString());
            String seatNumber = valid.checkString("\nEnter seat number you want to change: ");
            if (!reser.getSeatNumberChecked().contains(seatNumber)) {
                System.err.println("Invalid seat selection! Please try again");
                continue;
            }
            int index = reser.getSeatNumberChecked().indexOf(seatNumber);
            Seat oldSeat = new Seat();
            oldSeat = getSeatByNumber(seatNumber, reser.getFlight().getSeats());
            if (oldSeat == null) {
                System.err.println("Not find seat");
                continue;
            }
            String newSeatNumber = valid.checkString("Enter new seat: ");
            Seat selectSeat = getSeatByNumber(newSeatNumber, availableSeats);
            if (selectSeat == null) {
                System.err.println("Not find seat");
                continue;
            }
            reser.getSeatNumberChecked().set(index, newSeatNumber);
            oldSeat.isFlase();
            selectSeat.occupy();
            System.out.println("Change Seat successfully, new boarding passes: ");

            System.out.println("|====================================================================================================|");
            System.out.println("|                                            AIRLINE TICKET                                          |");
            System.out.println("|====================================================================================================|");
            System.out.printf("|ID Passenger:%-25s            |***********Ariplane ID: %10s***************|", reser.getReservationId(), flight.getFlightNumber());
            System.out.println();
            System.out.printf("|Name:        %-25s            |Contact:%-40s |", reser.getPassenger().getNamePassengerToFlight().get(index), reser.getPassenger().getContactInfo());
            System.out.println();
            System.out.printf("|Departure:   %-25s            |Departure Date and Time:%-25s|", flight.getDepartureCity(), flight.getDepartureTime());
            System.out.println();
            System.out.printf("|Destination: %-25s            |Seat:  %-41s |", flight.getDestinationCity(), reser.getSeatNumberChecked().get(index));
            System.out.println();
            System.out.println("|====================================================================================================|");
            System.out.println();

            break;
        }
    }

    public void printAvailableSeats() {
        String flightNumber = valid.checkString("Enter flight code: ");
        Flight f = getFlightByFlightNumber(flightNumber, flights);
        System.out.println("List of available seats in flight: ");
        //displayAvaiSeatList(availableSeats);
        displayStatusSeat(f);
    }

    // lấy ra các flight dateTime: dd/MM/yyyy HH:mm 
    // suy ra được 12h trước đó và 12h sau đó, lấy đc list trong khoảng đó.
    public List<Flight> getFlightsWithin12HoursOfDeparture(String dateString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime inputDateTime = LocalDateTime.parse(dateString, formatter);

        // Tính toán thời điểm 12 giờ trước và sau giờ bay
        LocalDateTime beforeDeparture = inputDateTime.minusHours(12);
        LocalDateTime afterDeparture = inputDateTime.plusHours(12);

        List<Flight> results = new ArrayList<>();

        for (Flight flight : flightsNoStart) {
            LocalDateTime flightDateTime = LocalDateTime.parse(flight.getDepartureTime(), formatter);
            //
            if (flightDateTime.isAfter(beforeDeparture) && flightDateTime.isBefore(afterDeparture)) {
                results.add(flight);
            }
        }

        return results;
    }

    public List<Crew> getCrewFree(List<Flight> result) {
        List<Crew> CrewsFree = new ArrayList<>();
        CrewsFree.addAll(listCrews);
        for (Flight f : result) {
            CrewsFree.removeAll(f.getAssignedCrewMembers());
        }
        return CrewsFree;
    }

    // Function 4: Crew Management and Administrator Access
    public void manageCrewAssignments() {

        System.out.println("Crew management and job assignment");

        // Hiển thị danh sách chuyến bay có sẵn để chọn
        System.out.println("List of available flights: ");
        displayFlightsNoStarList();

        String flightNumber = valid.checkString("Select flight number: ");
        // Tìm chuyến bay dựa trên mã chuyến bay
        Flight selectedFlight = getFlightByFlightNumber(flightNumber, flightsNoStart);

        ////////////////////////////
        if (selectedFlight == null) {
            System.err.println("No flights were found with this code.");
            return;
        }

        //getListFlightByDate()
        List<Flight> result = getFlightsWithin12HoursOfDeparture(selectedFlight.getDepartureTime());

        //getCrewFree
        List<Crew> CrewsFree = getCrewFree(result);

        if (selectedFlight.getAssignedCrewMembers().size() < 1) {
            System.out.println("Each flight includes pilots, flight attendants, and ground staff:");
            boolean flag = true;
            int count = 0;
            while (count != 2) {

                System.out.println("List of Pilots:");
                // display pitlot có sẵn
                displayCrewsByRole("pilot", CrewsFree);
                System.out.println("Choose at least 2 pilot !");
                String crewMemberID = valid.checkCrewId("Enter pilot id to assign: ", selectedFlight.getAssignedCrewMembers());
                // Kiểm tra xem phi hành đoàn có tồn tại trong danh sách hay không
                Crew selectedCrewMember = valid.getCrewByID(crewMemberID, CrewsFree);
                if (selectedCrewMember == null || !selectedCrewMember.getRole().equals("pilot")) {
                    System.err.println("Pilot not found. Please re-enter Pilot.");
                    continue;
                } else {
                    selectedFlight.assignCrewMember(selectedCrewMember);
                    System.out.println("Successful assignment.");
                    count++;
                    CrewsFree.remove(selectedCrewMember);
                }
            }

            int NumbersAttendants = valid.checkInt("Enter numbers attendants you want to add: ", 3, 10);
            for (int i = 0; i < NumbersAttendants; i++) {
                System.out.println("List of flight attendants:");
                // display flight attendants có sẵn
                displayCrewsByRole("flight attendant", CrewsFree);
                String crewMemberID = valid.checkCrewId("Enter flight attendant id for assignment: ", selectedFlight.getAssignedCrewMembers());
                // Kiểm tra xem phi hành đoàn có tồn tại trong danh sách hay không
                Crew selectedCrewMember = valid.getCrewByID(crewMemberID, CrewsFree);
                if (selectedCrewMember == null || !selectedCrewMember.getRole().equals("flight attendant")) {
                    System.err.println("Flight attendant not found. Please re-enter flight attendant.");
                    i--;
                } else {
                    selectedFlight.assignCrewMember(selectedCrewMember);
                    System.out.println("Successful assignment.");
                    CrewsFree.remove(selectedCrewMember);
                }
            }

//            while (flag) {
//                System.out.println("List of flight attendants:");
//                // display flight attendants có sẵn
//                displayCrewsByRole("flight attendant", CrewsFree);
//                String crewMemberID = valid.checkCrewId("Enter flight attendant id for assignment: ", selectedFlight.getAssignedCrewMembers());
//                // Kiểm tra xem phi hành đoàn có tồn tại trong danh sách hay không
//                Crew selectedCrewMember = valid.getCrewByID(crewMemberID, CrewsFree);
//                if (selectedCrewMember == null || !selectedCrewMember.getRole().equals("flight attendant")) {
//                    System.out.println("Flight attendant not found. Please re-enter flight attendant.");
//                    continue;
//                } else {
//                    selectedFlight.assignCrewMember(selectedCrewMember);
//                    System.out.println("Successful assignment.");
//                    flag = false;
//                }
//            }
//            flag = true;
            while (flag) {
                System.out.println("List of ground staff: ");
                // display pitlot có sẵn
                displayCrewsByRole("ground staff", CrewsFree);
                String crewMemberID = valid.checkCrewId("Enter ground staff id to assign: ", selectedFlight.getAssignedCrewMembers());
                // Kiểm tra xem phi hành đoàn có tồn tại trong danh sách hay không
                Crew selectedCrewMember = valid.getCrewByID(crewMemberID, CrewsFree);
                if (selectedCrewMember == null || !selectedCrewMember.getRole().equals("ground staff")) {
                    System.err.println("Ground staff not found. Please re-enter ground staff.");
                    continue;
                } else {
                    selectedFlight.assignCrewMember(selectedCrewMember);
                    System.out.println("Successful assignment !!!");
                    flag = false;
                }
            }
        } else {
            // Nhập thông tin phi hành đoàn để phân công
            String inputRole = valid.checkRole("Enter role crew you want to add more: ");
            displayCrewsByRole(inputRole, CrewsFree);
            String crewMemberID = valid.checkCrewId("Enter crew id to assign: ", selectedFlight.getAssignedCrewMembers());
            // Kiểm tra xem phi hành đoàn có tồn tại trong danh sách hay không
            Crew selectedCrewMember = valid.getCrewByID(crewMemberID, CrewsFree);
            if (selectedCrewMember == null) {
                System.err.println("No crew with this name was found.");
                return;
            }
            // Phân công công việc cho phi hành đoàn trên chuyến bay
            if (selectedCrewMember != null) {
                selectedFlight.assignCrewMember(selectedCrewMember);
                System.out.println("Successful assignment.");
            } else {
                System.err.println("Does not exist or Duplicate");
                System.err.println("Assignment failed");
                return;
            }
        }

    }

    public void CrewsInFlight() {
        System.out.println("List of available flights: ");
        displayFlightList();

        String flightNumber = valid.checkString("Select flight number: ");
        // Tìm chuyến bay dựa trên mã chuyến bay
        Flight selectedFlight = getFlightByFlightNumber(flightNumber, flights);
        if (selectedFlight == null) {
            System.err.println("No flights were found with this number.");
            return;
        }
        if (selectedFlight.getAssignedCrewMembers().isEmpty()) {
            System.err.println("No one has been assigned");
            return;
        }
        System.out.println("Members of flight number: " + selectedFlight.getFlightNumber() + " including: ");
        for (Crew cr : selectedFlight.getAssignedCrewMembers()) {
            System.out.println(cr.toString());
        }
    }

    public void addCrew() {
        // Nhập thông tin phi hành đoàn mới
        String id = valid.checkCrewId("Enter the id of the new crew:", listCrews);
        String name = valid.checkString("Enter new crew names: ");
        int age = valid.checkInt("Enter crew age:", 18, 100);
        String role = valid.checkRole("Enter crew roles (pilot, flight attendant, ground staff): ");
        // Tạo phi hành đoàn mới và thêm vào danh sách
        Crew newCrew = new Crew(id, name, age, role);
        if (role.equals("pilot")) {
            String licenseNumber = valid.checkLicenseNumber("Import license: ");
            //     public Pilot(String licenseNumber, String id, String name, int age, String role) {
            newCrew = new Pilot(licenseNumber, id, name, age, role);
        } else if (role.equals("flight attendant")) {
            int yearsOfExperience = valid.checkInt("Enter years of experience: ", 0, 100);
            newCrew = new FlightAttendant(yearsOfExperience, id, name, age, role);
        } else {
            String department = valid.checkLDepartment("Enter department: ");
            newCrew = new GroundStaff(department, id, name, age, role);
        }

        listCrews.add(newCrew);
        System.out.println("Added crew successfully.");
    }

    public void updateCrew() {
        Crew cr = getCrewByID(valid.checkString("Enter id crew to update: "), listCrews);
        if (cr == null) {
            System.err.println("Does not find crew.");
            return;
        }
        System.out.println("Information of crew: ");
        System.out.println(cr);

        String name = valid.checkString("Enter new crew names: ");
        int age = valid.checkInt("Enter crew age: ", 18, 100);
        String role = valid.checkRole("Enter crew roles (pilot, flight attendant, ground staff): ");
        // Tạo phi hành đoàn mới và thêm vào danh sách
        cr.setAge(age);
        cr.setName(name);
        cr.setRole(role);
        if (role.equals("pilot")) {
            Pilot pl = (Pilot) cr;
            String licenseNumber = valid.checkLicenseNumber("Import license: ");
            pl.setLicenseNumber(licenseNumber);
        } else if (role.equals("flight attendant")) {
            int yearsOfExperience = valid.checkInt("Enter years of experience: ", 0, 100);
            FlightAttendant fa = (FlightAttendant) cr;
            fa.setYearsOfExperience(yearsOfExperience);
        } else {
            String department = valid.checkLDepartment("Enter department: ");
            GroundStaff dp = (GroundStaff) cr;
            dp.setDepartment(department);
        }

    }

    public void addCrewAdmin() {
        // Nhập thông tin phi hành đoàn mới
        String id = valid.checkCrewId("Enter the id of the new crew: ", listCrews);
        String name = valid.checkString("Enter new crew names: ");
        int age = valid.checkInt("Enter crew age: ", 18, 100);
        String role = valid.checkRole1("Enter crew roles (pilot, flight attendant, ground staff, admin): ");
        // Tạo phi hành đoàn mới và thêm vào danh sách
        Crew newCrew = new Crew(id, name, age, role);
        if (role.equals("pilot")) {
            String licenseNumber = valid.checkLicenseNumber("Import license: ");
            //     public Pilot(String licenseNumber, String id, String name, int age, String role) {
            newCrew = new Pilot(licenseNumber, id, name, age, role);
        } else if (role.equals("flight attendant")) {
            int yearsOfExperience = valid.checkInt("Enter years of experience: ", 0, 100);
            newCrew = new FlightAttendant(yearsOfExperience, id, name, age, role);
        } else if (role.equals("ground staff")) {
            String department = valid.checkLDepartment("Enter department: ");
            newCrew = new GroundStaff(department, id, name, age, role);
        } else if (role.equals("admin")) {
            String password = valid.checkPassWord("Enter password: ");
            newCrew = new Admin(password, id, name, age, role);
        }

        listCrews.add(newCrew);
        System.out.println("Added crew successfully.");
    }

    public boolean checkAdmin() {
        String tk = valid.checkString("Enter account: ");
        String mk = valid.checkString("Enter password: ");
        if (isAdmin(tk, mk)) {
            // Đây là nơi bạn thực hiện các tác vụ quản lý hệ thống chuyến bay sau khi đăng nhập thành công
            System.out.println("Logged in successfully! You have permission to manage the flight system.");
            return true;
        } else {
            System.err.println("Login failed! Please try again later.");
            return false;
        }
    }

    public boolean isAdmin(String tk, String mk) {
        Crew crew = getCrewByID(tk, listCrews);
        if (crew instanceof Admin) {
            Admin ad = (Admin) crew;
            if (ad.getRole().equals("admin") && ad.getPassWord().equals(mk)) {
                return true;
            }
        }
        System.err.println("Wrong account or password");
        return false;
    }

    public void displayCrewMembers() {
        for (Crew crewMember : listCrews) {
            System.out.println(crewMember);
        }
    }

    public void displayCrewsByRole(String role, List<Crew> listCrews) {
        for (Crew crewMember : listCrews) {
            if (crewMember.getRole().equals(role)) {
                System.out.println(crewMember);
            }
        }
    }

    public Reservation findReservationById(String reservationId) {
        for (Reservation r : reservations) {
            if (r.getReservationId().equals(reservationId)) {
                return r;
            }
        }
        return null;
    }

    public ReservationChecked findReservationCheckedById(String reservationId) {
        for (ReservationChecked r : lReservationChecked) {
            if (r.getReservationId().equals(reservationId)) {
                return r;
            }
        }
        return null;
    }

    public List<Seat> findAllAvailableSeat(Flight flight) {
        List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : flight.getSeats()) {
            if (!seat.isOccupied()) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;  // Trả về null nếu không còn ghế trống trên chuyến bay
    }

    public void saveAvailbeSeatWithFlightNumber(Flight flight) {
        String availableSeats = flight.getFlightNumber() + ":";
        List<String> result = new ArrayList<>();
        for (Seat seat : flight.getSeats()) {
            if (!seat.isOccupied()) {
                availableSeats += seat.toString();
            }
        }
        lSeats.add(availableSeats);

    }

    public void displayStatusSeat(Flight flight) {
        int count = 0;
        for (Seat s : flight.getSeats()) {
            count++;
            if (!s.isIsOccupied()) {
                System.out.printf("%-6s", s.getSeatNumber() + " ");
            } else {
                System.out.printf("%-6s", "Sold" + " ");
            }
            if (count == 5) {
                System.out.println();
                count = 0;
            }
        }
    }

    public Flight getFlightByFlightNumber(String flightNumber, List<Flight> flights) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equals(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    public Seat getSeatByNumber(String number, List<Seat> seats) {
        for (Seat s : seats) {
            if (s.getSeatNumber().equals(number)) {
                return s;
            }
        }
        return null;
    }

    public void displayFlightList() {
        if (flights.isEmpty()) {
            System.out.println("Flight list is empty.");
            return;
        }
        System.out.println("Flight list: ");

        for (Flight f : flights) {
            // id - diem di - diem den
            System.out.println(f.getFlightNumber() + ": " + f.getDepartureCity() + " ----> " + f.getDestinationCity() + "| " + f.getDepartureTime() + " ----> " + f.getArrivalTime());
        }
    }

    public void displayFlightsNoStarList() {
        if (flightsNoStart.isEmpty()) {
            System.out.println("Flight list is empty.");
            return;
        }
        System.out.println("List of flights not yet flown:");

        for (Flight f : flightsNoStart) {
            // id - diem di - diem den
            System.out.println(f.getFlightNumber() + ": " + f.getDepartureCity() + "----> " + f.getDestinationCity() + "| " + f.getDepartureTime() + "----> " + f.getArrivalTime());
        }
    }

    public void displayAvaiSeatList(List<Seat> list) {
        System.out.println("Seat list:");
        for (Seat seat : list) {
            System.out.println(seat.getSeatNumber());
        }
    }

    public Crew getCrewByID(String crewID, List<Crew> lCrew) {
        for (Crew cr : lCrew) {
            if (cr.getId().equals(crewID)) {
                return cr;
            }
        }
        return null;
    }

    //save to data and load data
    public void saveData() {

        fm.saveToFile(flights, "flights.dat");
        fm.saveToFile(reservations, "passengers.dat");
        fm.saveToFile(lReservationChecked, "passengersDone.dat");
        fm.saveToFile(listCrews, "crews.dat");
        for (Flight f : flights) {
            saveAvailbeSeatWithFlightNumber(f);
        }
        fm.saveToFile(lSeats, "seats.dat");
        System.out.println("Success !!!");
    }

    public void loadData() {
        loadDataCrews(fm.loadFromFile("crews.dat"));
        loadDataFlights(fm.loadFromFile("flights.dat"));
        loadDataReservation(fm.loadFromFile("passengers.dat"));
        loadDataReservation(fm.loadFromFile("passengersDone.dat"));
        loadDataSeats(fm.loadFromFile("seats.dat"));
        FlightsNoStart();
    }

    public void loadDataReservation(List<String> dataFile) {
        for (String line : dataFile) {
            String[] data = line.split(", ");
            //return reservationId + ", " + flight + ", " + passenger + ',' seatnumber ;
            // +=>> R2, F0021, Tran Huy Hanh, 0963254123, 4, Hanh | Tan | Thai | Quy , S1- S2- S3- S4
            String reservationID = data[0].trim();
            Flight fl = getFlightByFlightNumber(data[1], flights);
            String passengerName = data[2].trim();
            String contactI4 = data[3].trim();
            int TotalSeat = Integer.parseInt(data[4].trim());
            String[] name = data[5].split("\\|");
            List<String> TotalName = new ArrayList<>();

            for (int i = 0; i < TotalSeat; i++) {
                TotalName.add(name[i].trim());
            }

            if (data.length == 7) {
                String[] seat = data[6].split("\\-");
                List<String> TotalSeats = new ArrayList<>();
                for (int i = 0; i < TotalSeat; i++) {
                    TotalSeats.add(seat[i].trim());
                }

                lReservationChecked.add(new ReservationChecked(TotalSeats, reservationID, fl, new Passenger(passengerName, contactI4, TotalSeat, TotalName)));
            } else {
                reservations.add(new Reservation(reservationID, fl, new Passenger(passengerName, contactI4, TotalSeat, TotalName)));
            }

        }
    }

    private void loadDataCrews(List<String> dataFile) {
        for (String line : dataFile) {
            //return id + " | " + name + " | " + age + " | " + role;
            String[] data = line.split("\\|");

            String id = data[0].trim();
            String name = data[1].trim();
            int age = Integer.parseInt(data[2].trim());
            String role = data[3].trim();
            //
            Crew newCrew = new Crew(id, name, age, role);
            if (role.equals("pilot")) {
                newCrew = new Pilot(data[4].trim(), id, name, age, role);
            } else if (role.equals("flight attendant")) {
                newCrew = new FlightAttendant(Integer.parseInt(data[4].trim()), id, name, age, role);
            } else if (role.equals("ground staff")) {
                newCrew = new GroundStaff(data[4].trim(), id, name, age, role);
            } else {
                newCrew = new Admin(data[4].trim(), id, name, age, role);
            }
            //
            listCrews.add(newCrew);
        }
    }

    public void loadDataFlights(List<String> dataFile) {
        for (String line : dataFile) {
            //F0000, HCM, HaNoi, 24/12/2023 11:30, 25/12/2023 01:45, 36,
            String[] data = line.split(",");

            String flightNumber = data[0].trim();
            String depatureCity = data[1].trim();
            String destinationCity = data[2].trim();
            String departureTime = data[3].trim();
            String arrivalTime = data[4].trim();
            //tao list de luu
            List<Seat> seats = new ArrayList<>();
            int totalSeat = Integer.parseInt(data[6].trim());
            int seatID = 1;
            for (int i = 0; i < totalSeat; i++) {
                String seatNumber = "S" + (seatID++);
                Seat newSeat = new Seat(seatNumber);
                seats.add(newSeat);
            }
            String price = data[5].trim();
            Flight f = new Flight(flightNumber, depatureCity, destinationCity, departureTime, arrivalTime, price, seats, listCrews);
            flights.add(f);
            if (data.length > 7 && !data[7].trim().isEmpty()) {
                String[] idCrews = data[7].split("\\|");
                int numberOfDash = idCrews.length;
                for (int i = 0; i < numberOfDash; i++) {
                    f.assignCrewMember(getCrewByID(idCrews[i].trim(), listCrews));
                }
            }

            //flightNumber + ", " + departureCity + ", " + destinationCity + ", " + departureTime + ", " + arrivalTime + ", " + seats.size()+", "+crewsInFlight    
            //, -C1112 - C1232 - C1293
        }
    }

    public void sort() {
        List<Flight> resultList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Comparator<Flight> c = new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                LocalDateTime d3 = LocalDateTime.parse(o1.getDepartureTime(), formatter);
                LocalDateTime d4 = LocalDateTime.parse(o2.getDepartureTime(), formatter);
                int d1 = o1.getDepartureCity().compareTo(o2.getDepartureCity());
                if (d1 == 0) {
                    return d3.compareTo(d4);
                } else {
                    return d1;
                }
            }
        };
                resultList.addAll(flightsNoStart);
        Collections.sort(resultList, c);

        System.out.println("Flight list: ");
        for (Flight flight : resultList) {
            // id - diem di - diem den
            System.out.println(flight.getDepartureTime() + " - " + flight.getFlightNumber() + " - " + flight.getDepartureCity() + " ---> " + flight.getDestinationCity());
        }
    }

   
    public void displayFlightListByDesending() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Flight> resultList = new ArrayList<>();
        Comparator<Flight> c = new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                try {
                    LocalDateTime d1 = LocalDateTime.parse(o1.getDepartureTime(), formatter);
                    LocalDateTime d2 = LocalDateTime.parse(o2.getDepartureTime(), formatter);
                    int compare = d2.compareTo(d1);
                    if (compare == 0) {
                        return o2.getFlightNumber().compareTo(o1.getFlightNumber());
                    } else {
                        return d2.compareTo(d1);
                    }
                } catch (Exception e) {
                    System.err.println("Incorrect date time must input by format dd/MM/yyyy HH:mm ! Please enter again !");
                    return 0;
                }

            }
        };
        resultList.addAll(flightsNoStart);
        Collections.sort(resultList, c);

        System.out.println("Flight list: ");
        for (Flight flight : resultList) {
            // id - diem di - diem den
            System.out.println(flight.getDepartureTime() + " - " + flight.getFlightNumber() + " - " + flight.getDepartureCity() + " ---> " + flight.getDestinationCity());
        }

    }

    private void loadDataSeats(List<String> dataFile) {
        for (String line : dataFile) {
            String[] data = line.split(":");
            Flight f = getFlightByFlightNumber(data[0].trim(), flights);
            String[] seatData = data[1].trim().split(",");
            //F0000:S4, S5, S7, S8, S9, S10, S11, S12, S13,
            if (f != null) {
                for (Seat seatNho : f.getSeats()) {
                    seatNho.occupy();
                }

                int totalSeats = f.getSeats().size();
                int seatID = 1;
                for (int i = 0; i < totalSeats; i++) { //ghe1 - ghe cuối
                    //String string = f.getSeats().get(i).getSeatNumber().trim();
                    String seatNumber = "S" + (seatID++);
                    for (String seat : seatData) {
                        if (seat.trim().equals(seatNumber.trim())) {
                            f.getSeats().get(i).isFlase();
                        }
                    }

                }

            }

        }
    }

    public List<Flight> FlightsNoStart() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        for (Flight f : flights) {
            LocalDateTime dateTime = LocalDateTime.parse(f.getDepartureTime(), formatter);
            if (now.isBefore(dateTime)) {
                flightsNoStart.add(f);
            }
        }
        return flightsNoStart;
    }

}
