package com.kailasnath.locationtracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kailasnath.locationtracker.Model.PassengerDetails;

public interface PassengerDetailRepo extends JpaRepository<PassengerDetails, Integer> {

}
