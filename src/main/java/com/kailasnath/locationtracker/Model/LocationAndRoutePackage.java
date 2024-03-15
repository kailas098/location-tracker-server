package com.kailasnath.locationtracker.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class LocationAndRoutePackage {

    private BusLocation busLocation;
    private double[][] route;

    public LocationAndRoutePackage(BusLocation busLocation) {
        this.busLocation = busLocation;
    }
}
