package com.kailasnath.locationtracker.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
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

    private Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    private Map<String, Integer> clientBusMap = new ConcurrentHashMap<>();

    @GetMapping("/subscribe/{clientId}")
    public SseEmitter subscribe(@PathVariable("clientId") String clientId) {
        SseEmitter sseEmitter = new SseEmitter();

        emitterMap.put(clientId, sseEmitter);
        System.out.println("clientId: " + clientId);

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
                busLocationAndRecordStatus.getBusId(),
                busLocationAndRecordStatus.getLatitude(),
                busLocationAndRecordStatus.getLongitude());

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation);

        /*
         * TODO
         * 1) use clientId-busId map to send updates to the clients subscribes to that
         * busId.
         * 2) traverse through clientId-busId map find the if the busId matches with the
         * record's busId then push it to that client.
         * by using emitterMap.
         */

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

    @GetMapping("/find-bus/{busId}")
    @ResponseBody
    public LocationAndRoutePackage getLocation(@PathVariable("busId") int busId) {

        BusLocation busLocation = busLocationService.getLocation(busId);
        double[][] route = locationCoordService.getRouteCoords(busId);

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation, route);

        return locationAndRoutePackage;
    }

    @GetMapping("/find-bus/{clientId}/{busId}")
    @ResponseBody
    public LocationAndRoutePackage getLocation(@PathVariable("clientId") String clientId,
            @PathVariable("busId") int busId) {

        BusLocation busLocation = busLocationService.getLocation(busId);
        double[][] route = locationCoordService.getRouteCoords(busId);

        clientBusMap.put(clientId, busId);

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation, route);

        return locationAndRoutePackage;
    }
}