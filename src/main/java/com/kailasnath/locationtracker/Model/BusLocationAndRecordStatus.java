package com.kailasnath.locationtracker.Model;

public class BusLocationAndRecordStatus {

    private int busId;
    private double latitude;
    private double longitude;
    private boolean record;

    public BusLocationAndRecordStatus() {
    }

    public BusLocationAndRecordStatus(int busId, double latitude, double longitude, boolean record) {
        this.busId = busId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.record = record;
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

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "BusLocation [id=" + busId + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }
}
