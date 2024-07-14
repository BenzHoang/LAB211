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
import model.Brand;
import model.Vehicle;

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
    public void displayVehicles(List<Vehicle> vehicleList ) {
        System.out.println("===============================LIST OF VEHICLES ======================================");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s| %-20s| %-10s| %-15s| %-13s| %-15s\n", "ID", "Type", "Name", "Color", "Price", "Brand");
        System.out.println("--------------------------------------------------------------------------------------");

        for (Vehicle vehicle : vehicleList) {
            System.out.printf("%-10s| %-20s| %-10s| %-15s| %-13d| %-15s\n", vehicle.getId(), vehicle.getType(), vehicle.getName(), vehicle.getColor(), vehicle.getPrice(), vehicle.getBrand().getBrandName());
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

    public String checkValidDate1(String msg) {
        String dateFormat = "dd/MM/yyyy";
        DateFormat sdf = new SimpleDateFormat(dateFormat);

        while (true) {
            String dateStr = checkString(msg);
            try {
                sdf.parse(dateStr);
            } catch (ParseException ex) {
                System.err.println("Incorrect date must input by format dd/MM/yyyy! please enter again: ");
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
                    System.out.println("Expiration date must large than production date ! Please enter again !");
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
                System.out.println("Please enter again!");
                continue;
            }
            return input_raw;
        }
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

    public double checkDouble(String msg, double min, double max) {

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

    public String checkBrandID(String msg, List<Brand> brands) {
        while (true) {
            String input = checkString(msg);
            // Fxyzt 
            // [F] 
            // {4} 
            String flightNumberPattern = "^B\\d{4}$";
            if (!input.matches(flightNumberPattern)) {
                System.err.println("The brand number must be format Fxyzt ! Please enter again !");
                continue;
            }
            if (getBrandByID(input, brands) != null) {
                System.err.println("The brand number must be not existed in system! Please enter again !");
                continue;

            }

            return input;
        }
    }

    public Brand getBrandByID(String brandID, List<Brand> brands) {
        for (Brand b : brands) {
            if (b.getBrandID().equals(brandID)) {
                return b;
            }
        }
        return null;
    }

    public String checkVehicleID(String msg, List<Vehicle> vehicles) {
        while (true) {
            String input = checkString(msg);
            // Fxyzt 
            // [F] 
            // {4} 
            String flightNumberPattern = "^V\\d{4}$";
            if (!input.matches(flightNumberPattern)) {
                System.err.println("The vehicle id must be format Fxyzt ! Please enter again !");
                continue;
            }
            if (getVehicleID(input, vehicles) != null) {
                System.err.println("The vehicle id must be not existed in system! Please enter again !");
                continue;

            }

            return input;
        }
    }

    public Vehicle getVehicleID(String vehicleID, List<Vehicle> vehicles) {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(vehicleID)) {
                return v;
            }
        }
        return null;
    }

}
