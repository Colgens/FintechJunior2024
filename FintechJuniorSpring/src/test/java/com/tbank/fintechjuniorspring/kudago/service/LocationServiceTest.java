package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
import com.tbank.fintechjuniorspring.kudago.model.Location;
import com.tbank.fintechjuniorspring.kudago.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location1;
    private Location location2;

    @BeforeEach
    public void setUp() {
        location1 = new Location();
        location1.setSlug("loc1");
        location1.setName("Location 1");

        location2 = new Location();
        location2.setSlug("loc2");
        location2.setName("Location 2");
    }

    @Test
    public void testGetAllLocations() {
        when(locationRepository.findAll()).thenReturn(Arrays.asList(location1, location2));

        List<Location> locations = locationService.getAllLocations();

        assertEquals(2, locations.size());
        assertEquals("Location 1", locations.get(0).getName());
        assertEquals("Location 2", locations.get(1).getName());
    }

    @Test
    public void testGetLocationById() {
        when(locationRepository.findById("loc1")).thenReturn(location1);

        Location location = locationService.getLocationById("loc1");

        assertNotNull(location);
        assertEquals("Location 1", location.getName());
    }

    @Test
    public void testGetLocationByIdNotFound() {
        when(locationRepository.findById("loc1")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> locationService.getLocationById("loc1"));
    }

    @Test
    public void testCreateLocation() {
        when(locationRepository.findById("loc1")).thenReturn(null);
        when(locationRepository.save(location1)).thenReturn(location1);

        Location createdLocation = locationService.createLocation(location1);

        assertNotNull(createdLocation);
        assertEquals("Location 1", createdLocation.getName());
    }

    @Test
    public void testCreateLocationAlreadyExists() {
        when(locationRepository.findById("loc1")).thenReturn(location1);

        assertThrows(IllegalArgumentException.class, () -> locationService.createLocation(location1));
    }

    @Test
    public void testUpdateLocation() {
        when(locationRepository.findById("loc1")).thenReturn(location1);
        when(locationRepository.save(location1)).thenReturn(location1);

        Location updatedLocation = locationService.updateLocation("loc1", location1);

        assertNotNull(updatedLocation);
        assertEquals("Location 1", updatedLocation.getName());
    }

    @Test
    public void testUpdateLocationNotFound() {
        when(locationRepository.findById("loc1")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> locationService.updateLocation("loc1", location1));
    }

    @Test
    public void testDeleteLocation() {
        when(locationRepository.findById("loc1")).thenReturn(location1);

        locationService.deleteLocation("loc1");

        verify(locationRepository, times(1)).deleteById("loc1");
    }

    @Test
    public void testDeleteLocationNotFound() {
        when(locationRepository.findById("loc1")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> locationService.deleteLocation("loc1"));
    }
}