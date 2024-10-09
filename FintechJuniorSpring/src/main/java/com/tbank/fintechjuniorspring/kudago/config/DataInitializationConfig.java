package com.tbank.fintechjuniorspring.kudago.config;

import com.tbank.fintechjuniorspring.kudago.service.DataInitializationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializationConfig {

    private final DataInitializationService dataInitializationService;

    public DataInitializationConfig(DataInitializationService dataInitializationService) {
        this.dataInitializationService = dataInitializationService;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> dataInitializationService.initializeData();
    }
}