package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kailasnath.locationtracker.Model.BusLocation;

@Repository
public interface BusLocationRepo extends JpaRepository<BusLocation, Integer> {

}