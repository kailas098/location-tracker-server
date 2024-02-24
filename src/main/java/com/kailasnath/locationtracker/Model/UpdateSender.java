package com.kailasnath.locationtracker.Model;

import java.io.IOException;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
        // TODO Auto-generated method stub

        try {
            sseEmitter.send(SseEmitter.event().name("location-updated").data(locationAndRoutePackage));
        } catch (IOException e) {
            sseEmitter.complete();
            e.printStackTrace();
        }
        
        super.run();
    }

}
