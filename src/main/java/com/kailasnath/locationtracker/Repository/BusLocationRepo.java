package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kailasnath.locationtracker.Model.BusLocation;

@Repository
public interface BusLocationRepo extends JpaRepository<BusLocation, Integer> {

    @Modifying
    @Transactional
    @Query
    (value = "UPDATE bus_location_tbl SET latitude=:latitude, longitude=:longitude WHERE bus_id=:busId", 
    nativeQuery = true)
    public void updateBusLocation(@Param("busId") int busId, @Param("latitude") double latitude,
            @Param("longitude") double longitude);
}