package com.kailasnath.locationtracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class BusLocation {

    @Id
    @GeneratedValue
    private int busId;
    private double latitude;
    private double longitude;

    public BusLocation() {
    }

    public BusLocation(int busId, double latitude, double longitude) {
        this.busId = busId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
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
        return "BusLocation [id=" + busId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
