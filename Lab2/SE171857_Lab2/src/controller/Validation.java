package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import model.Crew;
import model.Flight;

public class Validation {

    public Scanner sc = new Scanner(System.in);

    public boolean checkYesOrNo(String msg) {
        while (true) {
            String input = checkString(msg);
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must input Y or N to select option");
                continue;
            }
        }
    }

   public String checkValidDate(String msg) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime now = LocalDateTime.now();
    
    while (true) {
        String dateStr = checkString(msg);
        
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            
            if (now.isAfter(dateTime)) {
                System.err.println("Past time, enter a time in the future or present.");
                continue;
            }
        } catch (Exception e) {
            System.err.println("Invalid time, please enter in format dd/MM/yyyy HH:mm ! Please try again.");
            continue;
        }
        
        return dateStr;
    }
}   
   public String checkBeforeDate(String msg) {
        String dateFormat = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        while (true) {
            String dateStr = checkString(msg);
            try {
                sdf.parse(dateStr);
            } catch (ParseException e) {
                System.err.println("Incorrect date must input by format MM/dd/yyyy ! Please enter again !");
                continue;
            }
            return dateStr;
        }

    }
   


    public String checkAfterDate(String msg, String pd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        while (true) {
            String dateStr = checkString(msg);
            try {
                LocalDateTime d1 = LocalDateTime.parse(dateStr, formatter);
                LocalDateTime d2 = LocalDateTime.parse(pd, formatter);
                if (d1.compareTo(d2) < 0) {
                    System.err.println("Expiration date must large than production date ! Please enter again !");
                    continue;
                }
                return dateStr;
            } catch (Exception e) {
                System.err.println("Incorrect date time must input by format dd/MM/yyyy HH:mm ! Please enter again !");
                continue;
            }

        }
    }

    public String checkString(String msg) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            System.out.println(msg);
            // allow user input a string 
            String input_raw = sc.nextLine().trim();
            // input == null or do dai = 0 => rong 
            if (input_raw == null || input_raw.length() == 0) {
                // error
                System.err.println("Must input a string not empty !!!");
                System.err.println("Please enter again!");
                continue;
            }
            return input_raw;
        }
    }

    public String checkLicenseNumber(String msg) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            // allow user input a string 
            String input_raw = checkString(msg);
            // input == null or do dai = 0 => rong 
            if (input_raw == null || input_raw.length() == 0) {
                // error
                System.err.println("Must input a string not empty !!!");
                System.err.println("Please enter again!");
                continue;
            }
            String licenseNumber = "^P\\d{4}$";
            if (!input_raw.matches(licenseNumber)) {
                System.err.println("The license number must be format Pxyzt ! Please enter again !");
                continue;
            }
            return input_raw;
        }
    }
    public String checkPassWord(String msg) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            // allow user input a string 
            String input_raw = checkString(msg);
            // input == null or do dai = 0 => rong 
            if (input_raw == null || input_raw.length() == 0) {
                // error
                System.err.println("Must input a string not empty !!!");
                System.err.println("Please enter again!");
                continue;
            }
            String licenseNumber = "^.*(?=.{6,})(?=.*[A-Z]).*$";
            if (!input_raw.matches(licenseNumber)) {
                System.err.println("Password is invalid, at least 6 characters and has at least 1 uppercase letter");
                continue;
            }
            return input_raw;
        }
    }

    public String checkLDepartment(String msg) {
        // vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {
            System.out.println(msg);
            // allow user input a string 
            String input_raw = sc.nextLine().trim();
            // input == null or do dai = 0 => rong 
            if (input_raw == null || input_raw.length() == 0) {
                // error
                System.err.println("Must input a string not empty !!!");
                System.err.println("Please enter again!");
                continue;
            }
            String licenseNumber = "^D\\d{4}$";
            if (!input_raw.matches(licenseNumber)) {
                System.err.println("The department number must be format Dxyzt ! Please enter again !");
                continue;
            }
            return input_raw;

        }
    }

    public String checkFlightNumber(String msg, List<Flight> flights) {
        while (true) {
            String input = checkString(msg);
            // Fxyzt 
            // [F] 
            // {4} 
            String flightNumberPattern = "^F\\d{4}$";
            if (!input.matches(flightNumberPattern)) {
                System.err.println("The flight number must be format Fxyzt ! Please enter again !");
                continue;
            }
            if (getFlightByFlightNumber(input, flights) != null) {
                System.err.println("The flight number must be not existed in system! Please enter again !");
                continue;

            }

            return input;
        }
    }

    public String checkCrewId(String msg, List<Crew> crews) {
        while (true) {
            String input = checkString(msg);
            // Cxyzt 
            // [C] 
            // {4} 
            String crewIdPattern = "^C\\d{4}$";
            if (!input.matches(crewIdPattern)) {
                System.err.println("The crew id must be format Cxyzt ! Please enter again !");
                continue;
            }
            if (getCrewByID(input, crews) != null) {
                System.err.println("The crew id must be not existed in system! Please enter again !");
                continue;

            }

            return input;
        }
    }
    

    public Crew getCrewByID(String id, List<Crew> crewsList) {
        for (Crew f : crewsList) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public String checkRole(String msg) {
        while (true) {
            String input = checkString(msg);
            // pilot, flight attendant, ground staff, admin
            if (input.equals("pilot") || input.equals("flight attendant") || input.equals("ground staff")) {
                return input;
            }
            System.err.println("Must enter 1 of these 3 roles (pilot, flight attendant, ground staff)! try again");
        }
    }
    public String checkRole1(String msg) {
        while (true) {
            String input = checkString(msg);
            // pilot, flight attendant, ground staff, admin
            if (input.equals("pilot") || input.equals("flight attendant") || input.equals("ground staff")|| input.equals("admin")) {
                return input;
            }
            System.err.println("Must enter 1 of these 3 roles (pilot, flight attendant, ground staff, admin)! try again");
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

    public int checkInt(String msg, int min, int max) {

// vong lap su dung de nguoi dung nhap den khi dung 
        while (true) {

            // allow user input a string 
            String input_raw = checkString(msg);

            try {
                // loi nhap sai dinh dang so 
                int input = Integer.parseInt(input_raw);
                // loi nhap ngoai range cho phep
                if (input < min || input > max) {
                    System.err.println("Must input a number large than " + min + " and less than " + max);
                    continue;
                }
                return input;
            } catch (NumberFormatException e) {

                System.err.println("Must enter a number");
                continue;
            }

        }
    }

    public String checkValidTime(String msg) {
        String hourPattern = "([01]?[0-9]|2[0-3])";
        String minutePattern = "[0-5][0-9]";
        while (true) {
            String inputTime = checkString(msg);

            String[] parts = inputTime.split(":");

            if (parts.length != 2) {
                System.err.println("Format must be HH:mm ");
                continue;
            }

            if (!parts[0].matches(hourPattern)) {
                System.err.println("Invalid hour value. Format must be HH:mm ");
                continue;
            }

            if (!parts[1].matches(minutePattern)) {
                System.err.println("Invalid minute value. Format must be HH:mm ");
                continue;
            }

            if (parts[0].length() != 2 || parts[1].length() != 2) {
                System.err.println("Invalid time length. Format must be HH:mm ");
                continue;
            }

            try {
                LocalTime.parse(inputTime);
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing time");
                continue;
            }

            return inputTime;
        }

    }

}
