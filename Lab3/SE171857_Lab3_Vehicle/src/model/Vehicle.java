/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author ASUS
 */
public class Vehicle {

    private String id;
    private String type;
    private String name;
    private String color;
    private int price;
    private Brand brand;
    private String productYear;

    public Vehicle(String id, String type, String name, String color, int price, Brand brand, String productYear) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.color = color;
        this.price = price;
        this.brand = brand;
        this.productYear = productYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getProductYear() {
        return productYear;
    }

    public void setProductYear(String productYear) {
        this.productYear = productYear;
    }

//    public void ShowAlone() {
//        System.out.println("===============================LIST OF VEHICLES ======================================");
//        System.out.println("--------------------------------------------------------------------------------------");
//        System.out.printf("%-10s| %-20s| %-10s| %-15s| %-13s| %-15s\n", "ID", "Type", "Name", "Color", "Price", "Brand");
//        System.out.println("--------------------------------------------------------------------------------------");
//        System.out.printf("%-10s| %-20s| %-10s| %-15s| %-13d| %-15s\n", this.getId(), this.getType(), this.getName(), this.getColor(), this.getPrice(), this.getBrand().getBrandName());
//    }

    public void show1() {
        System.out.println("===============================LIST OF VEHICLES ======================================");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s| %-15s | %-15s\n", "ID", "Brand", "Country");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("%-10s| %-15s | %-15s\n", this.getId(), this.getBrand().getBrandName(), this.getBrand().getCountryBrand());

    }

    @Override
    public String toString() {
        return id + ", " + type + ", " + name + ", " + color + ", " + price + ", " + brand.getBrandID() + ", " + productYear;
    }

}
