package com.kailasnath.locationtracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BusLocation {

    @Id
    @GeneratedValue
    private int bus_id;
    private double latitude;
    private double longitude;

    
    public BusLocation() {
    }
    public BusLocation(int bus_id, double latitude, double longitude) {
        this.bus_id = bus_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public int getBus_id() {
        return bus_id;
    }
    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "BusLocation [id=" + bus_id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
