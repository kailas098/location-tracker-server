package com.kailasnath.locationtracker.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.Model.BusLocationAndRecordStatus;
import com.kailasnath.locationtracker.Model.LocationAndRoutePackage;
import com.kailasnath.locationtracker.service.BusLocationService;
import com.kailasnath.locationtracker.service.LocationCoordService;

@Controller
public class BusLocationController {

    BusLocationService busLocationService;

    LocationCoordService locationCoordService;

    public BusLocationController(BusLocationService busLocationService, LocationCoordService locationCoordService) {
        this.busLocationService = busLocationService;
        this.locationCoordService = locationCoordService;
    }

    List<String> tokens = new CopyOnWriteArrayList<>();

    Map<Integer, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    Map<Integer, Integer> clientBusMap = new ConcurrentHashMap<>();

    @GetMapping("/")
    public String getIndex() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") int id, @RequestParam("pass") String pass) {
        if (!busLocationService.validate(id, pass)) {
            return "redirect:login.html";
        }

        String token = busLocationService.generateAndAssignToken(id);
        return "redirect:location-tracker.html?" + "id=" + id + "&token=" + token;
    }

    @GetMapping("/validate/{clientId}/{token}")
    public ResponseEntity<String> validate(@PathVariable("clientId") int clientId,
            @PathVariable("token") String token) {

        if (tokens.contains(token))
            return new ResponseEntity<>("Connection already in use", HttpStatus.CONFLICT);

        if (!busLocationService.validateToken(clientId, token))
            return new ResponseEntity<>("not authorized", HttpStatus.UNAUTHORIZED);

        tokens.add(token);
        return new ResponseEntity<>("authorized", HttpStatus.OK);
    }

    @GetMapping("/subscribe/{clientId}")
    public SseEmitter subscribe(@PathVariable("clientId") int clientId) {
        SseEmitter sseEmitter = new SseEmitter();

        emitterMap.put(clientId, sseEmitter);

        System.out.println("subcribed clientId: " + clientId);

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

        Iterator<Map.Entry<Integer, Integer>> itr = clientBusMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<Integer, Integer> entry = itr.next();

            if (entry.getValue() == busLocation.getBusId()) {
                int clientId = entry.getKey();
                SseEmitter sseEmitter = emitterMap.get(clientId);
                if (sseEmitter != null) {
                    try {

                        sseEmitter.send(SseEmitter.event().name("location-updated").data(locationAndRoutePackage));
                    } catch (ClientAbortException e) {

                        String token = busLocationService.getToken(clientId);
                        if (token != null)
                            tokens.remove(token);

                        busLocationService.removeToken(clientId);
                        System.out.println("Client removed: " + clientId + ", " + e.getMessage());
                        clientBusMap.remove(clientId);
                    } catch (IOException e) {

                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        busLocationService.updateLocation(busLocation);

        if (busLocationAndRecordStatus.isRecord())
            locationCoordService.addLocationCoord(busLocation);

        return new ResponseEntity<>("location updated", HttpStatus.OK);
    }

    @GetMapping("/find-bus/{clientId}/{busId}")
    @ResponseBody
    public ResponseEntity<LocationAndRoutePackage> getLocation(@PathVariable("clientId") int clientId,
            @PathVariable("busId") int busId) {

        BusLocation busLocation = busLocationService.getLocation(busId);

        if (busLocation == null)
            return new ResponseEntity<>(new LocationAndRoutePackage(), HttpStatus.NOT_FOUND);

        double[][] route = locationCoordService.getRouteCoords(busId);
        LocationAndRoutePackage locationAndRoutePackage = new LocationAndRoutePackage(busLocation, route);

        clientBusMap.put(clientId, busId);
        System.out.println("CLient - bus map : " + clientBusMap);
        System.out.println("Client - emitter map : " + emitterMap);
        System.out.println("Tokens : " + tokens);

        System.out.println("CLient - bus map : " + clientBusMap);
        System.out.println("Client - emitter map : " + emitterMap);
        System.out.println("tokens : " + tokens);

        return new ResponseEntity<>(locationAndRoutePackage, HttpStatus.OK);
    }
}