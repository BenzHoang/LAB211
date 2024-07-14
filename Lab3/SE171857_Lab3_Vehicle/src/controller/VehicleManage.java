/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Brand;
import model.Vehicle;

/**
 *
 * @author ASUS
 */
public class VehicleManage {

    public List<Brand> brandList; // Danh sách thương hiệu
    public List<Vehicle> vehicleList; // Danh sách các xe
    Validation valid = new Validation();
    FileManage fm = new FileManage();

    public VehicleManage() {
        this.brandList = new ArrayList<>();
        this.vehicleList = new ArrayList<>();
    }

    public void addBrand() {
        while (true) {//brandID + ", " + brandName + ", " + countryBrand;
            String brandID = valid.checkBrandID("Enter brand ID: ", brandList);
            String brandName = valid.checkString("Enter brand name: ");
            String countryBrand = valid.checkString("Enter brand country: ");
            brandList.add(new Brand(brandID, brandName, countryBrand));
            System.out.println("Successfully !!!");
            System.out.println("The brand has been added to the system !");
            if (valid.checkYesOrNo("Do you want to add more(Y/N)? ")) {
                continue;
            }
            return;
        }
    }

    public void addVehicle() {
        //type + ", " + name + ", " + color + ", " + price + ", " + brand + ", " + productYear;
        while (true) {
            String idVehicle = valid.checkVehicleID("Enter id of vehicle: ", vehicleList);
            String typeVehicle = valid.checkString("Enter type of vehicle: ");
            String nameVehicle = valid.checkString("Enter name of vehicle: ");
            String colorVehicle = valid.checkString("Enter color of vehicle: ");
            int priceVehicle = valid.checkInt("Enter price of vehicle: ", 0, Integer.MAX_VALUE);
            String brandID = valid.checkString("Enter brand ID: ");
            String productYear = valid.checkValidDate1("Enter product year: ");

            Brand brand = getBrandByID(brandID);
            if (brand == null) {
                System.out.println("Brand with ID " + brandID + " not found !!!");
                return;
            }
            vehicleList.add(new Vehicle(idVehicle, typeVehicle, nameVehicle, colorVehicle, priceVehicle, brand, productYear));
            System.out.println("Successfully !!!");
            System.out.println("The vehicle has been added to the system !");
            if (valid.checkYesOrNo("Do you want to add more(Y/N)? ")) {
                continue;
            }
            return;
        }
    }

    public void updateVehicle() {
        Vehicle vehicle = getVehicleByID(valid.checkString("Enter ID to update: "));
        //idVehicle, typeVehicle, nameVehicle, colorVehicle, priceVehicle, brand, productYear
        String typeVehicle = valid.checkString("Enter type of vehicle: ");
        String nameVehicle = valid.checkString("Enter name of vehicle: ");
        String colorVehicle = valid.checkString("Enter color of vehicle: ");
        int priceVehicle = valid.checkInt("Enter price of vehicle: ", 0, Integer.MAX_VALUE);
        String brandID = valid.checkString("Enter brand ID: ");
        String productYear = valid.checkValidDate1("Enter product year: ");

        // Now, update the attributes of the vehicle
        vehicle.setType(typeVehicle);
        vehicle.setName(nameVehicle);
        vehicle.setColor(colorVehicle);
        vehicle.setPrice(priceVehicle);
        vehicle.getBrand().setBrandID(brandID);
        vehicle.setProductYear(productYear);

        System.out.println("Vehicle with ID " + vehicle + " has been updated.");

    }

