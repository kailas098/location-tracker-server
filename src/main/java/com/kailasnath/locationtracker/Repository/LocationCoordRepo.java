package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.LocationCoord;
import java.util.List;


@Repository
public interface LocationCoordRepo extends JpaRepository<LocationCoord, Integer>{

    public long countByLatitudeAndLongitude(double latitude, double longitude);

    public List<LocationCoord> findByRouteNumber(int routeNumber);
}
