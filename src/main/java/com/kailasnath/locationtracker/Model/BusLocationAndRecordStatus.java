package com.kailasnath.locationtracker.Model;

public class BusLocationAndRecordStatus {

    private int bus_id;
    private double latitude;
    private double longitude;
    private boolean record;

    public BusLocationAndRecordStatus() {
    }
    public BusLocationAndRecordStatus(int bus_id, double latitude, double longitude, boolean record) {
        this.bus_id = bus_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.record = record;
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
    public boolean isRecord() {
        return record;
    }
    public void setRecord(boolean record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "BusLocation [id=" + bus_id + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
