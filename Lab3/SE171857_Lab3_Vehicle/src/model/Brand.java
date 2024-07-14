/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ASUS
 */
public class Brand {

    private String brandID;
    private String brandName;
    private String countryBrand;

    public Brand(String brandID, String brandName, String countryBrand) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.countryBrand = countryBrand;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCountryBrand() {
        return countryBrand;
    }

    public void setCountryBrand(String countryBrand) {
        this.countryBrand = countryBrand;
    }

    @Override
    public String toString() {
        return brandID + ", " + brandName + ", " + countryBrand;
    }
    
    
}
