package com.kailasnath.locationtracker.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusLocationAndRecordStatus {

    private int busId;
    private double latitude;
    private double longitude;
    private boolean record;
    
}
