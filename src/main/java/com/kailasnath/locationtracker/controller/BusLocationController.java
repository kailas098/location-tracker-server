package com.kailasnath.locationtracker.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.BusLocationAndRecordStatus;
import com.kailasnath.locationtracker.Model.LocationAndRoutePackage;
import com.kailasnath.locationtracker.service.BusLocationService;
import com.kailasnath.locationtracker.service.LocationCoordService;

@Controller
public class BusLocationController {

    @Autowired
    BusLocationService busLocationService;

    @Autowired
    LocationCoordService locationCoordService;

    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        SseEmitter sseEmitter = new SseEmitter();

        emitters.add(sseEmitter);

        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
        return sseEmitter;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateBusLocation(
            @NonNull @RequestBody BusLocationAndRecordStatus busLocationAndRecordStatus) {

        // System.out.println("update received");
        BusLocation busLocation = new BusLocation(
                busLocationAndRecordStatus.getBus_id(),
                busLocationAndRecordStatus.getLatitude(),
                busLocationAndRecordStatus.getLongitude());

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation);

        for (SseEmitter sseEmitter : emitters) {
            try {
                sseEmitter.send(SseEmitter.event().name("location-updated").data(locationAndRoutePackage));
            } catch (IOException e) {
                sseEmitter.complete();
                e.printStackTrace();
            }
        }

        busLocationService.updateLocation(busLocation);

        if (busLocationAndRecordStatus.isRecord())
            locationCoordService.addLocationCoord(busLocation);

        return ResponseEntity.ok("Location Updated");
    }

    @GetMapping("/find-bus/{id}")
    @ResponseBody
    public LocationAndRoutePackage getLocation(@PathVariable("id") int id, Model model) {

        BusLocation busLocation = busLocationService.getLocation(id);
        double[][] route = locationCoordService.getRouteCoords(id);

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation, route);
        return locationAndRoutePackage;
    }
}