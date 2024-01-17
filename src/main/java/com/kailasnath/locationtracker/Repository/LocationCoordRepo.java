package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.LocationCoord;

@Repository
public interface LocationCoordRepo extends JpaRepository<LocationCoord, Integer>{

}
