package com.tbank.fintechjuniorspring.kudago.controller;

import com.tbank.fintechjuniorspring.kudago.exception.ResourceNotFoundException;
import com.tbank.fintechjuniorspring.kudago.exception.RestExceptionHandler;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LocationControllerNegativeTest {

    private MockMvc mockMvc;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(locationController)
                .setControllerAdvice(new RestExceptionHandler()) // Добавляем обработчик исключений
                .build();
    }

    @Test
    public void testGetLocationByIdNotFound() throws Exception {
        when(locationService.getLocationById("loc1")).thenThrow(new ResourceNotFoundException("Location not found"));

        mockMvc.perform(get("/api/v1/locations/loc1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateLocationAlreadyExists() throws Exception {
        when(locationService.createLocation(any(Location.class))).thenThrow(new IllegalArgumentException("Location already exists"));

        mockMvc.perform(post("/api/v1/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"slug\":\"loc1\",\"name\":\"Location 1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateLocationNotFound() throws Exception {
        when(locationService.updateLocation(eq("loc1"), any(Location.class))).thenThrow(new ResourceNotFoundException("Location not found"));

        mockMvc.perform(put("/api/v1/locations/loc1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"slug\":\"loc1\",\"name\":\"Location 1\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteLocationNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Location not found")).when(locationService).deleteLocation("loc1");

        mockMvc.perform(delete("/api/v1/locations/loc1"))
                .andExpect(status().isNotFound());
    }
}