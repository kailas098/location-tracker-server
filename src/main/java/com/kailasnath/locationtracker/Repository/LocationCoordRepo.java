package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.RouteCood;
import java.util.List;

@Repository
public interface LocationCoordRepo extends JpaRepository<RouteCood, Integer> {

    public long countByLatitudeAndLongitude(double latitude, double longitude);

    public List<RouteCood> findByRouteNumber(int routeNumber);
}
