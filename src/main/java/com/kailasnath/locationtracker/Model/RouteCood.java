package com.kailasnath.locationtracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RouteCood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int locId;

    private int routeNumber;
    private double latitude;
    private double longitude;

    public RouteCood() {
    }

    public RouteCood(int loc_id, int r_id, double latitude, double longitude) {
        this.locId = loc_id;
        this.routeNumber = r_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int id) {
        this.locId = id;
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

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int r_id) {
        this.routeNumber = r_id;
    }

    @Override
    public String toString() {
        return "Location [id=" + locId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
