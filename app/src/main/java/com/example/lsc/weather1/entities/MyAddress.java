package com.example.lsc.weather1.entities;

import android.location.Location;

/**
 * Created by lsc19 on 2018/2/27.
 */

public class MyAddress {
    int  status;
    public Location location;
    public String formatted_address;
    public  String country;
    public String province;
    public String city;
    public String district;

    public MyAddress() {}

    public MyAddress(int status, Location location, String formatted_address, String country, String province, String city, String district) {
        this.status = status;
        this.location = location;
        this.formatted_address = formatted_address;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
