package com.kailasnath.locationtracker.ThreadClasses;

import java.io.IOException;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kailasnath.locationtracker.Model.LocationAndRoutePackage;

public class UpdateSender extends Thread {

    private SseEmitter sseEmitter;
    private LocationAndRoutePackage locationAndRoutePackage;

    public UpdateSender(SseEmitter sseEmitter, LocationAndRoutePackage locationAndRoutePackage) {
        this.sseEmitter = sseEmitter;
        this.locationAndRoutePackage = locationAndRoutePackage;
    }

    @SuppressWarnings("null")
    @Override
    public void run() {
        try {
            sseEmitter.send(SseEmitter.event().name("location-updated").data(locationAndRoutePackage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        super.run();
    }

}
