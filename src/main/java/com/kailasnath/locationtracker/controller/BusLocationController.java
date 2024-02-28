package com.kailasnath.locationtracker.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import com.kailasnath.locationtracker.ThreadClasses.UpdateSender;
import com.kailasnath.locationtracker.service.BusLocationService;
import com.kailasnath.locationtracker.service.LocationCoordService;

@Controller
public class BusLocationController {

    @Autowired
    BusLocationService busLocationService;

    @Autowired
    LocationCoordService locationCoordService;

    private Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    private Map<String, Integer> clientBusMap = new ConcurrentHashMap<>();

    @GetMapping("/subscribe/{clientId}")
    public SseEmitter subscribe(@PathVariable("clientId") String clientId) {
        SseEmitter sseEmitter = new SseEmitter();

        emitterMap.put(clientId, sseEmitter);
        System.out.println("clientId: " + clientId);

        sseEmitter.onCompletion(() -> emitterMap.remove(clientId));
        sseEmitter.onTimeout(() -> emitterMap.remove(clientId));

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

        Iterator<Map.Entry<String, Integer>> itr = clientBusMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Integer> entry = itr.next();

            if (entry.getValue() == busLocation.getBusId()) {
                SseEmitter sseEmitter = emitterMap.get(entry.getKey());
                if (sseEmitter != null) {
                    UpdateSender updateSender = new UpdateSender(sseEmitter, locationAndRoutePackage);
                    updateSender.start();
                }
            }
        }

        busLocationService.updateLocation(busLocation);

        if (busLocationAndRecordStatus.isRecord())
            locationCoordService.addLocationCoord(busLocation);

        return ResponseEntity.ok("Location Updated");
    }

    @GetMapping("/find-bus/{clientId}/{busId}")
    @ResponseBody
    public LocationAndRoutePackage getLocation(@PathVariable("clientId") String clientId,
            @PathVariable("busId") int busId) {

        BusLocation busLocation = busLocationService.getLocation(busId);
        double[][] route = locationCoordService.getRouteCoords(busId);

        clientBusMap.put(clientId, busId);

        // System.out.println(clientBusMap);
        // System.out.println(emitterMap);

        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation, route);

        return locationAndRoutePackage;
    }
}