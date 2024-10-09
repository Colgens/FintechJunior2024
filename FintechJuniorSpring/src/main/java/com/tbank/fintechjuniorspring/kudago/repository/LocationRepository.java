package com.tbank.fintechjuniorspring.kudago.repository;

import com.tbank.fintechjuniorspring.kudago.model.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LocationRepository implements GenericRepository<Location, String> {
    private final ConcurrentHashMap<String, Location> locations = new ConcurrentHashMap<>();

    @Override
    public List<Location> findAll() {
        return new ArrayList<>(locations.values());
    }

    @Override
    public Location findById(String slug) {
        return locations.get(slug);
    }

    @Override
    public Location save(Location location) {
        locations.put(location.getSlug(), location);
        return location;
    }

    @Override
    public void deleteById(String slug) {
        locations.remove(slug);
    }
}