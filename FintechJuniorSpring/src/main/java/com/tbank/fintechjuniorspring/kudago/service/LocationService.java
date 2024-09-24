package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.fintechjuniorspring.kudago.model.Location;
import com.tbank.fintechjuniorspring.kudago.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(String slug) {
        return locationRepository.findById(slug);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(String slug, Location location) {
        location.setSlug(slug);
        return locationRepository.save(location);
    }

    public void deleteLocation(String slug) {
        locationRepository.deleteById(slug);
    }
}