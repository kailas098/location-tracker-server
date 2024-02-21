package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.RouteCoord;
import java.util.List;

@Repository
public interface RouteCoordRepo extends JpaRepository<RouteCoord, Integer> {

    public long countByLatitudeAndLongitude(double latitude, double longitude);

    public List<RouteCoord> findByRouteNumber(int routeNumber);
}
