package com.tbank.fintechjuniorspring.kudago.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.tbank.fintechjuniorspring.kudago.model.Category;
import com.tbank.fintechjuniorspring.kudago.model.Location;
import com.tbank.fintechjuniorspring.kudago.repository.CategoryRepository;
import com.tbank.fintechjuniorspring.kudago.repository.LocationRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DataInitializationServiceIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("kudago.categories.url", () -> "http://localhost:" + wireMockServer.port() + "/categories");
        registry.add("kudago.locations.url", () -> "http://localhost:" + wireMockServer.port() + "/locations");
    }

    @BeforeAll
    static void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        wireMockServer.stubFor(get(urlEqualTo("/categories"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\":1,\"slug\":\"cafe\",\"name\":\"Кафе\"},{\"id\":2,\"slug\":\"restaurant\",\"name\":\"Ресторан\"}]")));

        wireMockServer.stubFor(get(urlEqualTo("/locations"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"slug\":\"msk\",\"name\":\"Москва\"},{\"slug\":\"spb\",\"name\":\"Санкт-Петербург\"}]")));
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testInitializeData() {
        List<Category> categories = categoryRepository.findAll();
        assertEquals(2, categories.size());
        assertEquals("cafe", categories.get(0).getSlug());
        assertEquals("restaurant", categories.get(1).getSlug());

        List<Location> locations = locationRepository.findAll();
        assertEquals(2, locations.size());
        assertEquals("msk", locations.get(0).getSlug());
        assertEquals("spb", locations.get(1).getSlug());
    }
}