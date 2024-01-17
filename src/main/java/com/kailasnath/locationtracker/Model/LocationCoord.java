package com.kailasnath.locationtracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LocationCoord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int loc_id;

    private int r_id;
    private double latitude;
    private double longitude;

    public LocationCoord() {
    }
    public LocationCoord(int loc_id, int r_id, double latitude, double longitude) {
        this.loc_id = loc_id;
        this.r_id = r_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getLoc_id() {
        return loc_id;
    }
    public void setLoc_id(int id) {
        this.loc_id = id;
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
    public int getR_id() {
        return r_id;
    }
    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    @Override
    public String toString() {
        return "Location [id=" + loc_id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