    public void deleteVehicle() {
        valid.displayVehicles(vehicleList);
        String idToDelete = valid.checkString("Enter the ID of the vehicle to delete: ");

        // Find the vehicle to delete
        Vehicle vehicleToDelete = getVehicleByID(idToDelete);

        if (vehicleToDelete == null) {
            System.out.println("Vehicle with ID " + idToDelete + " does not exist.");
            return;
        }

        // Confirm deletion
        boolean confirmation = valid.checkYesOrNo("Are you sure you want to delete this vehicle? (yes/no): ");

        if (confirmation) {
            vehicleList.remove(vehicleToDelete);
            System.out.println("Vehicle with ID " + idToDelete + " has been deleted.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public void searchVehicleByID() {
        String idToSearch = valid.checkString("Enter ID of the vehicle to search: ");

        // Find the vehicle with the provided ID
        Vehicle vehicleFound = getVehicleByID(idToSearch);

        if (vehicleFound != null) {
            vehicleFound.show1();
        } else {
            System.out.println("Vehicle with ID " + idToSearch + " not found.");
        }
    }


    public void searchVehicleByName() {
        String nameToSearch = valid.checkString("Enter Name of the vehicle to search: ");
        boolean found = false;

        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getName().equalsIgnoreCase(nameToSearch)) {
                System.out.println("Vehicle found:\n" + vehicle.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Vehicle with Name " + nameToSearch + " not found.");
        }
    }

    public void checkVehicle() {
        String check = valid.checkString("Enter name vehicle to check: ");
        if (vehicleExists(check)) {
            System.out.println(check + " exists.");
        } else {
            System.out.println(check + " No Vehicle Found! ");
        }
    }

    public boolean vehicleExists(String vehicleName) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getName().equalsIgnoreCase(vehicleName)) {
                return true;
            }
        }
        return false;
    }

    public void displayBrands() {
        System.out.println("-------------------------------------------------");
        System.out.println("| Brand ID     |     Name      |   Country       |");
        System.out.println("-------------------------------------------------");

        for (Brand brand : brandList) {
            System.out.printf("| %-8s     | %-8s     | %-12s     |\n", brand.getBrandID(), brand.getBrandName(), brand.getCountryBrand());
        }

        System.out.println("-------------------------------------------------");
    }

   

    public Brand getBrandByID(String brandID) {
        for (Brand brand : brandList) {
            if (brand.getBrandID().equals(brandID)) {
                return brand;
            }
        }
        return null;
    }

    public Vehicle getVehicleByID(String IDVehicle) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getId().equals(IDVehicle)) {
                return vehicle;
            }
        }
        return null;
    }

    public void saveToFile() {
        fm.saveToFile(brandList, "brands.dat");
        fm.saveToFile(vehicleList, "vehicles.dat");
        System.out.println("successfully !!!");
    }

    public void loadToFile() {
        loadBrands(fm.loadFromFile("brands.dat"));
        loadVehicle(fm.loadFromFile("vehicles.dat"));
    }

    public void loadBrands(List<String> dataFile) {
        if (brandList == null) {
            brandList = new ArrayList<>(); // Khởi tạo danh sách nếu nó chưa tồn tại
        }
        for (String line : dataFile) {
            //brandID + ", " + brandName + ", " + countryBrand;
            String[] data = line.split(", ");
            String brandID = data[0].trim();
            String brandName = data[1].trim();
            String countryBrand = data[2].trim();
            brandList.add(new Brand(brandID, brandName, countryBrand));
        }
    }

    public void loadVehicle(List<String> dataFile) {
        if (vehicleList == null) {
            vehicleList = new ArrayList<>();
        }
        for (String line : dataFile) {
            //id + ", "type + ", " + name + ", " + color + ", " + price + ", " + brand + ", " + productYear;
            String[] data = line.split(", ");
            String id = data[0].trim();
            String type = data[1].trim();
            String name = data[2].trim();
            String color = data[3].trim();
            int price = Integer.parseInt(data[4].trim());
            String brandID = data[5].trim();
            String productYear = data[6].trim();
            //Xử lý brand
            Brand brand = getBrandByID(brandID);
            if (brand == null) {
                System.out.println("Brand with ID " + brandID + " not found.");
            }
            vehicleList.add(new Vehicle(id, type, name, color, price, brand, productYear));

        }
    }

    public void updateBrands() {
        String brandIDToUpdate = valid.checkString("Enter the ID of the brand to update: ");

        // Find the brand to update
        Brand brandToUpdate = getBrandByID(brandIDToUpdate);

        if (brandToUpdate == null) {
            System.out.println("Brand with ID " + brandIDToUpdate + " does not exist.");
            return;
        }

        // Prompt the user to enter updated brand information
        String newBrandName = valid.checkString("Enter the new brand name: ");
        String newCountryBrand = valid.checkString("Enter the new country of the brand: ");

        // Update the brand's information
        brandToUpdate.setBrandName(newBrandName);
        brandToUpdate.setCountryBrand(newCountryBrand);

        System.out.println("Brand with ID " + brandIDToUpdate + " has been updated.");
    }

    public void deleteBrands() {
        String brandIDToDelete = valid.checkString("Enter the ID of the brand to delete: ");

        // Find the brand to delete
        Brand brandToDelete = getBrandByID(brandIDToDelete);

        if (brandToDelete == null) {
            System.out.println("Brand with ID " + brandIDToDelete + " does not exist.");
            return;
        }

        // Confirm deletion
        String confirmation = valid.checkString("Are you sure you want to delete this brand? (yes/no): ");

        if (confirmation.equalsIgnoreCase("yes")) {
            brandList.remove(brandToDelete);
            System.out.println("Brand with ID " + brandIDToDelete + " has been deleted.");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    public void showNameByDescending() {
        String name = valid.checkString("Enter name: ");
        List<Vehicle> sortedList = new ArrayList<>();
        for (Vehicle v : vehicleList) {
            if (v.getName().contains(name)) {
                sortedList.add(v);
            }
        }
        if (sortedList == null) {
            System.err.println("Not found vehicle");
            return;
        }

        // Sort the list of vehicles by name in descending order using a Comparator
        Collections.sort(sortedList, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return v2.getName().compareTo(v1.getName());
            }
        });

        System.out.println("Vehicles by Name (Descending Order):");
        valid.displayVehicles(sortedList);
    }

    public void showAll() {
        valid.displayVehicles(vehicleList);
    }

    public void showByPrice() {
        List<Vehicle> sortedList = new ArrayList<>();
        int price = valid.checkInt("Enter price: ", 0, Integer.MAX_VALUE);
        for (Vehicle v : vehicleList) {
            if (v.getPrice() < price) {
                sortedList.add(v);
            }
        }
        if (sortedList == null) {
            System.err.println("Not found with this price");
            return;
        }
        // Sort the list of vehicles by price in ascending order
        Collections.sort(sortedList, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return v1.getPrice() - v2.getPrice();
            }
        });

        System.out.println("Vehicles by Price (Descending Order):");
        valid.displayVehicles(sortedList);
    }

    public void showByYear() {
        List<Vehicle> sortedList = new ArrayList<>(vehicleList);

        // Sort the list of vehicles by product year in ascending order
        Collections.sort(sortedList, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return v2.getProductYear().compareTo(v1.getProductYear());
            }
        });

        System.out.println("Vehicles by Product Year (Ascending Order):");
        for (Vehicle vehicle : sortedList) {
            System.out.println(vehicle.getName() + " - Year: " + vehicle.getProductYear());
        }
    }

}
