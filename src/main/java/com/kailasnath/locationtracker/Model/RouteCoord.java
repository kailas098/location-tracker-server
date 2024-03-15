package com.kailasnath.locationtracker.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RouteCoord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int locId;

    private int routeNumber;
    private double latitude;
    private double longitude;
}
