package com.kailasnath.locationtracker.Model;

public class LocationAndRoutePackage {

    private BusLocation busLocation;
    private double[][] route;

    public LocationAndRoutePackage(BusLocation busLocation, double[][] route) {
        this.busLocation = busLocation;
        this.route = route;
    }

    public LocationAndRoutePackage(BusLocation busLocation) {
        this.busLocation = busLocation;
    }

    public BusLocation getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(BusLocation busLocation) {
        this.busLocation = busLocation;
    }

    public double[][] getRoute() {
        return route;
    }

    public void setRoute(double[][] route) {
        this.route = route;
    }

}
