package com.tbank.fintechjuniorspring.kudago.config;

import com.tbank.executiontimeloggingstarter.aspect.LogExecutionTimeAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExecutionTimeLoggingConfigTest {

    @Autowired
    private LogExecutionTimeAspect logExecutionTimeAspect;

    @Test
    public void testLogExecutionTimeAspectBean() {
        assertNotNull(logExecutionTimeAspect);
    }
}