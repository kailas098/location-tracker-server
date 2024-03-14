package com.kailasnath.locationtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocationTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationTrackerApplication.class, args);
	}

}
/*
 * TODO
 * 
 * 1) Add a color field in BLRS (or) assign color based on busId
 * 2) Connect to MySql DB.
 */