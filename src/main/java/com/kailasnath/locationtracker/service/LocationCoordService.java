package com.kailasnath.locationtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.LocationCoord;
import com.kailasnath.locationtracker.Repository.LocationCoordRepo;

@Service
public class LocationCoordService {

    @Autowired
    LocationCoordRepo locationCoordRepo;

    public void addLocationCoord(@NonNull BusLocation busLocation) {

        double latitude = busLocation.getLatitude();
        double longitude = busLocation.getLongitude();

        LocationCoord coord = new LocationCoord();

        coord.setLatitude(latitude);
        coord.setLongitude(longitude);
        coord.setRouteNumber(busLocation.getBus_id());

        if (locationCoordRepo.countByLatitudeAndLongitude(latitude, longitude) == 0)
            locationCoordRepo.save(coord);
    }

    public double[][] getRouteCoords(int r_id) {

        List<LocationCoord> route = locationCoordRepo.findByRouteNumber(r_id);
        double[][] res = new double[route.size()][2];

        for (int i = 0; i < route.size(); i++) {
            LocationCoord coordinate = route.get(i);
            res[i] = new double[] { coordinate.getLatitude(), coordinate.getLongitude() };
        }

        return res;
    }
}
