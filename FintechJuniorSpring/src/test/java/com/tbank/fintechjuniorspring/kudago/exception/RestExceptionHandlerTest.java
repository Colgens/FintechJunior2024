package com.tbank.fintechjuniorspring.kudago.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RestExceptionHandlerTest {

    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @MockBean
    private ResourceNotFoundException resourceNotFoundException;

    @MockBean
    private IllegalArgumentException illegalArgumentException;

    @Test
    public void testHandleResourceNotFoundException() {
        when(resourceNotFoundException.getMessage()).thenReturn("Resource not found");

        ResponseEntity<String> response = restExceptionHandler.handleResourceNotFoundException(resourceNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody());
    }

    @Test
    public void testHandleIllegalArgumentException() {
        when(illegalArgumentException.getMessage()).thenReturn("Illegal argument");

        ResponseEntity<String> response = restExceptionHandler.handleIllegalArgumentException(illegalArgumentException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Illegal argument", response.getBody());
    }
}