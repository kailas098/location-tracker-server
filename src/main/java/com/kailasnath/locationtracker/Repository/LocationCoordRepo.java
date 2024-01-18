package com.kailasnath.locationtracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.LocationCoord;

@Repository
public interface LocationCoordRepo extends JpaRepository<LocationCoord, Integer>{

    public List<LocationCoord> findByLatitude(double latitude);

    public List<LocationCoord> findByLongitude(double longitude);

    public long countByLatitudeAndLongitude(double latitude, double longitude);
}
