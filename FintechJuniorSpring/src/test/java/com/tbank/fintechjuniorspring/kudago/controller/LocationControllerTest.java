package com.tbank.fintechjuniorspring.kudago.controller;

import com.tbank.fintechjuniorspring.kudago.model.Location;
import com.tbank.fintechjuniorspring.kudago.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private Location location1;
    private Location location2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();

        location1 = new Location();
        location1.setSlug("loc1");
        location1.setName("Location 1");

        location2 = new Location();
        location2.setSlug("loc2");
        location2.setName("Location 2");
    }

    @Test
    public void testGetAllLocations() throws Exception {
        List<Location> locations = Arrays.asList(location1, location2);
        when(locationService.getAllLocations()).thenReturn(locations);

        mockMvc.perform(get("/api/v1/locations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Location 1"))
                .andExpect(jsonPath("$[1].name").value("Location 2"));
    }

    @Test
    public void testGetLocationById() throws Exception {
        when(locationService.getLocationById("loc1")).thenReturn(location1);

        mockMvc.perform(get("/api/v1/locations/loc1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Location 1"));
    }

    @Test
    public void testCreateLocation() throws Exception {
        when(locationService.createLocation(any(Location.class))).thenReturn(location1);

        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"slug\":\"loc1\",\"name\":\"Location 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Location 1"));
    }

    @Test
    public void testUpdateLocation() throws Exception {
        when(locationService.updateLocation(eq("loc1"), any(Location.class))).thenReturn(location1);

        mockMvc.perform(put("/api/v1/locations/loc1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"slug\":\"loc1\",\"name\":\"Location 1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Location 1"));
    }

    @Test
    public void testDeleteLocation() throws Exception {
        doNothing().when(locationService).deleteLocation("loc1");

        mockMvc.perform(delete("/api/v1/locations/loc1"))
                .andExpect(status().isOk());
    }
}