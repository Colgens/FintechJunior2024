package com.tbank.fintechjuniorspring.kudago.config;
import com.tbank.executiontimeloggingstarter.aspect.LogExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionTimeLoggingConfig {

    @Bean
    public LogExecutionTimeAspect logExecutionTimeAspect() {
        return new LogExecutionTimeAspect();
    }
}
