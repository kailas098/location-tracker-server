package com.kailasnath.locationtracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LocationTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationTrackerApplication.class, args);

		try {
			printIp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printIp() throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder("ipconfig");

		Process prcess = processBuilder.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(prcess.getInputStream()));

		String s = null;

		while ((s = reader.readLine()) != null) {
			if (s.contains("IPv4 Address")) {
				System.out.println(s);
				break;
			}
		}
	}

}
/*
 * TODO
 * 
 * 1) Add a color field in BLRS (or) assign color based on busId
 * 2) Add messaging.
 * 
 */