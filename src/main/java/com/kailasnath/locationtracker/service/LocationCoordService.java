package com.kailasnath.locationtracker.service;

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
        
        LocationCoord coord = new LocationCoord();

        coord.setLatitude(busLocation.getLatitude());
        coord.setLongitude(busLocation.getLongitude());
        coord.setR_id(busLocation.getBus_id());

        locationCoordRepo.save(coord);
    }
}
