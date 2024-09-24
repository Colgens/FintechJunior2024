package com.tbank.fintechjuniorspring.kudago.controller;

import com.tbank.executiontimeloggingstarter.annotation.LogExecutionTime;
import com.tbank.fintechjuniorspring.kudago.model.Location;
import com.tbank.fintechjuniorspring.kudago.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@LogExecutionTime
@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable String id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable String id, @RequestBody Location location) {
        return locationService.updateLocation(id, location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable String id) {
        locationService.deleteLocation(id);
    }
}
