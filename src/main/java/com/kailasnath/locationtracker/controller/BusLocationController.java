package com.kailasnath.locationtracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.kailasnath.locationtracker.Model.BusLocation;
import com.kailasnath.locationtracker.service.BusLocationService;

@Controller
public class BusLocationController {

    @Autowired
    BusLocationService busLocationService;

    @PostMapping("update")
    public ResponseEntity<String> updateBusLocation(@NonNull @RequestBody BusLocation busLocation) {
        busLocationService.updateLocation(busLocation);
        System.out.println("update request received");
        return ResponseEntity.ok("Location Updated");
    }

    @GetMapping("find")
    public String getLocationView(@RequestParam("id") int id, Model model) {
        BusLocation busLocation = busLocationService.getLocation(id);
        model.addAttribute("buslocation", busLocation);
        return "viewLocation";
    }
}