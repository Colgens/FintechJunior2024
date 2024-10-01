package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
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
        Location location = locationRepository.findById(slug);
        if (location == null) {
            throw new ResourceNotFoundException("Location with id " + slug +  " not found");
        }
        return location;
    }

    public Location createLocation(Location location) {
        if (locationRepository.findById(location.getSlug()) != null) {
            throw new IllegalArgumentException("ID " + location.getSlug() + " already exists");
        }
        return locationRepository.save(location);
    }

    public Location updateLocation(String slug, Location location) {
        if (locationRepository.findById(slug) == null) {
            throw new ResourceNotFoundException("Location with id " + slug + " not found");
        }
        if (location.getSlug() != null && !location.getSlug().equals(slug)) {
            throw new IllegalArgumentException("IDs in the URL and JSON body of the request do not match");
        }
        location.setSlug(slug);
        return locationRepository.save(location);
    }

    public void deleteLocation(String slug) {
        if (locationRepository.findById(slug) == null) {
            throw new ResourceNotFoundException("Location with id " + slug + " not found");
        }
        locationRepository.deleteById(slug);
    }
}