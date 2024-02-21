package com.kailasnath.locationtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.RouteCoord;
import com.kailasnath.locationtracker.Repository.RouteCoordRepo;

@Service
public class LocationCoordService {

    @Autowired
    RouteCoordRepo locationCoordRepo;

    public void addLocationCoord(@NonNull BusLocation busLocation) {

        double latitude = busLocation.getLatitude();
        double longitude = busLocation.getLongitude();

        RouteCoord coord = new RouteCoord();

        coord.setLatitude(latitude);
        coord.setLongitude(longitude);
        coord.setRouteNumber(busLocation.getBus_id());

        if (locationCoordRepo.countByLatitudeAndLongitude(latitude, longitude) <= 0)
            locationCoordRepo.save(coord);
    }

    public double[][] getRouteCoords(int r_id) {

        List<RouteCoord> route = locationCoordRepo.findByRouteNumber(r_id);
        double[][] res = new double[route.size()][2];

        for (int i = 0; i < route.size(); i++) {
            RouteCoord coordinate = route.get(i);
            res[i] = new double[] { coordinate.getLatitude(), coordinate.getLongitude() };
        }

        return res;
    }
}
