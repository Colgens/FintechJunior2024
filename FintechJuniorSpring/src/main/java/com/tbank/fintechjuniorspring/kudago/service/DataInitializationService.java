package com.tbank.fintechjuniorspring.kudago.service;

import com.tbank.executiontimeloggingstarter.annotation.LogExecutionTime;
import com.tbank.fintechjuniorspring.kudago.model.Category;
import com.tbank.fintechjuniorspring.kudago.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class DataInitializationService {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializationService.class);

    private final RestTemplate restTemplate;
    private final CategoryService categoryService;
    private final LocationService locationService;

    @Autowired
    public DataInitializationService(RestTemplate restTemplate, CategoryService categoryService,
                                     LocationService locationService) {
        this.restTemplate = restTemplate;
        this.categoryService = categoryService;
        this.locationService = locationService;
    }

    @LogExecutionTime
    public void initializeData() {
        logger.info("Starting data initialization...");

        String categoriesUrl = "https://kudago.com/public-api/v1.4/place-categories/?lang=ru";
        Category[] categories = restTemplate.getForObject(categoriesUrl, Category[].class);
        if (categories != null) {
            Arrays.stream(categories).forEach(categoryService::createCategory);
            logger.info("Initialized {} categories", categories.length);
        }

        String locationsUrl = "https://kudago.com/public-api/v1.4/locations/?lang=ru";
        Location[] locations = restTemplate.getForObject(locationsUrl, Location[].class);
        if (locations != null) {
            Arrays.stream(locations).forEach(locationService::createLocation);
            logger.info("Initialized {} locations", locations.length);
        }

        logger.info("Data initialization completed.");
    }
}