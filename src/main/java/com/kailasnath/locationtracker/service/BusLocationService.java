package com.kailasnath.locationtracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Repository.BusLocationRepo;

@Service
public class BusLocationService {

    @Autowired
    BusLocationRepo busLocationRepo;

    public void updateLocation(@NonNull BusLocation busLocation) {
        busLocationRepo.save(busLocation);
    }

    public BusLocation getLocation(int id) {
        return busLocationRepo.findById(id).get();
    }
}
