package com.tbank.fintechjuniorspring.kudago.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DataInitializationConfigTest {

    @Autowired
    private CommandLineRunner commandLineRunner;

    @Test
    public void testCommandLineRunnerBean() {
        assertNotNull(commandLineRunner);
    }
}